(ns cljs-web.core
  (:use [webfui.framework :only [launch-app]]
        [webfui.utilities :only [get-attribute clicked]])
  (:use-macros [webfui.framework.macros :only [add-mouse-watch]]))

(def initial-state {:position 0})

(defn get-client-width []
  (.-clientWidth (.-body js/document)))

(def menu-nav [[:1 "网站首页" :nav]
               [:2 "关于我们" :nav]
               [:3 "业务体系" :nav]
               [:4 "人才招聘" :nav]
               [:5 "公司动态" :nav]
               [:6 "联系我们" :nav]])

(defn display-width
  [screen-count]
  (str (* (get-client-width) screen-count) "px"))

(defn render-all [state]
  [:div {:class :main}
   [:div {:class :mainbg}
    [:div.mainbg1]
    [:div.mainbg2]]
   [:div {:class :content
          :style {:left (str (:position state) "px")
                  :width (display-width (count menu-nav))}}
    (for [[index label mouse] menu-nav]
      [:div {:class :content-wrap
             :style {:width (display-width 1)}}
       [:div {:class :content-in}
        [:h1 label]]])]
   [:div {:class :menu-nav}
    [:ul {:class :ul-nav}
     (for [[index label mouse] menu-nav]
       [:li
        [:a {:href "javascript:;"}
         [:span {:mouse mouse :rel index} label]]])]]])

(add-mouse-watch :nav [state first-element last-element]
                 (when (clicked first-element last-element)
                   (let [client-width (get-client-width)
                         nav-index (name (get-attribute first-element :rel))
                         move-left (* client-width (- nav-index 1))]
                     {:position (* -1 move-left)})))

(launch-app (atom initial-state) render-all)

