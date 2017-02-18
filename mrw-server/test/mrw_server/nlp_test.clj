(ns mrw-server.nlp-test
  (:require [clojure.test :refer [deftest is]]
            [clj-http.client :as http]
            [mrw-server.nlp :as nlp]
            [mrw-server.conf :as conf]))

(deftest test-get-sentiment
  (let [request (atom [])]
    (with-redefs [http/post (fn [url data]
                              (reset! request [url data])
                              {:body {:sentiment "worry"}})
                  conf/nlp-url "http://nlp"]
      (is (= (nlp/get-sentiment "I lost my keys")
             "worry"))
      (let [[url data] @request]
        (is (= url "http://nlp/api/v1/sentiment/"))
        (is (= data {:form-params {:text "I lost my keys"}
                     :as :json}))))))
