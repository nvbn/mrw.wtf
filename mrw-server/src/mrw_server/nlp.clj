(ns mrw-server.nlp
  (:require [clj-http.client :as http]
            [mrw-server.conf :as confg]
            [mrw-server.conf :as conf]))

(defn get-sentiment
  [text]
  (let [response (http/get (str conf/nlp-url "/api/v1/sentiment/")
                           {:query-params {:text text}
                            :as :json})]
    (-> response :body :sentiment)))

(defn get-vader
  "Get vader from nlp service."
  [text]
  (let [response (http/get (str conf/nlp-url "/api/v1/vader/")
                           {:query-params {:text text}
                            :as :json})]
    (-> response :body)))
