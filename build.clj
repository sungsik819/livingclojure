(ns build
  (:require [clojure.tools.build.api :as b]))

(def filename "async-tea-pary")
(def version "0.0.1")
(def target-dir "dist")
(def class-dir (str target-dir "/classes"))
(def uber-file (format "%s/%s-%s-standalone.jar" target-dir filename version))

(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path target-dir}))

(defn uberjar [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})

  (b/compile-clj {:basis @basis
                  :ns-compile '[async-tea-party]
                  :class-dir class-dir})

  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'async-tea-party}))