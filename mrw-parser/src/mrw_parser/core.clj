(ns mrw-parser.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [mrw-parser.reddit :as reddit]
            [mrw-parser.db :as db]
            [mrw-parser.nlp :refer [get-sentiment]])
  (:gen-class))

(defn parse!
  [get-posts]
  (let [token (reddit/get-access-token)
        posts (get-posts token)
        with-sentiments (map posts #(assoc % :sentiment (get-sentiment (:title %))))]
    (doseq [post posts
            :let [sentiment (get-sentiment (:title post))]]
      (db/put! (assoc post :sentiment sentiment)))))

(def cli-options
  [["-t" "--type initial|daily" "Parsing type"
    :default :daily
    :parse-fn keyword
    :validate [#{:initial :daily} "Type should be daily or initinal."]]])

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)]
    (if (:errors parsed-args)
      (doseq [error (:errors parsed-args)]
        (println error))
      (condp = (-> parsed-args :options :type)
        :initial (do (db/create-index!)
                     (parse! #(reddit/get-all-top % 1000)))
        :daily (parse! reddit/get-today-top)))))
