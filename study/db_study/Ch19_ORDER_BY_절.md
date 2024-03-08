# 공부한 내용


## 1. ORDER BY 정렬

ORDER BY의 역할은? 데이터들을 일정 기준에 따라 정렬하는 것.

다음과 같이 ORDER BY <컬럼명> [ASC | DESC] 와 같은 문법으로 사용한다.

ASC는 오름차순, DESC는 내림차순이다.

```sql
SELECT PLAYER_NAME 선수명, POSITION 포지션, BACK_NO 백넘버 
FROM PLAYER 
ORDER BY PLAYER_NAME DESC;
```

## 2. SELECT 문장 실행 순서

SELECT 문장의 실행 순서는 다음과 같다.

```sql
5. SELECT 칼럼명 [ALIAS명] 
1. FROM 테이블명 
2. WHERE 조건식 
3. GROUP BY 칼럼(Column)이나 표현식 
4. HAVING 그룹조건식 
6. ORDER BY 칼럼(Column)이나 표현식;
```

정리하자면, WHERE / GROUP BY / HAVING과 같은 조건들을 포함한 SELECT 문이 먼저 조회되며, 조회된 결과를 대상으로 정렬을 하게 된다.

아래와 같이 SELECT 절에서 사용하지 않은 함수, 속성도 HAVING, ORDER BY 절에서 사용 가능하다.

```sql
SELECT JOB 
FROM EMP 
GROUP BY JOB 
HAVING COUNT(*) > 0 
ORDER BY MAX(EMPNO), MAX(MGR), SUM(SAL), COUNT(DEPTNO), MAX(HIREDATE);
```

## 3. Top N 쿼리

오라클의 경우, ORDER BY 절을 통한 정렬이 원하는대로 이루어지지 않는 경우가 있다.

```sql
SELECT ENAME, SAL 
FROM EMP 
WHERE ROWNUM < 4 
ORDER BY SAL DESC;
```

- 해당 SQL의 경우, 언뜻 보면 전체 데이터 정렬 → 조건에 따른 데이터 추출의 순서로 보인다.
- 하지만 실제로는 반대 순서인 조건에 따른 데이터 추출 → 추출된 데이터 정렬이 된다.

  → 즉, 테이블 내에서 SAL이 제일 높은 행을 찾으려는 시도가 무산된다.


이럴 때는 인라인 뷰 사용을 통해 먼저 정렬을 하고 데이터 추출을 진행한다.

```sql
SELECT ENAME, SAL 
FROM 
	(SELECT ENAME, SAL 
	 FROM EMP 
	 ORDER BY SAL DESC) 
WHERE ROWNUM < 4 ;
```

# 어려웠던 내용


아직도 집계 함수와 GROUP BY + HAVING을 사용하는 부분이 조금 어려운 것 같다.

다행스러운건 인라인 뷰가 뭔가 제일 어려울 줄 알았는데 보다보니까 그냥 이중 For문 느낌이어서 이해가 잘 되는 것 같다.

여전히 임의로 테이블을 만들어서 쿼리 연습을 해봐야 할 필요성을 느낀다 ㅋㅋ

# 궁금한 내용 / 부족한 내용


X

# 느낀점


2024/03/03(일) ~ 2024/03/07(금) 여행 이슈

너무 오래 놀고 왔더니 리듬이 깨진 느낌… 이번주 금토일 적당히 휴식하면서 잘 마무리하고 다음주부터 다시 잘 해봐야 할 것 같다!.