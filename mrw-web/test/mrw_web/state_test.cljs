(ns ^:figwheel-always mrw-web.state-test
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [deftest is testing async]]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [mrw-web.state :as state]))

(deftest test-get-initial-query
  (testing "With empty pathname"
    (.. js/history (pushState #js {} (str "test with empty pathname") "/"))
    (is (= (state/get-initial-query) "")))
  (testing "With mrw opened"
    (.. js/history (pushState #js {} (str "test with empty pathname")
                              "/mrw/I can't find my dog"))
    (is (= (state/get-initial-query) "I can't find my dog")))

  (.. js/history (pushState #js {} (str "tests")
                            "/test.html")))

(deftest test-fetch-reaction
  (async done
    (go (testing "When query not changes"
          (let [request (atom [])]
            (with-redefs [http/get (fn [url data]
                                     (reset! request [url data])
                                     (go {:body [{:title "MRW someone says I can pet their dog."
                                                  :url "http://i.imgur.com/KBKD9j4.gif"
                                                  :name "t3_4qwfd7"
                                                  :sentiment "worry"}]}))]
              (reset! state/state {:reaction nil
                                   :query ""})
              (<! (state/fetch-reaction "I can't find my dog"))
              (is (= @state/state {:query "I can't find my dog"
                                   :reaction {:title "MRW someone says I can pet their dog."
                                              :url "http://i.imgur.com/KBKD9j4.gif"
                                              :name "t3_4qwfd7"
                                              :sentiment "worry"}}))
              (is (= @request ["/api/v1/search/"
                               {:query-params {:query "I can't find my dog"}}])))))
        (testing "When query changes"
          (reset! state/state {:reaction nil
                               :query ""})
          (let [future (state/fetch-reaction "I can't find my dog")]
            (swap! state/state assoc :query "lost my keys")
            (<! future)
            (is (= @state/state {:query "lost my keys"
                                 :reaction nil}))))
        (done))))
