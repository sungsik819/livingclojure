(ns ch05-test
  (:require [clojure.test :as t]
            [ch05 :as ch05]))

(t/deftest test-serpent-talk
  (t/testing "Cries serpent! with a snake_case version of the input"
    (t/is (= "Serpent! You said: hello_there"
             (ch05/serpent-talk "hello there")))))

