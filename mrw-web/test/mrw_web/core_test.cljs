(ns mrw-web.core-test
  (:require [cljs.test :refer-macros [run-all-tests]]
            [mrw-web.state-test]))

(enable-console-print!)

(run-all-tests #"mrw-web.*-test")