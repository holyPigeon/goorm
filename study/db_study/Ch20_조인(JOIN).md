# 공부한 내용


## 1. JOIN 개요

두 개 이상의 테이블을 연결, 결합하는 행위를 JOIN 이라고 한다. 보통 JOIN은 PK나 FK 중심으로 이루어지지만 그렇지 않은 경우도 있다.

JOIN 연산 자체는 2개의 테이블 대상으로 진행된다.

(ex. 테이블 1, 2, 3 총 3개가 있으면 1 + 2가 먼저 JOIN되고 그 다음에 3과 JOIN된다.)

## 2. EQUI JOIN

두 개 테이블 간의 컬럼값이 정확하게 일치할 때 사용되는 JOIN 방법이다.

```sql
SELECT d.deptname, e.ename 
FROM dept d, emp e 
WHERE e.deptno = d.deptno;
```

EQUI JOIN은 보통 PK ↔ FK의 관계를 기반으로 성립되지만, 그렇지 않은 경우도 있다.

아래와 같이 INNER JOIN을 사용하여 동일한 기능을 하는 쿼리를 생성할 수 있다.

```sql
<일반적 케이스>

SELECT PLAYER.PLAYER_NAME 선수명, TEAM.TEAM_NAME 소속팀명 
FROM PLAYER, TEAM 
WHERE PLAYER.TEAM_ID = TEAM.TEAM_ID; 

<INNER JOIN 사용 케이스>

SELECT PLAYER.PLAYER_NAME 선수명, TEAM.TEAM_NAME 소속팀명 
FROM PLAYER 
INNER JOIN TEAM 
ON PLAYER.TEAM_ID = TEAM.TEAM_ID;
```

예시는 다음과 같다.

축구 선수들이 있는데, 개별 선수들의 팀 이름이나 연고지를 알고 싶을 때 EQUI JOIN을 사용 가능하다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_196.jpg

당연하게도, 아래와 같이 뒤에 여러가지 조건을 추가할 수 있다.

```sql
<일반적 케이스>

SELECT P.PLAYER_NAME 선수명, P.BACK_NO 백넘버, T.REGION_NAME 연고지, T.TEAM_NAME 팀명 
FROM PLAYER P, TEAM T 
WHERE P.TEAM_ID = T.TEAM_ID AND P.POSITION = 'GK' 
ORDER BY P.BACK_NO; 

<INNER JOIN 사용 케이스>

SELECT P.PLAYER_NAME 선수명, P.BACK_NO 백넘버, T.REGION_NAME 연고지, T.TEAM_NAME 팀명 
FROM PLAYER P 
INNER JOIN TEAM T 
ON P.TEAM_ID = T.TEAM_ID 
WHERE P.POSITION = 'GK' 
ORDER BY P.BACK_NO;
```

꼭 주의할 점은, 칼럼 앞에 꼭 테이블 별칭을 붙여주어야 한다는 것이다.

일단 당장 칼럼 중복이 있으면 쿼리가 작동하지 않게 되고, 중복이 없더라도 추후에 다른 칼럼이 추가되면서 중복 문제가 생길 수 있기 때문에 꼭 조심해야 한다.

## 3. Non EQUI JOIN

다음과 같이 꼭 “=” 연산자를 사용하지 않는 Non EQUI JOIN도 가능하다.

```sql
SELECT E.ENAME 사원명, E.SAL 급여, S.GRADE 급여등급 
FROM EMP E, SALGRADE S 
WHERE E.SAL BETWEEN S.LOSAL AND S.HISAL;
```

## 4. 3개 이상의 TABLE JOIN

서로 연관관계가 없는 두 테이블의 경우, 중간에 매개체가 되는 테이블이 껴서 총 3개 이상을 JOIN 해야 하는 상황이 발생할 수 있다.

아래와 같이 WHERE 절에 “=” 연산자가 AND를 통해 2번 들어가는 식으로 쿼리가 구성된다.

```sql
SELECT P.PLAYER_NAME 선수명, P.POSITION 포지션, T.REGION_NAME 연고지, T.TEAM_NAME 팀명, S.STADIUM_NAME 구장명 
FROM PLAYER P, TEAM T, STADIUM S 
WHERE P.TEAM_ID = T.TEAM_ID AND T.STADIUM_ID = S.STADIUM_ID 
ORDER BY 선수명; 
```

# 어려웠던 내용


# 궁금한 내용 / 부족한 내용


# 느낀점
