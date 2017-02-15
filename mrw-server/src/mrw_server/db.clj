(ns mrw-server.db
  (:require [clojure.tools.logging :as log]
            [clojure.string :as string]
            [clj-http.client :as http]
            [cheshire.core :refer [generate-string]]
            [slingshot.slingshot :refer [try+]]
            [mrw-server.conf :as conf]
            [clojure.string :as str]))

(defn- parse-response
  [response]
  (->> response :body :hits :hits (map :_source)))

(defn- search-reaction
  [body]
  (parse-response (http/get (str/join "/" [conf/elasticsearch-url conf/elasticsearch-index
                                           conf/elasticsearch-mapping "_search"])
                            {:content-type :json
                             :body (generate-string body)
                             :as :json})))

(defn- match
  [query sentiment]
  (let [body {:query {:bool {:must {:match {:title query}}
                             :filter {:term {:sentiment sentiment}}}}}]
    (search-reaction body)))

(defn search
  [query sentiment]
  (try+
    (match query sentiment)
    (catch Object e
      (log/error "Db request failed" e))))
