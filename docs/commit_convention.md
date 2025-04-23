## Commit 메시지 구조
```
[type] subject

body 

footer
```

## Commit Type

- Feat : 새로운 기능 추가
- Fix : 버그 수정
- Docs : 문서 수정
- Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- Refactor : 코드 리펙토링
- Test : 테스트 코드, 리펙토링 테스트 코드 추가
- Chore : 빌드 업무 수정, 패키지 매니저 수정

## Commit Subject

- 50글자 이내 작성, 특수문자 사용 X
- 개조식으로 작성
- issue code 작성

## Commit Body

- 한 줄당 72자 이내로 작성
- 최대한 상세히 작성
- 코드를 무엇을 왜 변경했는지 작성

## Commit Footer

- ```유형 ID: #이슈 번호``` 로 구성
- 필요하다면 작성한다.

### Commit Issue Tracker Type

- Fixes: 이슈 수정중(해결 X)
- Resolves: 이슈 해결
- Ref: 참조할 이슈
- Related to: 관련된 이슈(해결 X)