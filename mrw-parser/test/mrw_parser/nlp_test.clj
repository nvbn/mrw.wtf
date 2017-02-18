(ns mrw-parser.nlp-test
  (:require [clojure.test :refer [deftest is]]
            [clj-http.client :as http]
            [mrw-parser.nlp :as nlp]
            [mrw-parser.conf :as conf]))

(deftest test-get-sentiment
  (let [request (atom [])]
    (with-redefs [http/post (fn [url data]
                              (reset! request [url data])
                              {:body {:sentiment "sad"}})
                  conf/nlp-url "http://nlp"]
      (is (= (nlp/get-sentiment "found a dog")
             "sad"))
      (let [[url data] @request]
        (is (= url "http://nlp/api/v1/sentiment/"))
        (is (= data {:form-params {:text "found a dog"}
                     :as :json}))))))
