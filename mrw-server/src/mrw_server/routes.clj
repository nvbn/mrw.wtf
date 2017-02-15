(ns mrw-server.routes
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [mrw-server.handlers :as handlers]))

(defroutes routes
  (GET "/api/v1/search/" request (handlers/search request))
  (not-found "Page not found"))
