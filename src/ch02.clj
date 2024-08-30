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

;; 구조분해 없이
(let [x ["blue" "small"]
      color (first x)
      size (last x)]
  (str "The " color " door is " size))

;; 구조분해 중첩
(let [[color [size]] ["blue" ["very small"]]]
  (str "The " color " door is " size))

;; 자료구조 전체 얻기
(let [[color [size] :as original] ["blue" ["very small"]]]
  {:color color :size size :original original})

;; 구조분해 맵에 바인딩
(let [{flower1 :flower1 flower2 :flower2}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))

;; 맵에 해당요소가 없는 경우
(let [{flower1 :flower1 flower2 :flower2 :or {flower2 "missing"}}
      {:flower1 "red"}]
  (str "The flowers are " flower1 " and " flower2))

;; 맵의 전체 자료구조 얻기
(let [{flower1 :flower1 :as all-flowers}
      {:flower1 "red"}]
  [flower1 all-flowers])

;; 맵의 키와 심볼이 같은 경우 - 많이 쓰임
(let [{:keys [flower1 flower2]}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))

;; 함수 인수의 구조분해
;; 구조분해 하기 전
(defn flower-colors [colors]
  (str "The flowers are " (:flower1 colors) " and " (:flower2 colors)))

(flower-colors {:flower1 "red" :flower2 "blue"})

;; 구조분해
(defn flower-colors [{:keys [flower1 flower2]}]
  (str "The flowers are " flower1 " and " flower2))

(flower-colors {:flower1 "red" :flower2 "blue"})

;; 지연(Lazy) 평가
(take 5 (range))
(take 10 (range))

(range 5)

(class (range 5))

(count (take 1000 (range)))
(count (take 100000 (range)))

(repeat 3 "rabbit")
(class (repeat 3 "rabbit"))

;; repeat의 끝을 지정하지 않으면 무한이 된다.
(take 5 (repeat "rabbit"))
(count (take 5000 (repeat "rabbit")))

(rand-int 10)

(rand-int 10)

;; 1개의 값을 가지고 반복하기 때문에 다른 값이 나올 수 없음
;; repeat - 값을 반복
(repeat 5 (rand-int 10)) ;; => (2 2 2 2 2)

;; repeatly - 함수를 반복하여 실행
#(rand-int 10)
(#(rand-int 10))

(repeatedly 5 #(rand-int 10))

(take 10 (repeatedly #(rand-int 10)))

;; cycle - 컬렉션의 요소를 무한 반복 한다.
(take 3 (cycle ["big" "small"]))

(take 6 (cycle ["big" "small"]))

;; rest - 지연 시퀀스를 받으면 또다른 지연 시퀀스를 반환 한다.
(take 3 (rest (cycle ["big" "small"])))








