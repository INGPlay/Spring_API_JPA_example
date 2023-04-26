# **Notebook_API**
개인 프로젝트  

<br>

# 개요
- JPA를 활용한 REST API 프로젝트.
- 학습을 위해 EntityManager를 사용하는 Hibernate JPA로 작성함.

<br>

# 목표
- 계층관계를 나타내는 REST API 작성
- API 스펙 작성

# 사용 기술
> ## BackEnd
- JDK 11
- Spring Boot v2.7.10
- Spring Data JPA
- Hibernate JPA
- Lombok

> ## Database
- H2 DB
- Maria DB

> ## FrontEnd
- Swagger UI

> ## IDE
- IntelliJ IDEA

# 구조도
> ## 1. ERD
![ERD](images/Notebook_ERD.png)

- 각 사용자의 데이터의 무결성을 지키고자 User와 Container가 1:N, Container와 Post가 또 1:N 관계가 맺어짐  

- User와 연결된 ShortCut 테이블은 즐겨찾기 기능 같은 것을 생각해서 생성  

- User, Container, Post 에 공통된 생성 시간과 수정 시간은 JPA의 embedded 타입으로 참조하게 함

- 사용자의 권한을 구분하기 위해 Enum 타입 사용

- 작성중...


# 한 것들
- DB 구성
- REST API 코드 작성
- JPA 코드 작성

# 하면서 깨달은 것

1. JPA Entity 작성 시 자주 Update 되는 컬럼을 모아 따로 Embeddable 같은 객체로 만들고 Entity에서 참조해야 더 사용하기 편하게 될 듯하다.

2. Response Body를 어떻게 줘야 더 좋을지에 대한 고민이 있다. 이거는 프론트 단에 대한 이해가 부족해서이지 않을까 생각한다.

3. API에서 인증방식으로 JWT를 많이 선택하는데, Spring에서는 그것을 Spring Security와 연동하는 방식을 취하는 방식이 많은 듯하다. 그래서 그것을 해보려고 했는데, 이해가 안된다. JWT 방식에서 인증하고 토큰을 발급하는데 여러 번의 통신이 이루어지는데, 이것을 코드로 봐도 이해를 잘 못하겠다. 그냥 코드를 가져다 쓸 수도 있겠지만, 보안에 관련된 만큼 이해가 중요하지 않을까 생각된다.
    - 일단 Spring Security 실행 단계에 대한 학습이 필요하지 않나 생각한다. 그 후에 JWT 인증을 다시 생각해본다.


# 추가로 학습해야할 거...
- 프론트 단에서 API를 받는 방식에 대한 이해
- Spring Security and JWT
- Spring Filter