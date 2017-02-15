(ns mrw-web.core
  (:require [reagent.core :refer [render-component]]
            [mrw-web.components :refer [app]]))

(enable-console-print!)

(render-component [app] (.getElementById js/document "main"))
