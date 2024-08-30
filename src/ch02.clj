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
(defn flower-colors2 [{:keys [flower1 flower2]}]
  (str "The flowers are " flower1 " and " flower2))

(flower-colors2 {:flower1 "red" :flower2 "blue"})

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

;; 재귀
(def adjs ["normal"
           "too normal"
           "too big"
           "is swimming"])

(defn alice-is [in out]
  (if (empty? in)
    out
    (alice-is (rest in)
              (conj out
                    (str "Alice is " (first in))))))

(alice-is adjs [])

(defn alice-is-loop [input]
  (loop [in input
         out []]
    (if (empty? in)
      out
      (recur (rest in)
             (conj out (str "Alice is " (first in)))))))

(alice-is-loop adjs)

;; 재귀 호출시 스택 오버플로우
(defn countdown [n]
  (if (= n 0)
    n
    (countdown (- n 1))))

(countdown 3)
(countdown 100000)

;; recur로 재귀 호출
(defn countdown-recur [n]
  (if (= n 0)
    n
    (recur (- n 1))))

(countdown-recur 100000)

;; 데이터 변환

;; map
(def animals [:mouse :duck :dodo :lory :eaglet])

;; 키워드를 문자열로 만들기
(#(str %) :mouse) ;; => ":mouse"

(map #(str %) animals)

;; 무슨 차이 이지?
(map str animals)

(class (map #(str %) animals))

(take 3 (map #(str %) (range)))

(take 10 (map #(str %) (range)))

(println "Look at the mouse!") ;; => nil

;; 정의만 되고 출력되진 않음
(def animal-print (map #(println %) animals))

;; 출력
animal-print

;; 강제 실행 -> doall을 사용하여 강제 실행되어 표시됨
(def animal-print-doall (doall (map #(println %) animals)))

animal-print-doall ;; => (nil nil nil nil nil)


;; 함수가 한개 이상의 인자를 받을 경우 map은 2개의 컬렉션을 넣을 수 있다.
(def colors ["brown" "black" "blue" "pink" "gold"])

(defn gen-animal-string [animal color]
  (str animal "-" color))

(map gen-animal-string animals colors)

;; 가장 짧은 컬렉션이 있으면 그 컬렉션의 길이에 맞춰서 종료 된다.
(def colors-short ["brown" "black"])

(map gen-animal-string animals colors-short)

;; 가장 짧은 컬렉션에 맞춰 종료되기 때문에 무한 리스트 사용 가능
(map gen-animal-string animals (cycle colors-short))

;; reduce
(reduce + [1 2 3 4 5])

;; r은 계산된 누적 값
;; x는 인자 값
(reduce (fn [r x] (+ r (* x x))) [1 2 3])

(reduce (fn [r x] (if (nil? x) r (conj r x)))
        []
        [:mouse nil :duck nil nil :lory])

;; 다른 유용한 데이터 처리 함수들

;; 반대되는 함수를 반환 한다.
((complement nil?) nil)

((complement nil?) 1)

;; filter
;; complement로 만들어서 필터링 하기
(filter (complement nil?) [:mouse nil :dock nil])

;; 키워드 함수로 필터링 하기
(filter keyword? [:mouse nil :dock nil])

;; remove
(remove nil? [:mouse nil :duck nil])

;; for
;; name 함수는 키워드를 문자열로 바꾼다.
(for [animal [:mouse :duck :lory]]
  (str (name animal)))

;; 중첩 순회
(for [animal [:mouse :duck :lory]
      color [:red :blue]]
  (str (name color) (name animal)))

;; let 수정자 바인딩
(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-" (name animal))
            color-str (str "color-" (name color))
            display-str (str animal-str "-" color-str)]]
  display-str)

;; when 수정자
(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-" (name animal))
            color-str (str "color-" (name color))
            display-str (str animal-str "-" color-str)]
      :when (= color :blue)]
  display-str)

;; 중첩된 컬렉션을 받아서 중첩을 컬렉션을 제거하여 하나로 만든다.
(flatten [[:duck [:mouse] [[:lory]]]]) ;; => (:duck :mouse :lory)

;; 자료구조 형식 변환
(vec '(1 2 3))

(into [] '(1 2 3))

;; 정렬된 map으로 변경 하기
(sorted-map :b 2 :a 1 :z 3)

(into (sorted-map) {:b 2 :c 3 :a 1})

(into {} [[:a 1] [:b 2] [:c 3]]) ;; => {:a 1, :b 2, :c 3}

(into [] {:a 1 :b 2 :c 3}) ;; => [[:a 1] [:b 2] [:c 3]]

;; partition
;; 3개씩 묶기
(partition 3 [1 2 3 4 5 6 7 8 9])

;; 나누어 떨어지지 않으면 버린다.
(partition 3 [1 2 3 4 5 6 7 8 9 10])

;; partition-all 나누어 떨어지지 않아도 묶는다
(partition-all 3 [1 2 3 4 5 6 7 8 9 10])

;; partition-by 함수를 받아서 값이 변할때마다 새로운 묶음을 만든다.
(partition-by #(= 6 %) [1 2 3 4 5 6 7 8 9 10])



