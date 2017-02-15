(ns mrw-server.conf
  (:require [environ.core :refer [env]]))

(def elasticsearch-url (env :elasticsearch-url))

(def elasticsearch-index (env :elasticsearch-index))

(def elasticsearch-mapping (env :elasticsearch-mapping))

(def nlp-url (env :nlp-url))
