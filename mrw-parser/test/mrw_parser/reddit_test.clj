(ns mrw-parser.reddit-test
  (:require [clojure.test :refer [deftest is]]
            [clj-http.client :as http]
            [mrw-parser.reddit :as reddit]
            [mrw-parser.conf :as conf]
            [mrw-parser.image-hosting.imgur :as imgur]
            [mrw-parser.fixtures :refer [reddit-response]]))

(deftest test-get-access-token
  (let [request (atom [])]
    (with-redefs [http/post (fn [url data]
                              (reset! request [url data])
                              {:body {:access_token "token"}})
                  conf/reddit-username "username"
                  conf/reddit-password "password"
                  conf/reddit-key "key"
                  conf/reddit-secret "secret"]
      (let [token (reddit/get-access-token)
            [url data] @request]
        (is (= token "token"))
        (is (= url "https://www.reddit.com/api/v1/access_token"))
        (is (= data {:form-params {:grant_type "password"
                                   :username conf/reddit-username
                                   :password conf/reddit-password}
                     :basic-auth [conf/reddit-key conf/reddit-secret]
                     :headers {:User-Agent "mrw-parser.reddit"}
                     :as :json}))))))

(deftest test-with-reddit
  (with-redefs [reddit/get-access-token (constantly "token")]
    (reddit/with-reddit
      (is (= reddit/*access-token* "token")))))

(def reaction {:title "When someone stole my lunch out of the break-room fridge for the 3rd day in a row"
               :url "http://i.imgur.com/2BT0LZE.gif"
               :name "t3_5umb2h"})

(defmacro with-reddit-request
  [request-atom & body]
  `(let [~request-atom (atom [])]
     (with-redefs [reddit/get-access-token (constantly "token")
                   imgur/update-links identity
                   http/get (fn [url# data#]
                              (reset! ~request-atom [url# data#])
                              reddit-response)]
       (reddit/with-reddit
         ~@body))))

(deftest test-get-today-top
  (with-reddit-request request
    (let [reactions (reddit/get-today-top)
          [url data] @request]
      (is (= reactions [reaction]))
      (is (= url "https://oauth.reddit.com/r/reactiongifs/top/.json"))
      (is (= data {:headers {:User-Agent "mrw-parser.reddit"
                             :Authorization "Bearer token"}
                   :as :json
                   :query-params {:t "day"
                                  :limit 100
                                  :count 100}})))))

(deftest test-get-all-top
  (with-reddit-request request
    (let [reactions (vec (reddit/get-all-top 2))
          ; We only get second request here
          [url data] @request]
      (is (= reactions [reaction reaction]))
      (is (= url "https://oauth.reddit.com/r/reactiongifs/top/.json"))
      (is (= data {:headers {:User-Agent "mrw-parser.reddit"
                             :Authorization "Bearer token"}
                   :as :json
                   :query-params {:t "all"
                                  :limit 100
                                  :count 100
                                  :after (:name reaction)}})))))
