(ns mrw-parser.imgur-test
  (:require [clojure.test :refer [deftest are testing is]]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [throw+]]
            [mrw-parser.imgur :as imgur]
            [mrw-parser.conf :as conf]))

(deftest test-is-imgur?
  (testing "Is imgur"
    (are [url] (true? (imgur/is-imgur? {:url url}))
      "http://i.imgur.com/XJfTfpw.gifv"
      "http://i.imgur.com/XJfTfpw.gif"
      "http://i.imgur.com/XJfTfpw.mp4"
      "http://imgur.com/XJfTfpw"))
  (testing "is not imgur"
    (are [url] (false? (imgur/is-imgur? {:url url}))
      "https://media2.giphy.com/media/dFihq5BXlfChi/giphy.mp4"
      "https://i.redd.it/p9w3tdstshgy.gif")))

(def imgur-response {:status 200
                     :body {:data {:description nil
                                   :tags []
                                   :vote nil
                                   :account_id nil
                                   :mp4 "http://i.imgur.com/XJfTfpw.mp4"
                                   :gifv "http://i.imgur.com/XJfTfpw.gifv"
                                   :section "reactiongifs"
                                   :width 426
                                   :animated true
                                   :type "image/gif"
                                   :in_gallery true
                                   :size 19014383
                                   :title nil
                                   :nsfw false
                                   :account_url nil
                                   :bandwidth 32609666845
                                   :link "http://i.imgur.com/XJfTfpw.gif"
                                   :id "XJfTfpw"
                                   :is_ad false
                                   :mp4_size 617661
                                   :datetime 1429651648
                                   :looping true
                                   :favorite false
                                   :height 426
                                   :views 1715}
                            :success true
                            :status 200}
                     :request-time 381
                     :trace-redirects ["https://api.imgur.com/3/image/XJfTfpw"]
                     :orig-content-encoding "gzip"})

(deftest test-update-links
  (testing "When exists on imgur"
    (let [request (atom [])]
      (with-redefs [http/get (fn [url data]
                               (reset! request [url data])
                               imgur-response)
                    conf/imgur-id "imgur-id"]
        (let [reaction (imgur/update-links {:url "http://i.imgur.com/XJfTfpw.gifv"})
              [url data] @request]
          (is (= reaction {:url "http://i.imgur.com/XJfTfpw.gif"
                           :url-video "http://i.imgur.com/XJfTfpw.mp4"}))
          (is (= url "https://api.imgur.com/3/image/XJfTfpw"))
          (is (= data {:headers {:User-Agent "mrw-parser.imgur"
                                 :Authorization "Client-ID imgur-id"}
                       :as :json}))))))
  (testing "When doesn't exists on imgur"
    (with-redefs [http/get (fn [url data]
                             (throw+ {:status 404}))]
      (let [reaction (imgur/update-links {:url "http://i.imgur.com/XJfTfpw.gifv"})]
        (is (nil? reaction))))))
