(ns mrw-parser.imgur
  (:require [clojure.string :as string]
            [clj-http.client :as http]))

(defn is-imgur?
  "Check that reaction gif is on imgur."
  [{:keys [url]}]
  (boolean (string/index-of url "imgur.com/")))

; TODO: add available video links
(defn update-links
  "Put direct url to image."
  [reaction]
  (let [[_ id _] (re-find #".*imgur.com/(\w+)(\..*)*" (:url reaction))]
    (assoc reaction :url (str "http://i.imgur.com/" id ".gif"))))
