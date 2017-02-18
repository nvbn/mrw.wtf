(ns mrw-parser.conf
  (:require [environ.core :refer [env]]))

(def elasticsearch-url (env :elasticsearch-url))

(def elasticsearch-index (env :elasticsearch-index))

(def elasticsearch-mapping (env :elasticsearch-mapping))

(def nlp-url (env :nlp-url))

(def reddit-username (env :reddit-username))

(def reddit-password (env :reddit-password))

(def reddit-key (env :reddit-key))

(def reddit-secret (env :reddit-secret))

(def imgur-id (env :imgur-id))
