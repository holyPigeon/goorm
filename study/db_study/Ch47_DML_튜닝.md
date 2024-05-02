# 공부한 내용

## 1. 인덱스 유지 비용

인덱스를 변경하는 행위는 단순히 테이블을 변경하는 행위보다 더 많은 비용이 생긴다.

## 2. Insert 튜닝

- Direct Path Insert를 사용하면 된다.
- 다음은 Direct Path Insert를 사용하는 방법이다.
    - insert select 문장에 /*+ append */ 힌트 사용
    - 병렬 모드로 insert
    - direct 옵션을 지정하고 SQL*Loader(sqlldr)로 데이터를 로드
    - CTAS(create table … as select) 문장을 수행
    - nologging 모드 Insert

## 3. Update 튜닝

아래와 같이 쓰면 상당히 오랜 시간이 소요된다.

```sql
update 주문 
set 상태코드 = '9999' 
where 주문일시 < to_date('20000101', 'yyyymmdd')
```

아래와 같이 쓰는 게 낫다.

```sql
create table 주문_임시 
as select * from 주문 where 주문일시 >= to_date('20000101', 'yyyymmdd'); 

alter table emp drop constraint 주문_pk; 
drop index 주문_idx1; 
truncate table 주문; 
insert into 주문 select * from 주문_임시; 
alter table 주문 add constraint 주문_pk primary key(고객번호, 주문일시); 
create index 주문_idx1 on 주문(주문일시, 상태코드);
```