(ns ch05
  (:require [camel-snake-kebab.core :as csk]))

(defn serpent-talk [input]
  (str "Serpent! You said: "
       (csk/->snake_case input)))

(defn -main [args]
  (println (serpent-talk (:arg1 args))))
