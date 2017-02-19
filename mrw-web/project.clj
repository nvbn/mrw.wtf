(defproject mrw-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473"]
                 [org.clojure/core.async "0.2.395"]
                 [cljs-http "0.1.42"]
                 [reagent "0.6.0" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [cljsjs/material-ui "0.16.7-0"]
                 [clj-http "2.3.0"]
                 [compojure "1.5.2"]]
  :plugins [[lein-figwheel "0.5.9"]
            [lein-cljsbuild "1.1.5"]]
  :cljsbuild {:builds {:main {:source-paths ["src/"]
                              :figwheel true
                              :compiler {:main "mrw-web.core"
                                         :asset-path "/compiled/"
                                         :output-to "resources/public/compiled/main.js"
                                         :output-dir "resources/public/compiled"}}
                       :production {:source-paths ["src/"]
                                    :compiler {:main "mrw-web.core"
                                               :optimizations :advanced
                                               :externs ["resources/externs/material-ui.ext.js"]
                                               :source-map "resources/public/production/main.js.map"
                                               :asset-path "compiled/"
                                               :output-to "resources/public/production/main.js"
                                               :output-dir "resources/public/production"}}
                       :test {:source-paths ["src/" "test/"]
                              :figwheel true
                              :compiler {:main "mrw-web.core-test"
                                         :asset-path "/tests/"
                                         :output-to "resources/public/tests/main.js"
                                         :output-dir "resources/public/tests"}}}}
  :figwheel {:ring-handler mrw-web.handlers/routes})
