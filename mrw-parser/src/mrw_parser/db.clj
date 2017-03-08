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
                                                              :store true}
                                                        :compound {:type "float"
                                                                   :store true}
                                                        :neg {:type "float"
                                                              :store true}
                                                        :pos {:type "float"
                                                              :store true}
                                                        :neu {:type "float"
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
  [{:keys [name] :as reaction}]
  (log/info "Put in elasticsearch name =" name)
  (let [endpoint (string/join "/" [conf/elasticsearch-url conf/elasticsearch-index
                                   conf/elasticsearch-mapping name])]
    (try+
      (http/put endpoint {:content-type :json
                          :form-params reaction})
      (catch Object _
        (log/error (:throwable &throw-context) "can't put reaction to db")
        (throw+)))))
