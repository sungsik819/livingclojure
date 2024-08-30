(ns ch06
  (:require [clojure.core.async
             :refer [>! <! >!! <!! chan close! go go-loop alts!]]))

;; 동기 입출력
(comment
  (def tea-channel (chan 10))

;; 대기 입력 >!!
;; 느낌표 2개는 대기 호출
  (>!! tea-channel :cup-of-tea)

;; 대기 출력 <!!
  (<!! tea-channel)

  (>!! tea-channel :cup-of-tea-2)
  (>!! tea-channel :cup-of-tea-3)
  (>!! tea-channel :cup-of-tea-4)

  (close! tea-channel)

;; 채널이 close 된 후에는 넣을 수 없다.
  (>!! tea-channel :cup-of-tea-5)

;; 채널이 close 된 후에도 남아 있는 값이 있으면 뺄 수 있다.
  (<!! tea-channel) ;; => :cup-of-tea-2
  (<!! tea-channel) ;; => :cup-of-tea-3
  (<!! tea-channel) ;; => :cup-of-tea-4

;; 다 뺀 후에는 nil을 반환 한다.
  (<!! tea-channel) ;; nil

;; nil은 넣을 수 없다.
;; 예외 발생됨
  (>!! tea-channel nil))

;; 비동기 입출력
;; >! 비동기 입력 <! 비동기 출력
;; go 블럭 안에서 입력, 출력이 되어야 한다.
(let [tea-channel (chan 10)]
  (go (>! tea-channel :cup-of-tea-1))
  (go (println "Thanks for the" (<! tea-channel))))

;; go-loop를 사용하면 채널에 값이 들어오기를 기다렸다가 가져오기를 반복 할 수 있다.
(comment
  (def tea-channel (chan 10))

;; 백그라운드에서 tea-channel에 값이 들어오기를 기다린다.
  (go-loop []
    (println "Thanks for the" (<! tea-channel))
    (recur))

;; 값을 넣으면 대기하고 있던 go-loop가 실행 된다.
  (>!! tea-channel :hot-cup-of-tea)
  (>!! tea-channel :tea-with-sugar)
  (>!! tea-channel :tea-with-milk))

;; alts! - 여러 채널에서 가장먼저 도착하는 채널의 값을 가져온다.
(def tea-channel (chan 10))
(def milk-channel (chan 10))
(def sugar-channel (chan 10))

;; alts!로 여러 채널을 대기 한다.
(go-loop []
  (let [[v ch] (alts! [tea-channel
                       milk-channel
                       sugar-channel])]
    (println "Got" v "from" ch)
    (recur)))

(>!! sugar-channel :sugar)
(>!! milk-channel :milk)
(>!! tea-channel :tea)

;; core.async 연습
(def google-tea-service-chan (chan 10))
(def yahoo-tea-service-chan (chan 10))

(defn random-add []
  (reduce + (repeat (rand-int 100000) 1)))

;; random-add 시간동안 대기
(defn request-google-tea-service []
  (go
    (random-add)
    (>! google-tea-service-chan
        "tea compliments of google")))

(defn request-yahoo-tea-service []
  (go
    (random-add)
    (>! yahoo-tea-service-chan
        "tea compliments of yahoo")))

(defn request-tea []
  (request-google-tea-service)
  (request-yahoo-tea-service)
  (go (let [[v] (alts!
                 [google-tea-service-chan
                  yahoo-tea-service-chan])]
        (println v))))

(request-tea)








