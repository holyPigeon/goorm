# 공부한 내용


## 1. WHERE 조건절 개요

기본적인 문법은 다음과 같다.

```sql
SELECT [DISTINCT/ALL] 칼럼명 [ALIAS명] FROM 테이블명 WHERE 조건식;
```

## 2. 연산자의 종류

연산자의 종류는 다음과 같다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_172.jpg

- BETWEEN a AND b → a 이상 b 이하와 같다.
- IN (list) → list에 값이 속하는 로우를 선택한다.

연산자 우선순위는 다음과 같다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_173.jpg

근데 이거 솔직히 다 아는 거라서 그냥 이런 게 있구나 하고 보면 될 것 같다.

## 3. 비교 연산자

!https://dataonair.or.kr/publishing/img/knowledge/SQL_174.jpg

이건 우리집 강아지도 알 것 같다.

문자열 비교 방법은 다음과 같다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_175.jpg

문자열을 비교한다는 게 무슨 말인가 했는데, 사전식으로 비교했을 때 어떤 문자열이 더 앞에 오는지를 말하는 것 같다.

## 4. SQL 연산자

위와 같은 연산자 내용이다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_176.jpg

LIKE 절에서 쓰이는 와일드 카드의 종류이다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_177.jpg

- 사용 예시
  - % → “사람” 이란 단어를 포함된 결과를 찾고 싶을 때 → LIKE “%사람%”
  - _ → “앞뒤 글자가 ac”인 결과 찾고 싶을 때 → LIKE “a_c”
  - 가운데에 어떤 게 와도 상관 없음

## 5. 논리 연산자

아는 내용…

!https://dataonair.or.kr/publishing/img/knowledge/SQL_178.jpg

## 6. 부정 연산자

아는 내용…

!https://dataonair.or.kr/publishing/img/knowledge/SQL_180.jpg

## **7. ROWNUM, TOP 사용**

ROWNUM → 행의 개수를 제한하고 싶을 때 사용한다.

```sql
SELECT * FROM POSTS WHERE ROWNUM < 20;
```

SQL Server에서는 같은 기능에 TOP을 사용한다.

# 어려웠던 내용


딱히 없었던 것 같다!

# 궁금한 내용 / 부족한 내용


딱히 없다.

# 느낀점


아직까지 기본적인 내용인 것 같은데, 뒤에 GROUP BY, HAVING, ORDER BY 볼 생각에 살짝 끔찍하다. 마음의 준비를 해야될 것 같다.