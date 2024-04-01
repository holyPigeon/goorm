# 공부한 내용

## 1. 절차형 SQL 개요

프로그래밍 언어처럼 연속적인 실행이나 조건에 따른 분기, 반복이 가능한 SQL이다.

## 2. PL/SQL 개요

PL/SQL이 뭐냐 → 절차형 SQL을 영어로 한 것

### PL/SQL 특징

- 블럭 구조로 되어있다.
- 블럭 내부에서 DML, QUERY, 절차형 언어(IF, LOOP) 사용 가능
- PL/SQL을 이용하여 다양한 저장 모듈을 개발할 수 있다. (저장 모듈은 약간 함수같은 느낌?)
   - 저장 모듈의 종류는 다음과 같다.
   - Procedure
   - User Defined Function
   - Trigger
- 오라클과 PL/SQL을 지원하는 곳이라면 어디든지 프로그램을 이식 가능하다.
- SQL 쿼리를 블럭 단위로 보내기 때문에 성능상으로 좋다.

### PL/SQL 구조

크게 DECLARE / BEGIN / END로 나뉜다.

- DECLARE → `BEGIN ~ END` 절에서 사용될 변수와 인수에 대한 정의, 타입을 선언한다.
- BEGIN ~ END → SQL문 쿼리와 여러가지 비교, 제어문 등이 들어간다.
- EXCEPTION → `BEGIN ~ END` 절에서 발생한 에러를 핸들링한다.

### **PL/SQL 기본 문법(Syntax)**

프로시저의 생성 문법은 다음과 같다.

```sql
CREATE [OR REPLACE] 
Procedure [Procedure_name] 
( argument1 [mode] data_type1, argument2 [mode] date_type2, ... ... ) 
IS [AS] ... ... 
BEGIN ... ... EXCEPTION ... ... END; /
```

## 3. **T-SQL 개요**

T-SQL은 오라클이 아닌 SQL Server를 위한 절차형 언어이므로 스킵한다.

# 궁금한 내용 / 부족한 내용



# 느낀점

