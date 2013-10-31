(ns cljs-web.core
(:require [clojure.browser.repl :as repl]
          [jayq.core :as jq]))
            
(repl/connect "http://localhost:9000/repl")

(defn- get-client-width
  []
  (jq/width (jq/$ :body)))

(def content-id "#j_content")
(def content-wrap "#j_content .content-wrap")
(def part-count (count (jq/$ content-wrap)))
(def client-width (get-client-width))

(defn init-ui
  "初始化内容区宽度，并设置宽度"
  []
  (let [el-content-wrap (jq/$ content-wrap)
        el-content-main (jq/$ content-id)]
   (jq/css el-content-wrap {:width (str client-width "px")})
   (jq/css el-content-main {:width (str (* part-count client-width) "px")})))

(defn slider
  "内容移动方法，elem为内容所在区dom对象，index为当前内容在内容区中的索引"
  [elem index client-width]
  (jq/anim elem {:left  (str (- 0 (* client-width index)) "px")} "easeInOutExpo"))

(defn nav-event
  [elem-navs elem-nav elem-index]
  (slider (jq/$ content-id) elem-index client-width)
  (jq/remove-class elem-navs "active")
  (jq/add-class elem-nav "active"))

(defn bind-evt
  [el-navs el-nav el-index]
  (let [elem-nav (jq/$ el-nav)]
    (jq/bind elem-nav
             :click
             (fn []
               (nav-event el-navs elem-nav el-index)))))

(defn nav
  "导航效果，el-navs为导航菜单dom对象,为一个集合"
  [el-navs]
  (doall
   (map (partial bind-evt el-navs) el-navs (iterate inc 0)))
  (jq/trigger (jq/$ (first el-navs)) :click))

(init-ui)
(nav (jq/$ "#j_menunav li a"))
