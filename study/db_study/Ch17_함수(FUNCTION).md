# 공부한 내용


## 1. 내장 함수 개요

단일행 함수의 종류는 다음과 같다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_181.jpg)

SELECT, WHERE, ORDER BY절 등에서 사용할 수 있다. 근데 솔직히 이거 다 외우는 건 에바고 존재만 알아둔 다음에 필요할 때 써먹어면 좋을 것 같다.

## 2. 문자형 함수

단일형 함수 중에서 문자형 함수의 종류이다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_182.jpg)

## 3. 숫자형 함수

단일형 함수 중에서 숫자형 함수의 종류이다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_184.jpg)

## 4. 날짜형 함수

단일형 함수 중에서 날짜형 함수의 종류이다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_186.jpg)

## 5. 변환형 함수

다음과 같은 변환형 함수들이 있다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_189.jpg)

## 6. CASE 표현

다소 생소하긴 한데, 프로그래밍 언어에서 IF절과 같은 표현이다.

CASE 표현의 종류다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_190.jpg)

## 7. NULL 관련 함수

### NVL/ISNULL 함수

NVL과 ISNULL은 똑같은 기능인데 벤더마다 이름만 다르다.

NULL 관련 함수의 종류다.

![](https://dataonair.or.kr/publishing/img/knowledge/SQL_192.jpg)

# 어려웠던 내용


외우는 게 제일 어렵다… 일단 DB상에 이런 다양한 함수들이 존재한다는 것만 알고 나중에 필요할 때 다시 찾아보는 게 좋을 것 같다.

# 궁금한 내용 / 부족한 내용


## Q1. DB에 존재하는 이러한 유틸 함수들은 어플리케이션에서도 똑같이 구현이 가능한데 차이점은?

DB에서 내장 함수를 사용하면 일단 성능적으로 더 뛰어나고, 데이터 일관성과 무결성을 유지하기 더 쉽다.

반면 어플리케이션에서 데이터를 가공한다면 성능적으로는 조금 밀리나, 더 복잡한 기능과 연산을 사용할 수 있기 때문에 유연하고, 유지보수나 코드 재사용 면에서 더 편리하다.

결과적으로 이 둘 사이에는 어느정도의 Trade-Off가 있기 때문에 성능이 비교적 중요한 곳에는 직접 내장 함수를 사용한 쿼리를 쓰고, 성능이 딱히 중요하지 않은 곳에는 기본 데이터를 불러와서 어플리케이션에서 가공하는 게 더 좋을 것 같다.

# 느낀점


그냥 보고 넘어가는 내용인 것 같다 ㅋㅋㅋ 다음 챕터부터 잘 해야 된다…