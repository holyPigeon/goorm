# 공부한 내용


## 1. INSERT

테이블에 데이터를 삽입한다.

```sql
 INSERT INTO 테이블명 (COLUMN_LIST)VALUES (COLUMN_LIST에 넣을 VALUE_LIST);
```

## 2. UPDATE

데이터를 수정한다.

```sql
UPDATE 테이블명 SET 수정되어야 할 칼럼명 = 수정되기를 원하는 새로운 값;
```

## 3. DELETE

데이터를 모두 삭제한다.

```sql
DELETE [FROM] 삭제를 원하는 정보가 들어있는 테이블명;
```

뒤에 WHERE 절을 붙이면 해당하는 로우만 삭제한다.

```sql
DELETE [FROM] 삭제를 원하는 정보가 들어있는 테이블명 WHERE 칼럼 = 칼럼값;
```

## 4. SELECT

데이터를 조회한다. * → 와일드카드라고 부른다.

```sql
SELECT * FROM PLAYER;
```

`DISTINCT` 키워드를 붙이면 중복을 제거한 결과를 반환한다.

```sql
SELECT DISTINCT PLAYER_NUMBER FROM PLAYER;
```

`AS` 키워드를 통해 별칭을 지정할 수 있다.

```sql
	SELECT 별칭1.칼럼1, 별칭1.칼럼2, 별칭1.칼럼3 FROM 테이블1 AS 별칭1;
```

## 5. 산술 연산자와 합성 연산자

산술 연산자는 다음과 같다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_169.jpg)

합성 연산자는 다음과 같이 사용 가능하다.

```sql
SELECT PLAYER_NAME || '선수,' || HEIGHT || 'cm,' || WEIGHT || 'kg' 체격정보 
FROM PLAYER;
```

# 어려웠던 내용


딱히 어려웠던 내용은 없긴 했는데, 제약 조건 쪽이 깊게 들어가면 좀 복잡해서 아마 찾아보면서 써봐야할 것 같다.

합성 연산자도 기억이 가물가물했는데, 보니까 대충 무슨 말인지는 알 것 같다. 근데 CONCAT 연산을 하더라도 어차피 어플리케이션 상에서 하는 게 더 편리하고 수정도 용이할텐데 굳이 SQL 쿼리에서? 저렇게 할 필요가 있나 싶긴 하다.

# 궁금한 내용 / 부족한 내용


딱히 없는 듯..

# 느낀점


쿼리의 기본을 배운 느낌이다. 뒤에 심화 쿼리 내용이 나오는데, 마음의 준비를 해둬야겠다 ㅋㅋㅋ