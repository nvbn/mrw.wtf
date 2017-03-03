(ns mrw-parser.image-hosting.reddit
  (:require [clojure.string :as string]))

(defn is-reddit?
  [{:keys [url]}]
  (boolean (and (string/index-of url "i.redd.it")
                (string/ends-with? url ".gif"))))
