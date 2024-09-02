(ns ch08)

(macroexpand-1
 '(when (= 2 2) (println "It is four!")))


(defn hi-queen [phrase]
  (str phrase ", so please your Majesty."))

;; 원래 코드
(comment
  (defn alice-hi-queen []
    (hi-queen "My name is Alice"))

  (alice-hi-queen)

  (defn march-hare-hi-queen []
    (hi-queen "I'm the March Hare"))

  (march-hare-hi-queen)

  (defn white-rabbit-hi-queen []
    (hi-queen "I'm the White Rabbit"))

  (white-rabbit-hi-queen)

  (defn mad-hatter-hi-queen []
    (hi-queen "I'm the Mad Hatter"))

  (mad-hatter-hi-queen))

;; 매크로 적용 코드
(comment
  (defmacro def-hi-queen [name phrase]
    (list 'defn
          (symbol name)
          []
          (list 'hi-queen phrase)))

  (macroexpand-1 '(def-hi-queen alice-hi-queen "My name is Alice"))

  (def-hi-queen alice-hi-queen "My name is Alice")
  (alice-hi-queen)

  (def-hi-queen march-hare-hi-queen "I'm the March Hare")
  (march-hare-hi-queen)

  (def-hi-queen white-rabbit-hi-queen "I'm the White Rabbit")
  (white-rabbit-hi-queen)

  (def-hi-queen mad-hatter-hi-queen "I'm the Mad Hatter")
  (mad-hatter-hi-queen))

;; 템플릿으로 변경
(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name) []
     (hi-queen ~phrase)))

(macroexpand-1 '(def-hi-queen alice-hi-queen "My name is Alice"))

(def-hi-queen alice-hi-queen "My name is Alice")
(alice-hi-queen)

(def-hi-queen march-hare-hi-queen "I'm the March Hare")
(march-hare-hi-queen)

(def-hi-queen white-rabbit-hi-queen "I'm the White Rabbit")
(white-rabbit-hi-queen)

(def-hi-queen mad-hatter-hi-queen "I'm the Mad Hatter")
(mad-hatter-hi-queen)