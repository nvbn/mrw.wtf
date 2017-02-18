(ns mrw-parser.imgur-test
  (:require [clojure.test :refer [deftest are testing]]
            [mrw-parser.imgur :as imgur]))

(deftest test-is-imgur?
  (testing "Is imgur"
    (are [url] (true? (imgur/is-imgur? {:url url}))
      "http://i.imgur.com/XJfTfpw.gifv"
      "http://i.imgur.com/XJfTfpw.gif"
      "http://i.imgur.com/XJfTfpw.mp4"
      "http://imgur.com/XJfTfpw"))
  (testing "is not imgur"
    (are [url] (false? (imgur/is-imgur? {:url url}))
      "https://media2.giphy.com/media/dFihq5BXlfChi/giphy.mp4"
      "https://i.redd.it/p9w3tdstshgy.gif")))
