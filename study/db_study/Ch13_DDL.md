# 공부한 내용


## 1. 데이터 유형

데이터베이스의 테이블에 데이터를 저장할 때는 데이터 유형이 있다. DB별로 문법이 다 다르긴 하지만, 대표적인 4가지의 데이터 유형이 있다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_162.jpg)

VARCHAR는 가변길이지만 CHARACTER는 고정길이이므로, 주민번호와 같이 웬만하면 고정인 칼럼을 제외하면 거의 VARCHAR를 쓰는 게 맞다.

## 2. CREATE TABLE

테이블 생성 시에는 각 칼럼의 PK, FK 제약조건을 잘 설정해야 한다. 잘 설정하지 않으면 당연하게도 이상현상이 생길 수 있다.

### 테이블 생성 쿼리

테이블을 생성할 수 있다.

```sql
CREATE　TABLE　테이블이름 ( 칼럼명1 DATATYP, 칼럼명2 DATATYPE, 칼럼명3 DATATYPE) ;
```

### 제약 조건

- `CONSTRAINT` 를 통해 제약 조건을 추가할 수 있다.
    - 칼럼의 데이터 유형 뒤에 적을 수 있다.
    - 또는 테이블 정의 쿼리 뒤에 정의할 수 있다.

제약 조건의 종류는 다음과 같다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_166.jpg

Default 라는 조건도 있는데, 이것은 해당 칼럼에 비어있는(null인) 칸이 있다면 기본으로 지정된 값을 넣어주는 기능이다.

### 생성된 테이블 구조 확인

테이블 구조를 확인할 수 있다.

```sql
DESCRIBE 테이블명;
```

### **SELECT 문장을 통한 테이블 생성 사례**

“AS SELECT절” 쿼리를 통해 테이블을 복제할 수 있다.

```sql
CREATE TABLE TEAM_TEMP AS SELECT * FROM TEAM;
```

## 3. ALTER TABLE

### ADD COLUMN

기존 테이블에 칼럼을 추가한다.

```sql
ALTER TABLE PLAYER ADD (ADDRESS VARCHAR2(80));
```

### DROP COLUMN

기존 테이블에서 칼럼을 제거한다.

```sql
ALTER TABLE PLAYER DROP COLUMN ADDRESS;
```

### MODIFY COLUMN

타입을 변경하거나, 디폴트 값을 바꾸거나, 제약조건을 변경한다.

```sql
ALTER TABLE 테이블명 MODIFY (칼럼명1 데이터 유형 [DEFAULT 식] [NOT NULL], 칼럼명2 데이터 유형 …);
```

### DROP CONSTRAINT

제약조건을 삭제한다.

```sql
ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건명;
```

### ADD CONSTRAINT

제약조건을 추가한다.

```sql
ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 제약조건 (칼럼명);
```

MODIFY COLUMN과 다른 점은, 제약 조건이 컬럼 뒤에 추가되지 않고 테이블 정의 뒤에 추가된다.

## 4. RENAME TABLE

테이블명을 변경할 수 있다.

```sql
RENAME TEAM TO TEAM_BACKUP;
```

## 5. **DROP TABLE**

테이블을 삭제할 수 있다.

```sql
DROP TABLE 테이블명 [CASCADE CONSTRAINT];
```

## 6. **TRUNCATE TABLE**

테이블의 구조는 남겨놓고, 안에 있는 **데이터만 모두 지우는 방식**이다.

```sql
TRUNCATE TABLE TEAM;
```

# 어려웠던 내용


딱히 없었던 것 같다.

# 궁금한 내용 / 부족한 내용


TRUNCATE가 뭘 의미하는지 몰라서 검색해봤는데, 테이블 구조는 유지하면서 그 안에 데이터만 지우는 명령어였다. 아래는 추가적으로 조사해본 내용이다.

## TRUNCATE을 사용하면 성능이 향상된다

TRUNCATE은 테이블의 구조는 그대로 남겨두고, 안에 있는 데이터만 전부 날리는 명령어이다. TRUNCATE 명령어를 사용하면 성능 향상을 경험할 수 있는데 왜 그럴까?

보통 한 테이블에서 데이터 수정, 삭제, 삽입이 여러 번 일어나게 되면 데이터의 물리적 저장 위치가 점점 흩어지게 되면서 디스크 I/O가 증가하게 되고, 이 과정에서 인덱스 역시 파편화를 겪게 된다.

이 때 TRUNCATE을 진행하게되면, 이러한 파편화가 사라지고 저장 구조가 최적화되면서 성능 향상 효과가 있다.

# 느낀점


기존에 알았던 명령어를 복습한 느낌이다. 문법 자체를 다 외우지는 못했는데, “이런 게 있었었지”라는 생각 정도는 있어서 나중에 모르는 거는 다 찾아볼 수 있을 것 같다.

DDL은 솔직히 범위가 작아서 크게 신경쓰이진 않고, 뒤에 쿼리 부분(DML)이랑 트랜잭션 쿼리(TCL) 부분이 좀 걱정된다 ㅋㅋ 파이팅해보자..