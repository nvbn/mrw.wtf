(defproject mrw-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [clj-http "2.3.0"]
                 [cheshire "5.7.0"]
                 [ring "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.5.2"]
                 [slingshot "0.12.2"]
                 [environ "1.1.0"]]
  :plugins [[lein-ring "0.11.0"]]
  :ring {:handler mrw-server.core/app})
