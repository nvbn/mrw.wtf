(ns mrw-parser.imgur
  (:require [clojure.string :as string]
            [clj-http.client :as http]
            [mrw-parser.conf :as conf]))

(defn is-imgur?
  "Check that reaction gif is on imgur."
  [{:keys [url]}]
  (boolean (string/index-of url "imgur.com/")))

(defn- get-information
  [id]
  (let [response (http/get (str "https://api.imgur.com/3/image/" id)
                           {:headers {:User-Agent "mrw-parser.imgur"
                                      :Authorization (str "Client-ID " conf/imgur-id)}
                            :as :json})]
    (-> response :body :data)))

(defn update-links
  "Put direct url to image."
  [reaction]
  (let [[_ id _] (re-find #".*imgur.com/(\w+)(\..*)*" (:url reaction))
        {:keys [link mp4]} (get-information id)]
    (assoc reaction :url link :url-video mp4)))
