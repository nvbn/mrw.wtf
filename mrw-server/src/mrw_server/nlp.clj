(ns mrw-server.nlp
  (:require [clj-http.client :as http]
            [mrw-server.conf :as confg]
            [mrw-server.conf :as conf]))

(defn get-sentiment
  [text]
  (let [response (http/post (str conf/nlp-url "/api/v1/sentiment/")
                            {:form-params {:text text}
                             :as :json})]
    (-> response :body :sentiment)))

(defn get-vader
  "Get vader from nlp service."
  [text]
  (let [response (http/post (str conf/nlp-url "/api/v1/vader/")
                            {:form-params {:text text}
                             :as :json})]
    (-> response :body)))
