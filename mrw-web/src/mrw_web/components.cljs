(ns mrw-web.components
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as string]
            [cljs.core.async :refer [<! timeout]]
            [reagent.core :refer [dom-node]]
            [mrw-web.material-ui :as ui]
            [mrw-web.state :refer [state fetch-reaction get-initial-query]]))

(defn search-field
  [{:keys [default-value]}]
  [ui/paper {:class "search-field"}
   [ui/text-field {:name "search-input"
                   :style {:width "100%"}
                   :on-change #(fetch-reaction (.. % -currentTarget -value))
                   :default-value default-value
                   :floating-label-text "MRW"}]])

(defn reaction-gif
  [{:keys [url name title]}]
  (let [url (string/replace url "^https?://" "://")]
    [:a {:href url :target "blank"}
     [ui/paper {:class "reaction"}
      [:img {:src url :alt title}]]]))

(defn footer
  []
  [ui/paper {:id "footer"}
   [:p "Powered by " [:a {:href "https://www.reddit.com/dev/api/"} "reddit"]
    " and " [:a {:href "https://api.imgur.com/"} "imgur"] " api. "
    "Source code available on "
    [:a {:href "https://github.com/nvbn/mrw.wtf"} "github"] "."]])

(def app
  (with-meta
    (fn []
      [ui/theme-provider
       [:div#container [search-field {:default-value (:query @state)}]
        (when-let [reaction (:reaction @state)]
          [reaction-gif reaction])
        [footer]]])
    {:component-will-mount #(when-let [query (:query @state)]
                              (fetch-reaction query))}))
