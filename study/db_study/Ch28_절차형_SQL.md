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

## 4. Procedure의 생성과 활용

SCOTT 유저가 소유하고 있는 DEPT 테이블에 새로운 부서를 등록하는 과정을 다음과 같은 플로우차트로 나타낼 수 있다.

!https://dataonair.or.kr/publishing/img/knowledge/SQL_233.jpg

예시 프로시저 코드이다. → 개지옥이다 ㅋㅋ 써도 지피티한테 부탁하지 직접 쓸 일은 절대 없을 듯

```sql
CREATE OR REPLACE 
Procedure p_DEPT_insert 
------------- ① 
( v_DEPTNO in number, v_dname in varchar2, v_loc in varchar2, v_result out varchar2) IS cnt number := 0;

BEGIN 
	SELECT COUNT(*) 
	INTO CNT 
	------------- ② 
	FROM DEPT 
	
	WHERE DEPTNO = v_DEPTNO AND ROWNUM = 1; 
	
	if cnt > 0 then 
	------------- ③ 
	v_result := '이미 등록된 부서번호이다'; 
	
	else INSERT INTO DEPT (DEPTNO, DNAME, LOC)
	------------- ④ 
	VALUES (v_DEPTNO, v_dname, v_loc); 
	
	COMMIT; 
	------------- ⑤ 
	v_result := '입력 완료!!'; 
	
	end if; 
	
	EXCEPTION 
	-------------⑥ 
	WHEN OTHERS THEN ROLLBACK; v_result := 'ERROR 발생'; 
	
END; /
```

## **5. User Defined Function의 생성과 활용**

말 그대로 사용자 정의 함수이다. 프로시저와 거의 비슷한데, 하나 다른점은 함수이므로 반드시 하나의 return 값이 존재한다는 점이다.

코드는 다음과 같다.

```sql
CREATE OR REPLACE Function UTIL_ABS (v_input in number) 

---------------- ① 
return NUMBER IS v_return number := 0; 

---------------- ② 
BEGIN 

	if v_input < 0 then 
	---------------- ③ 
	v_return := v_input * -1; 
	else v_return := v_input; 
	end if; 
	RETURN v_return; 

---------------- ④ 
END; /
```

## **6. Trigger의 생성과 활용**

트리거란, 말 그대로 어떤 상황이 발생했을 때 트리거가 되어 자동으로 실행되는 로직을 뜻한다.

주로 INSERT, UPDATE와 같은 DML문이 수행되었을 때, DB 상에서 자동으로 로직을 실행한다.

예제 코드

```sql
CREATE OR REPLACE Trigger SUMMARY_SALES

 ---------------- ① 
 AFTER INSERT ON ORDER_LIST FOR EACH ROW DECLARE 
 ---------------- ② 
 o_date ORDER_LIST.order_date%TYPE; o_prod ORDER_LIST.product%TYPE; 
 BEGIN 
	 o_date := :NEW.order_date; 
	 o_prod := :NEW.product; 
	 UPDATE SALES_PER_DATE 
	 ---------------- ③ 
	 SET qty = qty + :NEW.qty, amount = amount + :
	 NEW.amount WHERE sale_date = o_date AND product = o_prod; 
	 if SQL%NOTFOUND then 
	 ---------------- ④ 
	 INSERT INTO SALES_PER_DATE VALUES(o_date, o_prod, :NEW.qty, :NEW.amount); 
	 end if; 
 END; /
```

스읍… 이것도 솔직히 쓸 일이 있을지 모르겠다..

## **7. 프로시저와 트리거의 차이점**

- 프로시저
   - BEGIN ~ END 절 내에 COMMIT, ROLLBACK과 같은 트랜잭션 종료 명령어를 사용 가능

- 데이터베이스 트리거
   - BEGIN ~ END 절 내에 사용 불가능

# 궁금한 내용 / 부족한 내용

1. 이러한 절차형 SQL의 경우 실무에서는 어떤 상황과 조건에서 쓰는 것인지 궁금하다.
2. 이 기능을 실제로 자주 쓰는지 궁금하다. (ㅋㅋㅋ)

## 🤔 절차형 SQL은 실무에서 어떻게 쓰일까?

### 절차형 SQL 개요

절차형 SQL의 종류인 프로시저, 사용자 정의 함수, 그리고 트리거.

간단하게 요약하면 DB 내에서 쿼리도 다루고, 이를 활용한 로직 역시 같이 다루는 것으로 볼 수 있다.

일반적인 경우에서 DB는 CRUD 등의 간단한 작업만 수행하고, 나머지 이를 활용한 로직은 어플리케이션에서 작성하는 경우가 많은데, 절차형 SQL을 통해 이 모든 과정을 DB 내에서 진행할 수 있다.

### 성능 향상과 유지/보수 사이의 선택

프로시저는 최소한 크기의 요청과 응답, 최소한의 통신으로 동작하기 때문에 당연하게도 향상된 성능을 기대할 수 있다는 장점이 있다. 실제로 방대한 데이터를 다루는 대기업에서는 프로시저를 사용하는 경우가 다수 있다고 한다.

(트리거의 경우는 후폭풍이 심해 거의 사용하지 않는다고 한다.)

반면 형상관리가 쉽지 않다는 단점도 있다. 어플리케이션 코드의 경우 깃허브 등의 툴로 형상관리가 가능하지만, SQL에서 동일한 작업을 하려면 수동으로 스크립트를 추출하고 관리해야 한다.

### 결론

커뮤니티 다수의 의견을 보았을 때, 현재로서는 JPA 사용만으로도 꽤 높은 수준의 성능을 낼 수 있고, 프로시저는 앞선 단점들로 인해 자주 사용되는 선택지는 아니다.

하지만 정말 방대한 양의 데이터를 처리해야하는 상황에서는, 조금이라도 높은 성능을 추구하는 게 맞기 때문에 프로시저를 사용해도 괜찮다.

(하지만 그 정도 규모의 회사에서 백엔드 개발자가 프로시저를 만질 일이 있을지는 솔직히 잘 모르겠다..)

# 느낀점

음… 뭔가 이런 기능이 있다는 것도 알겠고, 필요하다는 것도 알겠는데…

그걸 굳이 내가 쓸 일이 있나…? 라는 생각이 든다 ㅋㅋ

아… 쉽지 않다. 일단 알고만 넘어가는 걸로 결정