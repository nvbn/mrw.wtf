(ns mrw-parser.image-hosting.core-test
  (:require [clojure.test :refer [deftest are testing is]]
            [clj-http.client :as http]
            [mrw-parser.image-hosting.core :as image-hosting]
            [mrw-parser.fixtures :refer [imgur-response]]))

(deftest test-prepare
  (testing "imgur url"
    (with-redefs [http/get (constantly imgur-response)]
      (let [reaction (image-hosting/prepare {:url "http://i.imgur.com/XJfTfpw.gifv"})]
        (is (= reaction {:url "http://i.imgur.com/XJfTfpw.gif"
                         :url-video "http://i.imgur.com/XJfTfpw.mp4"})))))
  (testing "reddit url"
    (let [reaction (image-hosting/prepare {:url "https://i.redd.it/vjrmxajljwiy.gif"})]
      (is (= reaction {:url "https://i.redd.it/vjrmxajljwiy.gif"}))))
  (testing "wrong url"
    (let [reaction (image-hosting/prepare {:url "https://wrong/image.gif"})]
      (is (nil? reaction)))))
