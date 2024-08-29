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










