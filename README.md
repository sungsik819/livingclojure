# livingclojure
클로저 시작하기 연습

## 테스트 실행
```bash
> clj -X:test
```
## 테스트 자동 실행
```bash
> clj -X:test:watch
```
## main에 인자 전달
```bash
> clj -X ch05/-main :arg1 "HelloPigeon"
```

## 실행 파일 만들기
```bash
> clj -T:build uberjar
```