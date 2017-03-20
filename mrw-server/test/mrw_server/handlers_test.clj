(ns mrw-server.handlers-test
  (:require [clojure.test :refer [deftest is testing]]
            [ring.mock.request :as mock]
            [mrw-server.db :as db]
            [mrw-server.nlp :as nlp]
            [mrw-server.handlers :refer [search]]))

(deftest test-search
  (testing "When reaction found"
    (with-redefs [db/search (constantly [{:title "MRW nothing got lost in the 'auto-recovery document'",
                                          :url "http://i.imgur.com/DsKL8V3.gif",
                                          :name "t3_58acpz",
                                          :sentiment "worry"}])
                  nlp/get-sentiment (constantly "worry")
                  nlp/get-vader (constantly {:pos 0.5
                                             :neu 0.5
                                             :neg 0.5})]
      (let [result (-> (mock/request :get "/api/v1/search/")
                       (assoc :query-params {"query" "I lost my keys"})
                       search)]
        (is (= result {:status 200
                       :headers {}
                       :body [{:title "MRW nothing got lost in the 'auto-recovery document'"
                               :url "http://i.imgur.com/DsKL8V3.gif"
                               :name "t3_58acpz"
                               :sentiment "worry"}]})))))
  (testing "When reaction nout found"
    (with-redefs [db/search (constantly [])
                  nlp/get-sentiment (constantly "worry")
                  nlp/get-vader (constantly {:pos 0.5
                                             :neu 0.5
                                             :neg 0.5})]
      (let [result (-> (mock/request :get "/api/v1/search/")
                       (assoc :query-params {"query" "I lost my keys"})
                       search)]
        (is (= result {:status 200
                       :headers {}
                       :body []}))))))
