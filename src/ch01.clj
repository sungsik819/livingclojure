(ns ch01)

(first [:jar1 1 2 3 :jar2])

(rest [:jar1 1 2 3 :jar2])

(nth [:jar1 1 2 3 :jar2] 0)

(nth [:jar1 1 2 3 :jar2] 2)

;; 벡터에 last 적용
(last [:rabbit :pocket-watch :marmalade])

;; 리스트에 last 적용
(last '(:rabbit :pocket-watch :marmalade))

(count [1 2 3 4])

;; conj는 벡터의 맨 뒤에 요소를 추가 한다.
(conj [:toast :butter] :jam)


;; 여러 개의 요소를 맨 뒤에 추가 한다.
(conj [:toast :butter] :jam :honey)

;; conj는 리스트의 맨 앞에 요소를 추가 한다.
(conj '(:toast :butter) :jam)

;; 여러 개의 요소를 맨 앞에 추가 한다.
(conj '(:toast :butter) :jam :honey)

{:jam1 "strawberry" :jam2 "blackberry"}

;; map 자료 구조
;; get을 명시적으로 사용한 예
(get {:jam1 "strawberry" :jam2 "blackberry"} :jam2)

(get {:jam1 "strawberry" :jam2 "blackberry"} :jam3 "not found")

;; 키를 함수로 사용해 값 가져오기
(:jam2 {:jam1 "strawberry" :jam2 "blackberry" :jam3 "marmalade"})

;; keys 함수
(keys {:jam1 "strawberry" :jam2 "blackberry" :jam3 "marmalade"})

;; vals 함수
(vals {:jam1 "strawberry" :jam2 "blackberry" :jam3 "marmalade"})

;; map의 값을 갱신(update)
;; 값을 업데이트 하는 것이 아닌 업데이트 된 값을 가진 새로운 데이터 구조를 반환 한다.
(assoc {:jam1 "red" :jam2 "black"} :jam1 "orange")

;; 추가도 가능
(assoc {:jam1 "red" :jam2 "black"} :jam3 "orange")

;; 제거
;; 제거된 새로운 맵을 반환 한다.
(dissoc {:jam1 "strawberry" :jam2 "blackberry"} :jam1)

;; 집합 자료 구조
#{:red :blue :white :pink}

;; 생성할 때 중복을 허용하지 않는다. 
;; #{:red :blue :white :pink :pink}

;; 합집합
;; (clojure.set/union #{:r :b :w} #{:w :p :y})

;; 차집합
;; (clojure.set/difference #{:r :b :w} #{:w :p :y})

;; 교집합
;; (clojure.set/intersection #{:r :b :w} #{:w :p :y})

;; 벡터를 집합으로 변경
(set [:rabbit :rabbit :watch :door])

;; 맵을 집합으로 변경
(set {:a 1 :b 2 :c 3})

;; 요소 가져오기
(get #{:rabbit :door :watch} :rabbit)

;; 없을 경우
(get #{:rabbit :door :watch} :jar)

;; 키워드를 함수로 사용하여 가져오기
(:rabbit #{:rabbit :door :watch})

;; 집합 자체를 함수로 사용해 가져오기
(#{:rabbit :door :watch} :rabbit)

;; contains? 사용
(contains? #{:rabbit :door :watch} :rabbit)

(contains? #{:rabbit :door :watch} :jam)

;; 추가
(conj #{:rabbit :door} :jam)

;; 제거
(disj #{:rabbit :door} :door)

(first '(+ 1 1))

;; 심볼
(def developer "Alice")

developer
ch01/developer

(let [developer "Alice in Wonderland"]
  developer)

developer







