(ns mrw-server.db-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-http.client :as http]
            [cheshire.core :refer [generate-string]]
            [mrw-server.db]
            [mrw-server.db :as db]
            [mrw-server.conf :as conf]))

(def elasticsearch-response {:status 200
                             :headers {"content-type" "application/json; charset=UTF-8"
                                       "transfer-encoding" "chunked"}
                             :body {:took 626
                                    :timed_out false
                                    :_shards {:total 5
                                              :successful 5
                                              :failed 0}
                                    :hits {:total 67
                                           :max_score 5.655835
                                           :hits [{:_index "mrw"
                                                   :_type "reaction"
                                                   :_id "t3_58acpz"
                                                   :_score 5.655835
                                                   :_source {:title "MRW nothing got lost in the 'auto-recovery document'"
                                                             :url "http://i.imgur.com/DsKL8V3.gif"
                                                             :name "t3_58acpz"
                                                             :sentiment "worry"}}]}}
                             :request-time 1453
                             :trace-redirects ["http://localhost:9200/mrw/reaction/_search"]
                             :orig-content-encoding "gzip"})

(deftest test-search
  (testing "When request succeed"
    (let [request (atom [])]
      (with-redefs [http/get (fn [url data]
                               (reset! request [url data])
                               elasticsearch-response)
                    conf/elasticsearch-url "http://elastic"
                    conf/elasticsearch-index "mrw"
                    conf/elasticsearch-mapping "reaction"]
        (let [result (db/search "I lost my keys" "worry")
              [url data] @request]
          (is (= result [{:title "MRW nothing got lost in the 'auto-recovery document'",
                          :url "http://i.imgur.com/DsKL8V3.gif",
                          :name "t3_58acpz",
                          :sentiment "worry"}]))
          (is (= url "http://elastic/mrw/reaction/_search"))
          (is (= data {:content-type :json
                       :body (generate-string {:query {:bool {:must {:match {:title "I lost my keys"}}
                                                              :filter {:term {:sentiment "worry"}}}}})
                       :as :json}))))))
  (testing "When request fails"
    (with-redefs [http/get (fn [url data]
                             (throw (Exception.)))]
      (is (nil? (db/search "I lost my keys" "worry"))))))
