(ns ch03)

(def who-atom (atom :caterpillar))

;; atom의 값 보기
@who-atom

;; atom의 값 바꾸기
(reset! who-atom :chrysalis)

@who-atom

;; swap!으로 값 바꾸기
;; swap에서 사용하는 함수는 순수 함수여야 한다
(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

(swap! who-atom change)

@who-atom

(def counter (atom 0))

@counter

(dotimes [_ 5] (swap! counter inc))

@counter

;; 병행 실행
(let [n 5]
  (future (dotimes [_ n] (swap! counter inc)))
  (future (dotimes [_ n] (swap! counter inc)))
  (future (dotimes [_ n] (swap! counter inc))))

@counter

;; 부수효과가 있는 함수인 경우 
;; 병행 실행시 중복된 값인 경우에 swap은 재시도를 한다.
(defn inc-print [val]
  (println val)
  (inc val))

(let [n 2]
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print))))

@counter

;; ref 사용하기





