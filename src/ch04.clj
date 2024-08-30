(ns ch04
  (:import (java.net InetAddress))) ;; 자바 클래스 불러오기

;; 자바 코드 가져다 쓰기
(class "caterpillar") ;; => java.lang.String

(.toUpperCase "caterpillar")

(.indexOf "caterpillar" "pillar")

;; string 생성
(String. "Hi!!!")

;; 호스트명 받아 아이피 주소로 바꾸기
(InetAddress/getByName "localhost")

;; 객체의 속성 사용
(.getHostName (InetAddress/getByName "localhost"))

;; 패키지명을 포함하는 완전한 이름 사용
(java.net.InetAddress/getByName "localhost")

;; doto를 사용하여 중첩 호출
(def sb (doto (StringBuffer. "Who ")
          (.append "are ")
          (.append "you?")))

(.toString sb)

;; doto를 사용하지 않은 버전
(def sb2 (.append
          (.append
           (StringBuffer. "Who ")
           "are ")
          "you?"))

(.toString sb2)

;; thread first로 되지 않을까?
(def thread-version (-> (StringBuffer. "Who ")
                        (.append "are ")
                        (.append "you?")))

(.toString thread-version)

;; 자바 클래스 import
(import 'java.util.UUID)

(UUID/randomUUID)

;; 다형성
;; 일반적인 방법
(defn who-are-you [input]
  (cond
    (= java.lang.String (class input)) "String - Who are you?"
    (= clojure.lang.Keyword (class input)) "Keyword - Who are you?"
    (= java.lang.Long (class input)) "Number - Who are you?"))

(who-are-you :alice)
(who-are-you "alice")
(who-are-you 123)
(who-are-you true)


;; 멀티 메소드 사용
;; 어떤 함수인지 따라 분기 할지 결정 한다.
;; 아래는 class 함수의 결과에 따라 분기된다.
(defmulti who-are-you-multi class)

(defmethod who-are-you-multi java.lang.String [input]
  (str "String - who are you? " input))

(defmethod who-are-you-multi clojure.lang.Keyword [input]
  (str "Keyword - who are you? " input))

(defmethod who-are-you-multi java.lang.Long [input]
  (str "Number - who are you? " input))

;; default가 없을 경우 밑에 boolean 값에 예외 발생
;; No method in multimethod 'who-are-you-multi' for dispatch value: class java.lang.Boolean
(defmethod who-are-you-multi :default [input]
  (str "I don't know - who are you? " input))

(who-are-you-multi :alice)

(who-are-you-multi "Alice")

(who-are-you-multi 123)

(who-are-you-multi true)

;; 멀티메소드에서 분기 함수는 어떤 함수든 사용 될 수 있다.
(comment
  (defmulti eat-mushroom (fn [height]
                           (if (< height 3)
                             :grow
                             :shrink)))

  (defmethod eat-mushroom :grow [_]
    "Eat the right side to grow.")

  (defmethod eat-mushroom :shrink [_]
    "Eat the left side to shrink.")

  (eat-mushroom 1)

  (eat-mushroom 9))

;; 프로토콜 사용
(defprotocol BigMushroom
  (eat-mushroom [this]))

(extend-protocol BigMushroom
  java.lang.String
  (eat-mushroom [this]
    (str (.toUpperCase this) " mmmm tasty!!"))

  clojure.lang.Keyword
  (eat-mushroom [this]
    (case this
      :grow "Eat the right side!"
      :shrink "Eat the left side!"))

  java.lang.Long
  (eat-mushroom [this]
    (if (< this 3)
      "Eat the right side to grow"
      "Eat the left side to shrink")))

(eat-mushroom "Big Mushroom")

(eat-mushroom :grow)

(eat-mushroom 1)

;; 새로운 자료구조를 만들기 위한 record 정의
(defrecord Mushroom [color height])

(def regular-mushroom (Mushroom. "white and blue polka dots" "2 inches"))

;; 정의한 레코드가 class화 됨
(class regular-mushroom)

;; 필드 읽기
(.-color regular-mushroom)
(.-height regular-mushroom)

;; record와 protocol 합치기
(comment
  (defprotocol Edible
    (bite-right-side [this])
    (bite-left-side [this]))

  (defrecord WinderlandMushroom [color height]
    Edible
    (bite-right-side [_]
      (str "The " color " bite makes you grow bigger"))
    (bite-left-side [_]
      (str "The " color " bite makes you grow smaller")))

  (defrecord RegularlandMushroom [color height]
    Edible
    (bite-right-side [_]
      (str "The " color " bite tastes bad"))
    (bite-left-side [_]
      (str "The " color " bite tastes bad too")))

  (def alice-mushroom (WinderlandMushroom. "blue dots" "3 inches"))
  (def reg-mushroom (RegularlandMushroom. "brown" "1 inches"))

  ;; 키가 커질지 작아질지에 대한 문자열 반환
  (bite-right-side alice-mushroom)
  (bite-left-side alice-mushroom)

  ;; 맛이 없다는 문자열 반환
  (bite-right-side reg-mushroom)
  (bite-left-side reg-mushroom))

;; 타입
;; 구조화된 데이터를 원한다면 record 
;; 그것이 아니라면 type을 사용한다.
(comment
  (defprotocol Edible
    (bite-right-side [this])
    (bite-left-side [this]))

  (deftype WinderlandMushroom []
    Edible
    (bite-right-side [_]
      (str "The bite makes you grow bigger"))
    (bite-left-side [_]
      (str "The bite makes you grow smaller")))

  (deftype RegularlandMushroom []
    Edible
    (bite-right-side [_]
      (str "The bite tastes bad"))
    (bite-left-side [_]
      (str "The bite tastes bad too")))

  (def alice-mushroom (WinderlandMushroom.))
  (def reg-mushroom (RegularlandMushroom.))

  ;; 키가 커질지 작아질지에 대한 문자열 반환
  (bite-right-side alice-mushroom)
  (bite-left-side alice-mushroom)

  ;; 맛이 없다는 문자열 반환
  (bite-right-side reg-mushroom)
  (bite-left-side reg-mushroom))

;; 일반 함수 사용
;; 프로토콜은 자주 사용하지 않는다?
(defn bite-right-side [mushroom]
  (if (= (:type mushroom) "wonderland")
    "The bite makes you grow bigger"
    "The bite tastes bad"))

(defn bite-left-side [mushroom]
  (if (= (:type mushroom) "wonderland")
    "The bite makes you grow smaller"
    "The bite tastes bad too"))

(bite-right-side {:type "wonderland"})
(bite-left-side {:type "wonderland"})

(bite-right-side {:type "regular"})
(bite-left-side {:type "regular"})
















