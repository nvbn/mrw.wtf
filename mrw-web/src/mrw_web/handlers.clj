(ns mrw-web.handlers
  "Handlers for development environment"
  (:require [compojure.core :refer [ANY defroutes]]
            [clj-http.client :as http]))

(defn redirect
  [request]
  (let [backend-request (assoc request :server-port 3000)]
    (http/request backend-request)))

(defn index
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp (clojure.java.io/resource "public/index.html"))})

(defroutes routes
  (ANY "/api/*" [] redirect)
  (ANY "/mrw/*" [] index))
