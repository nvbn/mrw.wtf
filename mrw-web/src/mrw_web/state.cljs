(ns mrw-web.state
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as string]
            [cljs.core.async :refer [<! timeout]]
            [reagent.core :as reagent]
            [cljs-http.client :as http]))

(defn get-initial-query
  []
  (-> (.. js/document -location -pathname)
      (string/replace #"^/mrw" "")
      (string/replace-first #"/" "")
      js/decodeURI))

(defonce state (reagent/atom {:query (get-initial-query)
                              :reaction nil}))

(defn is-query-changed?
  [query]
  (go (<! (timeout 300))
      (not= query (:query @state))))

(defn fetch-reaction
  [query]
  (swap! state assoc :query query)
  (go (when-not (<! (is-query-changed? query))
        (.. js/history (pushState #js {} (str "mrw " query) (str "/mrw/" query)))
        (let [{[reaction] :body} (<! (http/get "/api/v1/search/"
                                               {:query-params {:query query}}))]
          (swap! state assoc
                 :reaction reaction)))))
