(ns ch03)

;; 동기적 변경 - atom, ref
(comment
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

  @counter)

(comment
  ;; ref 사용하기
  (def alice-height (ref 3))
  (def right-hand-bites (ref 10))

  ;; ref도 atom과 마찬가지로 역참조하여 값을 구할 수 있다.
  @alice-height
  @right-hand-bites

  ;; alter는 ref용으로 atom의 swap과 비슷함
  ;; swap과 같이 alter에서 받는 함수에도 부수효과가 없어야 한다.
  (defn eat-from-right-hand []
    (when (pos? @right-hand-bites)
      (alter right-hand-bites dec)
      (alter alice-height #(+ % 24))))

  ;; 아래와 같이 실행하는 경우 오류 발생 
  ;; Execution error (IllegalStateException) at ch03/eat-from-right-hand (REPL:65).
  ;; No transaction running
  (eat-from-right-hand)

  ;; dosync는 내부의 모든 상태 변화를 조율 하면서 트랜잭션 처리
  (dosync (eat-from-right-hand))

  @alice-height
  @right-hand-bites)

;; 위 코드를 수정한 버전
(comment
  ;; ref 사용하기
  (def alice-height (ref 3))
  (def right-hand-bites (ref 10))

  ;; dosync를 함수 안에 작성 한다.
  (defn eat-from-right-hand []
    (dosync (when (pos? @right-hand-bites)
              (alter right-hand-bites dec)
              (alter alice-height #(+ % 24)))))

  ;; 스레드를 3개 만들어 병행성 테스트
  (let [n 2]
    (future (dotimes [_ n] (eat-from-right-hand)))
    (future (dotimes [_ n] (eat-from-right-hand)))
    (future (dotimes [_ n] (eat-from-right-hand))))

  @alice-height
  @right-hand-bites)

;; commute 사용 - 내부의 별도 값으로 ref 값을 설정
;; ref 사용하기
(def alice-height (ref 3))
(def right-hand-bites (ref 10))

  ;; dosync를 함수 안에 작성 한다.
(defn eat-from-right-hand []
  (dosync (when (pos? @right-hand-bites)
            (commute right-hand-bites dec)
            (commute alice-height #(+ % 24)))))

  ;; 스레드를 3개 만들어 병행성 테스트
(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand))))

@alice-height
@right-hand-bites

;; ref-set 사용 하기
;; 하나의 ref 값이 다른 ref값을 의존하는 경우 사용한다.
;; 다른 값으로 부터 바로 계산되는 경우 사용
(def x (ref 1))
(def y (ref 1))

(defn new-values []
  (dosync
   (alter x inc)
   (ref-set y (+ 2 @x))))

(let [n 2]
  (future (dotimes [_ n] (new-values)))
  (future (dotimes [_ n] (new-values))))

@x
@y

;; 비동기적 변경 - agent
(comment
  (def who-agent (agent :caterpillar))

  @who-agent

  (defn change [state]
    (case state
      :caterpillar :chrysalis
      :chrysalis :butterfly
      :butterfly))

  (send who-agent change)

  @who-agent

  (send-off who-agent change)

  @who-agent)

;; agent의 오류 대응
;; agent 재시작 모드
(comment
  (def who-agent (agent :caterpillar))

  (defn change-error [state]
    (throw (Exception. "Boom!")))

  (send who-agent change-error)

  @who-agent

  ;; 위 오류가 실행된 상태에서 밑의 함수를 실행하면 오류가 남아 있다.
  (send who-agent change)

  ;; agent의 오류 검사
  ;; agent-errors는 deprecated 되어 agent-error 함수 사용
  (agent-error who-agent)


  ;; 위 오류가 계속 남아 있으므로 restart-agent로 초기값을 주고 다시 시작한다.
  (restart-agent who-agent :caterpillar)

  (send who-agent change)

  @who-agent)

;; 오류 모드를 바꿔 agent를 재시작 하지 않고 오류 확인 하기
(def who-agent (agent :caterpillar))

(defn change-error [state]
  (throw (Exception. "Boom!")))

(set-error-mode! who-agent :continue)

(defn err-handler-fn [a ex]
  (println "error " ex " value is " @a))

;; 오류 핸들러 등록
(set-error-handler! who-agent err-handler-fn)

;; 오류 발생
(send who-agent change-error)

@who-agent

;; 재시작하지 않고 다음 작업 실행
(send who-agent change)

@who-agent





