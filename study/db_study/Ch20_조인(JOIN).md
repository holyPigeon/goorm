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

# 어려웠던 내용


# 궁금한 내용 / 부족한 내용


# 느낀점
