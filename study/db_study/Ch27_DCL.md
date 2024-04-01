# 공부한 내용

## 1. DCL 개요

DCL은 데이터베이스 상에서 유저를 생성하고 권한을 관리할 수 있는 명령어이다.

## 2. 유저와 권환

데이터베이스에서 유저를 생성하고 권한을 부여하는 이유는 간단하다. 의도치 않은 접근으로 인한 데이터의 손실을 방지하기 위함이다.

오라클에서는 각자의 필요에 따라 권한을 부여한 기본 유저들이 존재한다.

![](https://prod-files-secure.s3.us-west-2.amazonaws.com/5486ac02-837a-4340-b853-a8cd7b03f65f/f91614f6-d417-482f-b924-4b57028bdc1e/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-04-01_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_3.32.43.png)

### **유저 생성과 시스템 권한 부여**

O 표시된 오브젝트에만 해당 명령어를 사용할 수 있다.

![](https://prod-files-secure.s3.us-west-2.amazonaws.com/5486ac02-837a-4340-b853-a8cd7b03f65f/fe72cead-1c63-4c3b-932a-bc2f764a06b0/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-04-01_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_3.37.19.png)

다음과 같이 PJS 유저로 접속하여 SCOTT 유저에게 MENU 테이블에 대한 권한을 부여할 수 있다.

```sql
Oracle CONN PJS/KOREA7 // 연결되었다.

INSERT INTO MENU VALUES (1, '화이팅'); // 1개의 행이 만들어졌다. 
COMMIT; // 커밋이 완료되었다. 
GRANT SELECT ON MENU TO SCOTT;
```

## 3. Role을 이용한 권한 부여

위처럼 유저에게 직접 권한을 부여할 수도 있지만, 데이터베이스에 존재하는 권한은 100가지가 넘기 때문에, 보통은 ROLE을 이용하여 권한을 부여한다. 유저에게 권한이 아닌 ROLE을 부여하는 것이다.

오라클에서 가장 많이 사용하는 ROLE은 CONNECT와 RESOURCE이며,

![스크린샷 2024-04-01 오후 3.53.55.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/5486ac02-837a-4340-b853-a8cd7b03f65f/5b8b66f7-3cb5-4a6d-8803-f7152337624a/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-04-01_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_3.53.55.png)

추가로, 유저 삭제 명령어인 `DROP <사용자>`는  `CASCADE` 옵션과 같이 쓰면 해당 유저가 만든 오브젝트까지 같이 삭제한다.

다음은 예시 코드다.

```sql
Oracle CONN SYSTEM/MANAGER // 연결되었다. 

DROP USER JISUNG CASCADE; // 사용자 삭제, JISUNG 유저가 만든 MENU 테이블도 같이 삭제 
CREATE USER JISUNG IDENTIFIED BY KOREA7; // 사용자가 생성되었다. 
GRANT CONNECT, RESOURCE TO JISUNG; //권한이 부여되었다. 

CONN JISUNG/KOREA7 // 연결되었다. 

CREATE TABLE MENU ( MENU_SEQ NUMBER NOT NULL, TITLE VARCHAR2(10)); // 테이블이 생성되었다.
```

# 궁금한 내용 / 부족한 내용

## 🤔 권한 부여는 실무에서 어떻게 사용할까?

권한 부여와 같은 기능을 실무에서 사용할 일이 있다면 정확히 프로세스가 어떻게 되는지 궁금해서 오라클 기준으로 찾아보았다.

### 조건

- 오라클을 사용중이다.
- 현재 나는 A부서에서 일하고 있으며, B부서에 오라클 서버 상의 테이블을 보여줄 일이 있다.
- 테이블 이름은 events며, 수정 및 삭제는 불가하고 조회만 하게 유저 권한 설정을 하고 싶다.

### 프로세스

1. **DBA, 또는 해당 권한이 있는 사용자로 로그인한다.**
2. B부서 직원이 사용할 사용자 계정을 생성하고 해당 계정에 SELECT 권한 부여

    ```sql
    CREATE USER b_dept IDENTIFIED BY <password>; // 사용자 생성
    GRANT CREATE SESSION TO b_dept; // 로그인 권한 부여
    GRANT SELECT ON events TO b_dept; // events 테이블 조회 권한 부여
    ```

3. 이후 B부서에서는 b_dept 계정을 통해 로그인하여 테이블에 접근하면 된다.

# 느낀점

이번 챕터는 나름 EZ했던 것 같다. 뭔가 실제로 사용하는 프로세스에 대해서도 생각해보니까 더 이해가 잘 되는 것 같다.

물론 권한 종류가 너무 많아서 나중에 쓸 때는 보면서 해야겠지만 그래도 뿌듯하다 ^^