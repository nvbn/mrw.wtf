(ns mrw-parser.fixtures)

(def imgur-response {:status 200
                     :body {:data {:description nil
                                   :tags []
                                   :vote nil
                                   :account_id nil
                                   :mp4 "http://i.imgur.com/XJfTfpw.mp4"
                                   :gifv "http://i.imgur.com/XJfTfpw.gifv"
                                   :section "reactiongifs"
                                   :width 426
                                   :animated true
                                   :type "image/gif"
                                   :in_gallery true
                                   :size 19014383
                                   :title nil
                                   :nsfw false
                                   :account_url nil
                                   :bandwidth 32609666845
                                   :link "http://i.imgur.com/XJfTfpw.gif"
                                   :id "XJfTfpw"
                                   :is_ad false
                                   :mp4_size 617661
                                   :datetime 1429651648
                                   :looping true
                                   :favorite false
                                   :height 426
                                   :views 1715}
                            :success true
                            :status 200}
                     :request-time 381
                     :trace-redirects ["https://api.imgur.com/3/image/XJfTfpw"]
                     :orig-content-encoding "gzip"})

(def reddit-response {:body {:kind "Listing",
                             :data {:modhash "",
                                    :children [{:kind "t3",
                                                :data {:banned_by nil,
                                                       :archived false,
                                                       :link_flair_text nil,
                                                       :selftext_html nil,
                                                       :hide_score false,
                                                       :contest_mode false,
                                                       :quarantine false,
                                                       :secure_media nil,
                                                       :selftext "",
                                                       :ups 10354,
                                                       :secure_media_embed {},
                                                       :stickied false,
                                                       :subreddit_type "public",
                                                       :spoiler false,
                                                       :media_embed {},
                                                       :over_18 false,
                                                       :clicked false,
                                                       :name "t3_5umb2h",
                                                       :permalink "/r/reactiongifs/comments/5umb2h/when_someone_stole_my_lunch_out_of_the_breakroom/",
                                                       :approved_by nil,
                                                       :removal_reason nil,
                                                       :mod_reports [],
                                                       :created 1.48736962E9,
                                                       :visited false,
                                                       :likes nil,
                                                       :is_self false,
                                                       :title "When someone stole my lunch out of the break-room fridge for the 3rd day in a row",
                                                       :subreddit_id "t5_2t5y3",
                                                       :edited false,
                                                       :author "hero0fwar",
                                                       :preview {:images [{:source {:url "https://i.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fm=jpg&amp;s=4fd2c476c1ee2df93073bba0abb0e350",
                                                                                    :width 720,
                                                                                    :height 404},
                                                                           :resolutions [{:url "https://i.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;fm=jpg&amp;s=bd49a73078cabac4ae5584f78447d35b",
                                                                                          :width 108,
                                                                                          :height 60}
                                                                                         {:url "https://i.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;fm=jpg&amp;s=3ef1b2d09e6549c1a865f4e4b30c7268",
                                                                                          :width 216,
                                                                                          :height 121}
                                                                                         {:url "https://i.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;fm=jpg&amp;s=fcd8608c718218e8e7160a645dec7652",
                                                                                          :width 320,
                                                                                          :height 179}
                                                                                         {:url "https://i.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;fm=jpg&amp;s=b3443c70f39cc016555265e311e11fa8",
                                                                                          :width 640,
                                                                                          :height 359}],
                                                                           :variants {:gif {:source {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?s=b7b9114aa652673d72f709f72aebef88",
                                                                                                     :width 720,
                                                                                                     :height 404},
                                                                                            :resolutions [{:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=096462c9daa2c203763f74eb7bbe36f2",
                                                                                                           :width 108,
                                                                                                           :height 60}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=5d41e5c364fb213349e43a6e617635e2",
                                                                                                           :width 216,
                                                                                                           :height 121}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=afd128a9fc16d0973f39c035664898fa",
                                                                                                           :width 320,
                                                                                                           :height 179}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;s=6932a52f7102ddcf179209d178254664",
                                                                                                           :width 640,
                                                                                                           :height 359}]},
                                                                                      :mp4 {:source {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fm=mp4&amp;mp4-fragmented=false&amp;s=82ae085b02980e0e9d7e150f49cf16d0",
                                                                                                     :width 720,
                                                                                                     :height 404},
                                                                                            :resolutions [{:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;fm=mp4&amp;mp4-fragmented=false&amp;s=7ec9347f1bd9c8e8c3b6fe8e18793eda",
                                                                                                           :width 108,
                                                                                                           :height 60}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;fm=mp4&amp;mp4-fragmented=false&amp;s=9c4be33072a172a5b04d0cbc1cf8a643",
                                                                                                           :width 216,
                                                                                                           :height 121}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;fm=mp4&amp;mp4-fragmented=false&amp;s=a7360137ac44778fef76a53d67e2ee04",
                                                                                                           :width 320,
                                                                                                           :height 179}
                                                                                                          {:url "https://g.redditmedia.com/NGkpyfLPjKQR3aOE2UCBlJ44v2EoQbNiZxEz8T9PUA4.gif?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;fm=mp4&amp;mp4-fragmented=false&amp;s=82a1ca93a1f20ef676cb354bb7f1cf7a",
                                                                                                           :width 640,
                                                                                                           :height 359}]}},
                                                                           :id "MuWu55IaXS-o060YkZkGZBYQE1KU_bn4BsLuOMCTg9U"}],
                                                                 :enabled true},
                                                       :distinguished nil,
                                                       :thumbnail "http://b.thumbs.redditmedia.com/dozx0MruaN_bvGufmhH_k4kHOxxq-YlEYtURCgyOb4w.jpg",
                                                       :link_flair_css_class nil,
                                                       :hidden false,
                                                       :locked false,
                                                       :user_reports [],
                                                       :id "5umb2h",
                                                       :score 10354,
                                                       :saved false,
                                                       :report_reasons nil,
                                                       :url "http://i.imgur.com/2BT0LZE.gif",
                                                       :post_hint "link",
                                                       :author_flair_css_class "captFlair",
                                                       :downs 0,
                                                       :gilded 0,
                                                       :created_utc 1.48734082E9,
                                                       :author_flair_text nil,
                                                       :domain "i.imgur.com",
                                                       :brand_safe true,
                                                       :num_comments 560,
                                                       :media nil,
                                                       :num_reports nil,
                                                       :subreddit "reactiongifs",
                                                       :subreddit_name_prefixed "r/reactiongifs",
                                                       :suggested_sort nil}}],
                                    :after "t3_5umb2h",
                                    :before "t3_5umb2h"}}
                      })
