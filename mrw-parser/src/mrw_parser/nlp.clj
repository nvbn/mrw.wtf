(ns mrw-parser.nlp
  (:require [clojure.tools.logging :as log]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [try+ throw+]]
            [mrw-parser.conf :as conf]))

(defn get-sentiment
  "Get sentiment from nlp service."
  [text]
  (let [response (try+
                   (http/post (str conf/nlp-url "/api/v1/sentiment/")
                              {:form-params {:text text}
                               :as :json})
                   (catch Object _
                     (log/error (:throwable &throw-context) "can't get sentiment")
                     (throw+)))]
    (-> response :body :sentiment)))

(defn get-vader
  "Get vader from nlp service."
  [text]
  (let [response (try+
                   (http/post (str conf/nlp-url "/api/v1/vader/")
                              {:form-params {:text text}
                               :as :json})
                   (catch Object _
                     (log/error (:throwable &throw-context) "can't get vader")
                     (throw+)))]
    (-> response :body)))
