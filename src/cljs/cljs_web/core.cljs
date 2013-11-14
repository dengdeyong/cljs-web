(ns cljs-web.core
  (:require [enfocus.core :as ef]
            [enfocus.effects :as effects]
            [enfocus.events :as events]
            [clojure.browser.repl :as repl])
  (:use-macros [enfocus.macros :only [defaction]]))

(defn get-client-width []
  (.-clientWidth (.-body js/document)))

(defn get-screen-width []
  (let [client-width (get-client-width)]
    (str client-width "px")))

(defn get-container-width []
  (let [client-width (get-client-width)]
    (str (* client-width 7) "px")))

(defaction init-size []
  ["#content"] (ef/set-style :width (get-container-width))
  ["#content > *.content-wrap"] (ef/set-style :width (get-screen-width)))

(defaction remove-nav-active []
  "#menunav > * a" (ef/remove-class "active"))

(defaction move-content [move-left]
  "#content" (effects/chain
              (effects/move (* -1 move-left) :cury 500)))

(defn set-current-nav [target]
  (ef/at target (ef/add-class "active")))

(defn set-nav [target]
  (let [cur-index (ef/from target (ef/get-attr :rel))
        move-left (* (js/parseInt cur-index) (get-client-width))]
    (remove-nav-active)
    (set-current-nav target)
    (move-content move-left)))

(defaction nav []
  ["#menunav > * a"] (events/listen :click
                                    #(set-nav (.-currentTarget %))))

(set! (.-onload js/window)
      #(do (init-size)
           (nav)))
