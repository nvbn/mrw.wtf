(ns mrw-server.handlers
  (:require [clojure.tools.logging :as log]
            [ring.util.response :refer [response]]
            [mrw-server.db :as db]
            [mrw-server.nlp :refer [get-sentiment]]))

(defn search
  [request]
  (if-let [query (get-in request [:query-params "query"])]
    (let [sentiment (get-sentiment query)]
      (response (db/search query sentiment)))
    (response [])))
