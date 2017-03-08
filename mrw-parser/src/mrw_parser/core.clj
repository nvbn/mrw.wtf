(ns mrw-parser.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [overtone.at-at :refer [mk-pool every]]
            [mrw-parser.reddit :as reddit]
            [mrw-parser.db :as db]
            [mrw-parser.nlp :refer [get-sentiment get-vader]])
  (:gen-class))

(defn parse!
  "Parser reddit mrws."
  [get-posts]
  (let [posts (get-posts)
        with-sentiments (map posts #(assoc % :sentiment (get-sentiment (:title %))))]
    (doseq [post posts
            :let [sentiment (get-sentiment (:title post))
                  vader (get-vader (:title post))
                  reaction (merge post vader {:sentiment sentiment})]]
      (db/put! post))))

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
      (reddit/with-reddit
        (condp = (-> parsed-args :options :type)
          :initial (do (db/create-index!)
                       (parse! #(reddit/get-all-top 1000)))
          :daily (let [pool (mk-pool)]
                   (every (* 12 60 60 1000)
                          #(parse! reddit/get-today-top)
                          pool)))))))
