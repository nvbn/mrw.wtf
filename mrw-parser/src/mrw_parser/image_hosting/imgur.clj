(ns mrw-parser.image-hosting.imgur
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [try+ throw+]]
            [mrw-parser.conf :as conf]))

(defn is-imgur?
  "Check that reaction gif is on imgur."
  [{:keys [url]}]
  (boolean (string/index-of url "imgur.com/")))

(defn- get-information
  [id]
  (try+
    (let [response (http/get (str "https://api.imgur.com/3/image/" id)
                             {:headers {:User-Agent "mrw-parser.imgur"
                                        :Authorization (str "Client-ID " conf/imgur-id)}
                              :as :json})]
      (-> response :body :data))
    (catch Object e
      (log/error (:throwable &throw-context) "can't fetch urls from imgur"))))

(defn update-links
  "Put direct url to image."
  [reaction]
  (let [[_ id _] (re-find #".*imgur.com/(\w+)(\..*)*" (:url reaction))
        information (get-information id)]
    (when information
      (assoc reaction :url (:link information)
                      :url-video (:mp4 information)))))
