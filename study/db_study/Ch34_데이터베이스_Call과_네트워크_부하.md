# 공부한 내용

## 1. 데이터베이스 Call과 종류

### SQL 커서에 대한 작업 요청에 따른 분류

먼저 “커서”라는 개념에 대해 알아보고 가자. 커서란, 쿼리문에 의해 반환되는 결과값들을 저장하는 메모리 공간을 뜻한다.

쉽게 말해, SQL 파싱 및 최적화를 거쳐 도출된 실행계획에 따라 실제로 데이터를 검색하고, 그에 따른 결과집합을 반환하는 것이 바로 “커서”이다.

- 커서에 대한 작업 요청에 따른 분류는 다음과 같다.
  - Parse Call → SQL 파싱을 요청하는 Call
  - Execute Call → SQL 실행을 요청하는 Call
  - Fetch Call → SELECT문의 결과 데이터 전송을 요청하는 Call

### Call 발생 위치에 따른 구분

- User Call
  - DBMS 외부로부터 요청되는 Call
- Recursive Call
  - DBMS 내부로부터 요청되는 Call

음… 일단 User Call이 많이 생기면 안된다는 것을 지속적으로 강조한다. 다음과 같은 방법을 통해 User Call을 줄일 수 있다.

- **Loop 쿼리 해소와 집합적 사고**
  - 한 개의 쿼리를 여러 번 사용하지 말고, 집합으로 처리할 수 있는 쿼리로 한 번만 사용하자는 뜻이다.
- **Array Processing**
  - 이 역시 위와 비슷한 내용이다. 단일 행이 아닌 여러 행 단위로 처리하면 좋다.
- **효과적인 화면 페이지 처리**
  - 화면에 데이터를 표시할 때 한 번에 모든 데이터를 표시하는 것이 아닌, 필요한 부분만 데이터를 표시하는 것이 좋다.

    (ex. 게시판 페이지가 있을 때, 전체 5000페이지가 있더라도 사용자 입장에서는 대충 50페이지 이후부터는 볼 확률이 거의 없으므로 50페이지까지만 데이터를 로드한다.)

- **사용자 정의 함수/프로시저/트리거의 적절한 활용**
  - 이상하게 쓰면 안된다고 한다… 이건 잘 모르겠다

Recursive Call의 경우 SQL 파싱과 최적화 과정에서 발생하는 다양한 SQL 수행이 이에 해당한다.

Recursive Call을 최대한 줄이려면, 1차적으로 바인드 변수를 적극적으로 사용함으로써 하드파싱 횟수를 최대한 줄이는 것이 좋다. 2차적으로는 “사용자 정의 함수 / 프로시저 / 트리거”를 적절하게 활용해야 한다.

## 2. 데이터베이스 Call과 성능

### One SQL 구현의 중요성

One SQL을 사용하면 굉장한 성능상의 향상이 있다고 한다. 그 예를 살펴보자…

1번 코드 → 최대 110만번의 Call 발생

```java
public class JavaLoopQuery{ 
	public static void insertData(
	Connection con , 
	String param1 , 
	String param2 , 
	String param3 , 
	long param4
	) throws Exception{ 
	
		String SQLStmt = "INSERT INTO 납입방법별_월요금집계 " 
		+ "(고객번호, 납입월, 납입방법코드, 납입금액) " 
		+ "VALUES(?, ?, ?, ?)"; 
		
		PreparedStatement st = con.prepareStatement(SQLStmt); 
		st.setString(1, param1); 
		st.setString(2, param2); 
		st.setString(3, param3); 
		st.setLong(4, param4); 
		st.execute(); 
		st.close(); 
	} 
	
	public static void execute(
		Connection con, 
		String input_month
	) throws Exception { 
	
		String SQLStmt = "SELECT 고객번호, 납입월, 지로, 자동이체, 신용카드, 핸드폰, 인터넷 " 
		+ "FROM 월요금납부실적 " 
		+ "WHERE 납입월 = ?"; 
		
		PreparedStatement stmt = con.prepareStatement(SQLStmt); 
		stmt.setString(1, input_month); 
		ResultSet rs = stmt.executeQuery(); 
		
		while(rs.next()){ 
			String 고객번호 = rs.getString(1); 
			String 납입월 = rs.getString(2); 
			long 지로 = rs.getLong(3); 
			long 자동이체 = rs.getLong(4); 
			long 신용카드 = rs.getLong(5); 
			long 핸드폰 = rs.getLong(6); 
			long 인터넷 = rs.getLong(7); 
			
			if(지로 > 0) insertData (con, 고객번호, 납입월, "A", 지로); 
			if(자동이체 > 0) insertData (con, 고객번호, 납입월, "B", 자동이체); 
			if(신용카드 > 0) insertData (con, 고객번호, 납입월, "C", 신용카드); 
			if(핸드폰 > 0) insertData (con, 고객번호, 납입월, "D", 핸드폰); 
			if(인터넷 > 0) insertData (con, 고객번호, 납입월, "E", 인터넷); 
		} 
		
		rs.close(); 
		stmt.close(); 
	} 
	
	static Connection getConnection() throws Exception { …… } 
	static void releaseConnection(Connection con) throws Exception { …… } 
	
	public static void main(String[] args) throws Exception{ 
	
		Connection con = getConnection(); 
		execute(con, "200903"); 
		releaseConnection(con); 
	} 
}
```

2번 코드 → 단 2회의 Call 발생 (Parse Call 1회, Execute Call 1회)

```java
public class JavaOneSQL{ 
	public static void execute(
		Connection con, 
		String input_month) throws Exception { 
			String SQLStmt = "INSERT INTO 납입방법별_월요금집계" 
			+ "(납입월,고객번호,납입방법코드,납입금액) " 
			+ "SELECT x.납입월, x.고객번호, CHR(64 + Y.NO) 납입방법코드 " 
			+ " , DECODE(Y.NO, 1, 지로, 2, 자동이체, 3, 신용카드, 4, 핸드폰, 5, 인터넷) " 
			+ "FROM 월요금납부실적 x, (SELECT LEVEL NO FROM DUAL CONNECT BY LEVEL <= 5) y " 
			+ "WHERE x.납입월 = ? " 
			+ "AND y.NO IN ( DECODE(지로, 0, NULL, 1), DECODE(자동이체, 0, NULL, 2) " 
			+ " , DECODE(신용카드, 0, NULL, 3) , DECODE(핸드폰, 0, NULL, 4) " 
			+ " , DECODE(인터넷, 0, NULL, 5) )"; 
			
			PreparedStatement stmt = con.prepareStatement(SQLStmt); 
			stmt.setString(1, input_month); 
			stmt.executeQuery(); 
			stmt.close(); 
		} 
		
		static Connection getConnection() throws Exception { …… } 
		static void releaseConnection(Connection con) throws Exception { …… } 
		
		public static void main(String[] args) throws Exception{ 
			Connection con = getConnection(); 
			execute(con, "200903"); 
			releaseConnection(con); 
		} 
	}
```

→ 요약하자면, DB 입장에서 “간단한 쿼리를 반복해서 여러 번 날리는 것”이 “거대한 쿼리를 한 번에 날리는 것”보다 부담이 훨씬 크기 때문에 One SQL을 꼭 지향해야 한다.

사실 110만번 ↔ 2번이라는 Call 횟수 차이부터가 말도 안되기 때문에 “훨씬” 이란 말로도 부족한 것 같다. 차이가 압도적이다…!

### 데이터베이스 Call과 시스템 확장성

역시 DB Call을 줄이면 시스템의 처리 부하가 대폭 감소하며, 시스템의 확장성이 크게 향상된다는 얘기이다.

## 3. Array Processing 활용

앞과 비슷한 내용인데, 데이터베이스 작업을 개별적으로 실행하는 대신, 배열 단위로 데이터를 모아 한 번에 처리하는 모습을 보인다.

특히 **`BULK COLLECT`**와 **`FORALL`** 구문을 활용하여, 데이터를 1,000건씩 Fetch한 후 Bulk로 INSERT하는 방식을 보여준다.

벌크 연산을 적극 활용하자…!

## 4. Fetch Call 최소화

### 부분범위 처리 원리

DBMS는 클라이언트에게 데이터를 전송할 때 “한 번에 모든 데이터를 전송하는 것”이 아닌, “일정량씩 나누어 전송하는 것”을 택한다.

이 때, 일정량의 데이터만을 전송했는데 더 많은 데이터를 요구하는 것을 Fetch Call, 이 때의 추가적으로 전송되는 데이터의 사이즈를 Array Size(=Fetch Size)라고 한다.

대부분의 서비스에서는 이러한 기능을 활용하여 한 번에 모든 데이터를 보여주는 것이 아닌, 필요할 때만 Fetch Call을 사용해 추가적인 데이터를 불러오는 방식을 사용한다.

### **ArraySize 조정에 의한 Fetch Call 감소 및 블록 I/O 감소 효과**

어쨌든 간에 Fetch Call도 Call의 한 종류이기 때문에 오고가는 데에 통신 비용이 든다.

따라서 파일을 내려받는 경우처럼 어차피 모든 데이터를 전송해야할 상황이라면 가능한 Array Size를 크게 잡는 편이 Fetch Call의 횟수가 줄어서 좋다.

(네트워크 부하가 줄어들 뿐만 아니라 서버 프로세스가 읽어야 할 블록 개수까지 줄어드는 효과가 있다.)

다음과 같이, Fetch Count가 줄어들수록 Block I/O 또한 줄어드는 효과가 있다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_271.jpg)

그런데 주목할 점은, Array Size가 무작정 커진다고 해서 블록 I/O가 그만큼 적어지지 않는다는 것이다. 위 자료를 보면 Array Size = 100 이하부터는 블록 I/O에도 큰 변화가 없다.

## 5. 페이지 처리 활용

앞의 Fetch Call 내용을 참고해 Pagination을 구현 및 활용할 수 있다.

장점은 뭐 당연히 어마무시하고, 구현 같은 경우는 JPA에서 지원해주기 때문에 잠시 넘어가도록 하겠다..

## 6. 분산 쿼리

분산 데이터베이스 환경에서 분산 쿼리의 성능을 향상시킬 수 있는 방법에 대해 알아보자.

음… 잘은 모르겠지만 분산 쿼리의 성능을 높이는 핵심 원리는, 네트워크를 통한 데이터 전송량을 줄이는 데에 있다고 한다.





