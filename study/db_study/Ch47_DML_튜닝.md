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

