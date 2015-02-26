(defproject clj-event-sourcing-sample "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot clj-event-sourcing-sample.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
