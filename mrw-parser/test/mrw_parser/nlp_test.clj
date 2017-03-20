(ns mrw-parser.nlp-test
  (:require [clojure.test :refer [deftest is]]
            [clj-http.client :as http]
            [mrw-parser.nlp :as nlp]
            [mrw-parser.conf :as conf]))

(deftest test-get-sentiment
  (let [request (atom [])]
    (with-redefs [http/get (fn [url data]
                             (reset! request [url data])
                             {:body {:sentiment "sad"}})
                  conf/nlp-url "http://nlp"]
      (is (= (nlp/get-sentiment "found a dog")
             "sad"))
      (let [[url data] @request]
        (is (= url "http://nlp/api/v1/sentiment/"))
        (is (= data {:query-params {:text "found a dog"}
                     :as :json}))))))

(deftest test-get-vader
  (let [request (atom [])]
    (with-redefs [http/get (fn [url data]
                             (reset! request [url data])
                             {:body {:compound 0.4404,
                                     :neg 0.0,
                                     :pos 0.744,
                                     :neu 0.256}})
                  conf/nlp-url "http://nlp"]
      (is (= (nlp/get-vader "got a gift")
             {:compound 0.4404,
              :neg 0.0,
              :pos 0.744,
              :neu 0.256}))
      (let [[url data] @request]
        (is (= url "http://nlp/api/v1/vader/"))
        (is (= data {:query-params {:text "got a gift"}
                     :as :json}))))))
