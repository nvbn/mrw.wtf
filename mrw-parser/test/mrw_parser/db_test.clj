(ns mrw-parser.db-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-http.client :as http]
            [mrw-parser.conf :as conf]
            [mrw-parser.db :as db]))

(defmacro with-elastic-request
  [request-atom & body]
  `(let [~request-atom (atom [])]
     (with-redefs [http/put #(reset! ~request-atom [%1 %2])
                   conf/elasticsearch-url "http://elastic"
                   conf/elasticsearch-index "mrw"
                   conf/elasticsearch-mapping "reaction"]
       ~@body)))

(deftest test-create-index!
  (testing "When request succeed"
    (with-elastic-request request
      (db/create-index!)
      (let [[url data] @request]
        (is (= url "http://elastic/mrw"))
        (is (= data {:content-type :json
                     :form-params {:mappings db/mappings}})))))
  (testing "When index already exists"
    (with-redefs [http/put #(throw (Exception.))]
      (is (nil? (db/create-index!))))))

(deftest test-put!
  (with-elastic-request request
    (db/put! {:title "MRW I forgot"
              :url "http://i.imgur.com/XJfTfpw.gif"
              :name "reaction_1"
              :sentiment "horified"})
    (let [[url data] @request]
      (is (= url "http://elastic/mrw/reaction/reaction_1"))
      (is (= data {:content-type :json
                   :form-params {:title "MRW I forgot"
                                 :url "http://i.imgur.com/XJfTfpw.gif"
                                 :name "reaction_1"
                                 :sentiment "horified"}})))))
