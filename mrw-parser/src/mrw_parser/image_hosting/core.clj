(ns mrw-parser.image-hosting.core
  (:require [mrw-parser.image-hosting.imgur :as imgur]
            [mrw-parser.image-hosting.reddit :as reddit]))

(defn prepare
  [reaction]
  (cond
    (imgur/is-imgur? reaction) (imgur/update-links reaction)
    (reddit/is-reddit? reaction) reaction))
