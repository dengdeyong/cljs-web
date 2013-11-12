(defproject cljs-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1586"]]
  :plugins [[lein-cljsbuild "0.2.7"]
            [lein-swank "1.4.4"]]
  :cljsbuild {
              :repl-listen-port 9000
              :builds [{
                        :source-path "src/cljs"
                        :compiler {
                                   :output-to "resources/public/js/core.js"
                                   :optimizations :advanced
                                   :pretty-print true}}]})
