(ns mrw-parser.reddit
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clj-http.client :as http]
            [mrw-parser.conf :as conf]))

(defn get-access-token
  []
  (let [response (http/post "https://www.reddit.com/api/v1/access_token"
                            {:form-params {:grant_type "password"
                                           :username conf/reddit-username
                                           :password conf/reddit-password}
                             :basic-auth [conf/reddit-key conf/reddit-secret]
                             :headers {:User-Agent "mrw-parser.reddit"}
                             :as :json})]
    (-> response :body :access_token)))

(defn- fetch
  [access-token & {:as params}]
  (let [response (http/get "https://oauth.reddit.com/r/reactiongifs/top/.json"
                           {:headers {:User-Agent "mrw-parser.reddit"
                                      :Authorization (str "Bearer" access-token)}
                            :as :json
                            :query-params params})]
    (:body response)))

(defn- get-full-url
  [url]
  (let [[_ id _] (re-find #".*imgur.com/(\w+)(\..*)*" url)]
    (str "http://i.imgur.com/" id ".gif")))

(defn- parse-page
  [page]
  (->> page
       :data
       :children
       (map :data)
       (filter #(string/index-of (:url %) "imgur.com/"))
       (map #(update % :url get-full-url))))

(defn get-today-top
  [access-token]
  (log/info "Get today top from reddit")
  (parse-page (fetch access-token
                     :t "day"
                     :limit 100
                     :count 100)))

(defn- get-top-page
  [access-token after]
  (let [page (fetch access-token
                    :t "all"
                    :limit 100
                    :count 100
                    :after after)]
    (parse-page page)))

(defn get-all-top
  ([access-token pages]
   (log/info "Start getting all top from reddit, pages =" pages)
   (get-all-top access-token pages ""))
  ([access-tolen pages after]
   (if (zero? pages)
     []
     (let [page (get-top-page access-tolen after)]
       (log/info "Get all top from reddit, page =" pages)
       (lazy-seq (concat page (get-all-top access-tolen
                                           (dec pages)
                                           (-> page last :name))))))))
