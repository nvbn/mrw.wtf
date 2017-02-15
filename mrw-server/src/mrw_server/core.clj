(ns mrw-server.core
  (:require [compojure.handler :refer [site]]
            [ring.middleware.json :refer [wrap-json-response]]
            [mrw-server.routes :refer [routes]]))

(def app
  (-> (site routes)
      wrap-json-response))
