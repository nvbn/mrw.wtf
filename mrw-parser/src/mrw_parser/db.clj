(ns mrw-parser.db
  (:require [clojure.tools.logging :as log]
            [clojure.string :as string]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [try+ throw+]]
            [mrw-parser.conf :as conf]))

(def mappings {conf/elasticsearch-mapping {:properties {:title {:type "text"
                                                                :store true}
                                                        :sentiment {:type "text"
                                                                    :store true}
                                                        :url {:type "text"
                                                              :store true}}}})

(defn create-index!
  "Create index in elasticsearch."
  []
  (log/info "Create index in elasticsearch")
  (try+
    (http/put (str conf/elasticsearch-url "/" conf/elasticsearch-index)
              {:content-type :json
               :form-params {:mappings mappings}})
    (catch Object _
      (log/warn "Index already exists!"))))

(defn put!
  "Put reaction in elasticsearch."
  [{:keys [title url name sentiment]}]
  (log/info "Put in elasticsearch name =" name)
  (let [endpoint (string/join "/" [conf/elasticsearch-url conf/elasticsearch-index
                                   conf/elasticsearch-mapping name])]
    (try+
      (http/put endpoint {:content-type :json
                          :form-params {:title title
                                        :url url
                                        :name name
                                        :sentiment sentiment}})
      (catch Object _
        (log/error (:throwable &throw-context) "can't put reaction to db")
        (throw+)))))
