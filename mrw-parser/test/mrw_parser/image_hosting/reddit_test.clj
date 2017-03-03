(ns mrw-parser.image-hosting.reddit-test
  (:require [clojure.test :refer [deftest is]]
            [mrw-parser.image-hosting.reddit :as reddit]))

(deftest test-is-reddit?
  (is (true? (reddit/is-reddit? {:url "https://i.redd.it/vjrmxajljwiy.gif"})))
  (is (false? (reddit/is-reddit? {:url "https://i.redd.it/vjrmxajljwiy.mp4"})))
  (is (false? (reddit/is-reddit? {:url "https://gfycat.com/BeautifulJampackedIberianmole.gif"}))))
