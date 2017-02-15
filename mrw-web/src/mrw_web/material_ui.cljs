(ns mrw-web.material-ui
  (:require [reagent.core :refer [adapt-react-class]]
            [cljsjs.material-ui]))

(def text-field (adapt-react-class js/MaterialUI.TextField))

(def theme-provider (adapt-react-class js/MaterialUI.MuiThemeProvider))

(def paper (adapt-react-class js/MaterialUI.Paper))
