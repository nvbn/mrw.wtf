(ns mrw-parser.imgur-test
  (:require [clojure.test :refer [deftest are testing is]]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [throw+]]
            [mrw-parser.image-hosting.imgur :as imgur]
            [mrw-parser.conf :as conf]
            [mrw-parser.fixtures :refer [imgur-response]]))

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
