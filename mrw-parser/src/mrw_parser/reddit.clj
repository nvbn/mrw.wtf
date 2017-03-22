(ns mrw-parser.reddit
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [try+ throw+]]
            [mrw-parser.image-hosting.core :refer [prepare]]
            [mrw-parser.conf :as conf]))

(def ^:dynamic *access-token*)

(defn get-access-token
  []
  (let [response (try+
                   (http/post "https://www.reddit.com/api/v1/access_token"
                              {:form-params {:grant_type "password"
                                             :username conf/reddit-username
                                             :password conf/reddit-password}
                               :basic-auth [conf/reddit-key conf/reddit-secret]
                               :headers {:User-Agent "mrw-parser.reddit"}
                               :as :json})
                   (catch Object _
                     (log/error (:throwable &throw-context) "can't get access token")
                     (throw+)))]
    (-> response :body :access_token)))

(defmacro with-reddit
  "Authorize to reddit."
  [& body]
  `(binding [*access-token* (get-access-token)]
     ~@body))

(defn- fetch
  [& {:as params}]
  (let [response (try+
                   (http/get "https://oauth.reddit.com/r/reactiongifs/top/.json"
                             {:headers {:User-Agent "mrw-parser.reddit"
                                        :Authorization (str "Bearer " *access-token*)}
                              :as :json
                              :query-params params})
                   (catch Object _
                     (log/error (:throwable &throw-context) "can't fetch reactions")
                     (throw+)))]
    (:body response)))

(defn- is-gif?
  [{:keys [url]}]
  (string/ends-with? url ".gif"))

(defn- http-to-https
  [reaction]
  (update reaction :url string/replace "http://" "https://"))

(defn- parse-page
  [page]
  (->> page
       :data
       :children
       (map :data)
       (map prepare)
       (remove nil?)
       (filter is-gif?)
       (map http-to-https)
       (map #(select-keys % [:title :url :name :sentiment]))))

(defn get-today-top
  "Get today top reactions."
  []
  (log/info "Get today top from reddit")
  (parse-page (fetch :t "day"
                     :limit 100
                     :count 100)))

(defn- get-top-page
  [after]
  (let [page (fetch :t "all"
                    :limit 100
                    :count 100
                    :after after)]
    (parse-page page)))

(defn get-all-top
  "Get top reaction of all time."
  ([pages]
   (log/info "Start getting all top from reddit, pages =" pages)
   (get-all-top pages ""))
  ([pages after]
   (if (zero? pages)
     []
     (let [page (get-top-page after)]
       (log/info "Get all top from reddit, page =" pages)
       (lazy-seq (concat page (get-all-top (dec pages)
                                           (-> page last :name))))))))
