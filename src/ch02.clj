(ns ch02)

(class true)

(true? true)

(true? false)

(false? false)

(false? true)

(nil? nil)

(nil? 1)

(not true)

(not false)


;; nil은 논리적으로 거짓이다.
(not nil)

(not "hi")

(= :drinkme :drinkme)

(= :drinkme 4)

;; 컬렉션이 달라도 요소가 같으면 true 이다.
(= '(:drinkme :bottle) [:drinkme :bottle])

(not= :drinkme 4)

(empty? [:table :door :key])

(empty? [])

(empty? {})

(empty? '())

(seq [1 2 3])

(class [1 2 3])

(class (seq [1 2 3]))

(seq [])

(empty? [])

;; 비어 있지 않은 것을 확인하려면 seq를 쓰자
(seq [])

;; 모든 요소 결과 참 확인
;; 진위 함수(predicate)가 필요하다 - 논리 검사의 결과 값을 반환하는 함수
(every? odd? [1 3 5])

(every? odd? [1 2 3 4 5])

(defn drinkable? [x]
  (= x :drinkme))

(every? drinkable? [:drinkme :drinkme])

(every? drinkable? [:drinkme :poison])

(every? (fn [x] (= x :drinkme)) [:drinkme :drinkme])

(every? #(= % :drinkme) [:drinkme :drinkme])

;; 모든 요소 거짓
(not-any? #(= % :drinkme) [:drinkme :poison])

(not-any? #(= % :drinkme) [:poison :poison])

;; 일부 참
(some #(> % 3) [1 2 3 4 5])

(#{1 2 3 4 5} 3)

;; 집합으로 사용 가능 하다
(some #{3} [1 2 3 4 5])

(some #{4 5} [1 2 3 4 5])

;; 논리 거짓인 값에 대해서는 조심해야 한다.
(some #{nil} [nil nil nil])

(some #{false} [false false false])

;; 흐름 제어
(if true "it is true" "it is false")

(if false "it is true" "it is false")

(if nil "it is true" "it is false")

(if (= :drinkme :drinkme)
  "Try it"
  "Don't try it")

(let [need-to-grow-small (> 5 3)]
  (if need-to-grow-small
    "drink bottle"
    "don't drink bottle"))

(if-let [need-to-grow-small (> 5 1)]
  "drink-bottle"
  "don't drink bottle")

;; 참 값만 처리 - 참이 아니면 nil 반환
(defn drink [need-to-grow-small]
  (when need-to-grow-small "drink bottle"))

(drink true)

(drink false)

(when-let [need-to-grow-small true]
  "drink bottle")

(when-let [need-to-grow-small false]
  "drink bottle")

;; 여러가지 한번에 테스트
(let [bottle "drinkme"]
  (cond
    (= bottle "poison") "don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"))

;; cond는 검사의 순서가 중요
(let [x 5]
  (cond
    (> x 10) "bigger then 10"
    (> x 4) "bigger then 4"
    (> x 3) "bigger then 3"))

(let [x 5]
  (cond
    (> x 3) "bigger then 3"
    (> x 10) "bigger then 10"
    (> x 4) "bigger then 4"))

;; 참이 없으면 nil을 반환
(let [x 1]
  (cond
    (> x 10) "bigger then 10"
    (> x 4) "bigger then 4"
    (> x 3) "bigger then 3"))

(let [bottle "mystery"]
  (cond
    (= bottle "poison") "don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"
    :else "unknown"))

;; case
(let [bottle "drinkme"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"))

;; 참이 없으면 exception을 던진다.
(let [bottle "mystery"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"))

(let [bottle "mystery"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"
    "unknown"))

;; 함수를 만드는 함수
;; partial - currying
;; 1개의 함수의 인수를 단순화 시킴
(defn grow [name direction]
  (if (= direction :small)
    (str name " is growing smaller")
    (str name " is growing bigger")))

(grow "Alice" :small)
(grow "Alice" :big)

(partial grow "Alice")

((partial grow "Alice") :small)

;; comp
;; 여러 함수를 엮어서 하나의 함수로 만듦
(defn toggle-grow [direction]
  (if (= direction :small) :big :small))

(toggle-grow :big)

(toggle-grow :small)

(defn oh-my [direction]
  (str "Oh My! You are growing " direction))

(oh-my (toggle-grow :small))

(defn suprise [direction]
  ((comp oh-my toggle-grow) direction))

(suprise :small)

;; partial 연습
(defn adder [x y]
  (+ x y))

(adder 3 4)

(def adder-5 (partial adder 5))

(adder-5 10)

;; 구조분해
(let [[color size] ["blue" "small"]]
  (str "The " color " door is " size))









