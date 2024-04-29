# 공부한 내용

앞 장에서 다뤘지만, 반복적인 DB Call을 일으키는 SQL을 One SQL로 통합하면 성능상의 개선 효과가 극적으로 나타난다. 어떠한 기법을 통해 One SQL을 구현할 수 있는지 알아보자.

## 1. CASE문 활용

예를 들어 아래와 같은 예시를 SQL로 구현한다고 가정하자.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_500.jpg)

단순하게 One SQL을 작성한다면 아래와 같이 작성할 수 있다.

```sql
INSERT INTO 월별요금납부실적 (고객번호, 납입월, 지로, 자동이체, 신용카드, 핸드폰, 인터넷) 
SELECT K.고객번호, '200903' 납입월 , A.납입금액 지로 , B.납입금액 자동이체 , C.납입금액 신용카드 , D.납입금액 핸드폰 , E.납입금액 인터넷 
FROM 고객 K ,
(
	SELECT 고객번호, 납입금액 
	FROM 월별납입방법별집계 
	WHERE 납입월 = '200903' AND 납입방법코드 = 'A'
) A ,
(
	SELECT 고객번호, 납입금액 
	FROM 월별납입방법별집계 
	WHERE 납입월 = '200903' AND 납입방법코드 = 'B'
) B ,
(
	SELECT 고객번호, 납입금액 
	FROM 월별납입방법별집계 
	WHERE 납입월 = '200903' AND 납입방법코드 = 'C'
) C ,
(
	SELECT 고객번호, 납입금액 
	FROM 월별납입방법별집계 
	WHERE 납입월 = '200903' AND 납입방법코드 = 'D'
) D ,
(
	SELECT 고객번호, 납입금액 
	FROM 월별납입방법별집계 
	WHERE 납입월 = '200903' AND 납입방법코드 = 'E'
) E 
WHERE A.고객번호(+) = K.고객번호 AND B.고객번호(+) = K.고객번호 AND C.고객번호(+) = K.고객번호 AND D.고객번호(+) = K.고객번호 AND E.고객번호(+) = K.고객번호 AND NVL(A.납입금액,0)+NVL(B.납입금액,0)+NVL(C.납입금액,0)+NVL(D.납입금액,0)+NVL(E.납입금액,0) > 0
```

하지만 단순히 One SQL인게 중요한 게 아니라, 어떻게 I/O 효율을 달성하냐가 중요하다.

→ 동일 레코드에 반복 접근하지 않음으로써 블록 엑세스 양을 최소화해야 한다.

I/O 효율을 고려할 경우, 다음과 같이 작성할 수 있다.

```sql
INSERT INTO 월별요금납부실적 (고객번호, 납입월, 지로, 자동이체, 신용카드, 핸드폰, 인터넷) 
SELECT 고객번호, 납입월 , 
NVL(SUM(CASE WHEN 납입방법코드 = 'A' THEN 납입금액 END), 0) 지로 , 
NVL(SUM(CASE WHEN 납입방법코드 = 'B' THEN 납입금액 END), 0) 자동이체 , 
NVL(SUM(CASE WHEN 납입방법코드 = 'C' THEN 납입금액 END), 0) 신용카드 , 
NVL(SUM(CASE WHEN 납입방법코드 = 'D' THEN 납입금액 END), 0) 핸드폰 , 
NVL(SUM(CASE WHEN 납입방법코드 = 'E' THEN 납입금액 END), 0) 인터넷 
FROM 월별납입방법별집계 
WHERE 납입월 = '200903' 
GROUP BY 고객번호, 납입월;
```

추가적으로 Pivot 구문을 이용해서도 비슷한 쿼리를 짤 수 있다.