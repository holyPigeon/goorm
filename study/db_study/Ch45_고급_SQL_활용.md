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

## 2. 데이터 복제 기법 활용

테이블 복제 기법을 활용해야 할 경우가 가끔 있는데, 전통적으로는 아래와 같이 복제용 테이블을 만들어놓고 활용했다.

```sql
create table copy_t ( no number, no2 varchar2(2) ); 

insert into copy_t 
select rownum, lpad(rownum, 2, '0') 
from big_table where rownum <= 31; 

alter table copy_t add constraint copy_t_pk primary key(no); 
create unique index copy_t_no2_idx on copy_t(no2);
```

반면 아래와 같이 조건절 없는 조인(Cross Join)을 활용할 경우, 카티션 곱에 의해 데이터가 2배 복제된다.

```sql
select * from emp a, copy_t b where b.no <= 2;
```

오라클 최신 버전에서는 dual 테이블을 사용할 수도 있다.

```sql
select * from emp a, 
	(
		select rownum 
		no from dual 
		connect by level <= 2
	 ) b;
```

## **3. Union All을 활용한 M:M 관계의 조인**

보통 M:M 관계의 경우, 그대로 조인하면 카티션 곱이 발생하므로 좋지 않다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_501.jpg

위와 같은 두 테이블을 조인하는 경우, 상품과 연월을 기준으로 group by를 먼저 수행하면 두 집합이 1:1 관계가 되기 때문에 Full Outer Join을 통해 원하는 결과집합을 얻을 수 있다.

```sql
select 
nvl(a.상품, b.상품) as 상품 , 
nvl(a.계획연월, b.판매연월) as 연월 , 
nvl(계획수량, 0) 계획수량 , 
nvl(판매수량, 0) 판매수량 
from ( 
			select 상품, 계획연월, sum(계획수량) 계획수량 
			from 부서별판매계획 
			where 계획연월 between '200901' and '200903' 
			group by 상품, 계획연월 
		 ) a 
		 full outer join 
		 ( 
			 select 상품, 판매연월, sum(판매수량) 판매수량 
			 from 채널별판매실적 
			 where 판매연월 between '200901' and '200903' 
			 group by 상품, 판매연월 ) b 
			on a.상품 = b.상품 and a.계획연월 = b.판매연월
```

하지만 이 방식은 DBMS 버전에 따라 같은 테이블에 2번씩 엑세스하는 등 비효율적인 부분이 있다. Union All을 이용하여 보다 효율적으로 바꿀 수 있다.

```sql
select 
	상품, 
	연월, 
	nvl(sum(계획수량), 0) as 계획수량, 
	nvl(sum(실적수량), 0) as 실적수량 
from ( 
			select 
				상품, 
				계획연월 as 연월, 
				계획수량, 
				to_number(null) as 실적수량 
			from 부서별판매계획 
			where 계획연월 between '200901' and '200903' 
			union all 
			select 
				상품, 
				판매연월 as 연월, 
				to_number(null) as 계획수량, 
				판매수량 
			from 채널별판매실적 
			where 판매연월 between '200901' and '200903' 
			) a 
group by 상품, 연월; 

상품 연월 계획수량 판매수량 
---- ------ ------- ------ 
상품A 200901 10000 7000 
상품A 200902 5000 0 
상품A 200903 20000 8000 
상품B 200901 20000 0 
상품B 200902 15000 12000 
상품B 200903 0 19000 
상품C 200901 15000 13000 
상품C 200902 0 18000 
상품C 200903 20000 0
```
## 4. 페이징 처리

데이터베이스 Call과 네트워크 부하를 줄이기 위해서는 페이징 처리를 활용하는 것이 굉장히 중요하다. 페이징 처리 사례를 알아보자.

### 일반적인 페이징 처리용 SQL

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_502.jpg)

위 예시에 대한 페이징 처리 방식의 쿼리를 알아보자.

```sql
SELECT * FROM (
  SELECT ROWNUM NO, 거래일시, 체결건수, 체결수량, 거래대금, COUNT(*) OVER () CNT
  FROM (
    SELECT 거래일시, 체결건수, 체결수량, 거래대금 
    FROM 시간별종목거래
    WHERE 종목코드 = :isu_cd       -- 사용자가 입력한 종목코드
      AND 거래일시 >= :trd_time    -- 사용자가 입력한 거래일자 또는 거래일시
    ORDER BY 거래일시
  )
  WHERE ROWNUM <= :page * :pgsize + 1
) 
WHERE NO BETWEEN (:page - 1) * :pgsize + 1 AND :pgsize * :page
```

### 뒤쪽 페이지까지 자주 조회할 때

페이징 처리 자체가 사용자가 자주 조회할만한 앞 쪽 부분을 우선적으로 보여주는 것이기 때문에, 뒤쪽 페이지까지 자주 조회한다면 위 쿼리는 비효율적이게 된다.

→ 이러한 상황에서는 뒤쪽 페이지로 이동할 때 앞쪽의 레코드를 거치지 않고 바로 이동해야한다.

다음 버튼을 눌렀을 때

```sql
SELECT 거래일시, 체결건수, 체결수량, 거래대금
FROM (
  SELECT 거래일시, 체결건수, 체결수량, 거래대금
  FROM 시간별종목거래 A
  WHERE :페이지이동 = 'NEXT' 
    AND 종목코드 = :isu_cd
    AND 거래일시 >= :trd_time
  ORDER BY 거래일시
)
WHERE ROWNUM <= 11
```

이전 버튼을 눌렀을 때

```sql
SELECT 거래일시, 체결건수, 체결수량, 거래대금
FROM (
  SELECT 거래일시, 체결건수, 체결수량, 거래대금
  FROM 시간별종목거래 A
  WHERE :페이지이동 = 'PREV'
    AND 종목코드 = :isu_cd
    AND 거래일시 <= :trd_time
  ORDER BY 거래일시 DESC
)
WHERE ROWNUM <= 11
ORDER BY 거래일시
```

### Union All 활용

Union All을 활용하면 아래와 같이 하나의 SQL로 처리하는 것도 가능하다.

```sql
-- 'NEXT' 페이지에서 거래 데이터 조회
SELECT 거래일시, 체결건수, 체결수량, 거래대금
FROM (
  SELECT 거래일시, 체결건수, 체결수량, 거래대금
  FROM 시간별종목거래
  WHERE :페이지이동 = 'NEXT' -- 'NEXT' 입력 시 첫 페이지도 이를 사용
    AND 종목코드 = :isu_cd
    AND 거래일시 >= :trd_time
  ORDER BY 거래일시
)
WHERE ROWNUM <= 11

UNION ALL

-- 'PREV' 페이지에서 거래 데이터 조회
SELECT 거래일시, 체결건수, 체결수량, 거래대금
FROM (
  SELECT 거래일시, 체결건수, 체결수량, 거래대금
  FROM 시간별종목거래
  WHERE :페이지이동 = 'PREV'
    AND 종목코드 = :isu_cd
    AND 거래일시 <= :trd_time
  ORDER BY 거래일시 DESC
)
WHERE ROWNUM <= 11

-- 결과 정렬
ORDER BY 거래일시
```

## 5. 윈도우 함수 활용

!https://dataonair.or.kr/publishing/img/knowledge/SQL_503.jpg

상태코드가 NULL이면 가장 최근에 상태코드가 바뀐 레코드의 값을 보여주는 식이다.

```sql
SELECT 
  일련번호, 
  측정값,
  (
    SELECT /*+ index_desc(장비측정 장비측정_idx) */ 상태코드
    FROM 장비측정
    WHERE 일련번호 <= o.일련번호 
      AND 상태코드 IS NOT NULL
      AND ROWNUM <= 1
  ) AS 상태코드
FROM 
  장비측정 o
ORDER BY 
  일련번호
```

앞쪽 결과만이 아닌 전체 결과를 확인해야 한다면, 아래 쿼리가 더 효율적이다.

```sql
SELECT 
  일련번호, 
  측정값,
  LAST_VALUE(상태코드 IGNORE NULLS) OVER (
    ORDER BY 일련번호 
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS 상태코드
FROM 
  장비측정
ORDER BY 
  일련번호
```

## 6. With 구문 활용

최신 문법인 With 구문 역시 활용 가능하다.

```sql
WITH 위험고객카드 AS (
  SELECT 카드.카드번호
  FROM 카드
  JOIN 고객 ON 고객.고객번호 = 카드.고객번호
  WHERE 카드.카드번호객여부 = 'Y'
)
SELECT v.*
FROM (
  SELECT a.카드번호 AS 카드번호,
         SUM(a.거래금액) AS 거래금액,
         NULL AS 현금서비스잔액,
         NULL AS 해외거래금액
  FROM 카드거래내역 a
  JOIN 위험고객카드 b ON a.카드번호 = b.카드번호
  GROUP BY a.카드번호

  UNION ALL

  SELECT a.카드번호 AS 카드번호,
         NULL AS 거래금액,
         SUM(a.amt) AS 현금서비스금액,
         NULL AS 해외거래금액
  FROM (
    SELECT a.카드번호 AS 카드번호,
           SUM(a.거래금액) AS amt
    FROM 현금거래내역 a
    JOIN 위험고객카드 b ON a.카드번호 = b.카드번호
    GROUP BY a.카드번호

    UNION ALL

    SELECT a.카드번호 AS 카드번호,
           SUM(a.결재금액) * -1 AS amt
    FROM 현금결재내역 a
    JOIN 위험고객카드 b ON a.카드번호 = b.카드번호
    GROUP BY a.카드번호
  ) a
  GROUP BY a.카드번호

  UNION ALL

  SELECT a.카드번호 AS 카드번호,
         NULL AS 거래금액,
         NULL AS 현금서비스금액,
         SUM(a.거래금액) AS 해외거래금액
  FROM 해외거래내역 a
  JOIN 위험고객카드 b ON a.카드번호 = b.카드번호
  GROUP BY a.카드번호
) v
```

상당히 끔찍한 모습이다…
