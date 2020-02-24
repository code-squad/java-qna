# Step 2

헤로쿠 배포 주소

https://serene-journey-91670.herokuapp.com/

## 1. 학습정리

### 1. 웹 3 계층 구성

#### 웹 서버

클라이언의 HTTP 요청을 가장 먼저 받는다. 보통 웹뜻 서버에 하나 이상의 에플리케이션이 존재하거나, 간단한 로드 밸런싱을 하기 위해 사용한다. Apache, nginx가 존재한다.

#### 어플리케이션 서버

개발자가 구현한 비지니스 로직을 실행하는 계층이다. 톰켓은 요청에 맞는 서블릿을 통해 로직을 수행하고 응답한다.

#### 데이터베이스

데이터의 영속성을 유지하기 위해 존재한다. MySQL, Oracle, PostgreSQL, MongoDB 등이 존재한다. 

### 2. Servlet thread issue

서블릿의 서비스 메소드는 요청이 발생했을 때, 새로운 스레드를 생성해서 응답을 처리하기 때문에 요청을 병렬적으로 처리할 수 있다. 하지만, 생성된 쓰레드가 인스턴스 변수를 통해 동시에 같은 메모리에 접근할 수 있기 때문에 예상치 못한 결과를 얻을 수 있다.

> Reference
>
> https://stackoverflow.com/questions/9555842/why-servlets-are-not-thread-safe/
> https://www.geeksforgeeks.org/introduction-java-servlets/
> https://docs.oracle.com/cd/E19146-01/819-2634/abxbh/index.html


### 3. HTTP의 query parameter vs body

HTTP 프로토콜에서 GET 메소드는 query parameter를 통해 POST 메소드는 body를 통해 필요한 인수를 서버에게 전송한다. 기본적으로 로그인을 GET 메소드로 처리할 경우 url에 패스워드가 포함되기 때문에 보안성 좋지 않기 때문에 query parameter 방식을 사용하지 않는다. 그렇다면 왜 GET 메소드는 body에 인수를 담지 않고 query parameter를 사용할까?

URL이 같은 GET 요청에 같은 페이지를 제공하기 위해서라고 생각한다. 예를 들면 사용자 리스트를 보여주는 페이지의 경우 url에 `?page=3&limit=10` 페이지 번호와 유저 수가 존재하기 때문에 사용자는 같은 url을 통해 서버는 동일한 페이지를 제공할 수 있다.
## 2. 질문사항

### 1. put 메소드는 완전한 entity를 서버에게 제공해햐 하는가?

PUT 메소드와 PATCH 메소드에 대해서 공부했습니다. put 메소드는 요청할 때 완전한 entity를 제공해서 resource를 변경하는 반면 patch 메소드는 제공된 필드만 리소스를 변경하기 때문에 멱등하지 않다고 이해했습니다. 그렇다면 개인정보를 수정할 때, 사용자로부터 필요한 모든 필드(아이디, 비밀번호 등)를 제공 받을 수 있게 form 페이지를 구성하는 것이 맞을까요?

저는 이 방법이 이상하다고 느껴서 patch 메소드를 이용해 구현했습니다.

> Reference
>
> https://stackoverflow.com/questions/28459418/rest-api-put-vs-patch-with-real-life-examples

```
# 수정 전
GET /users/1
{"userId": "kyungrae", "password": "1234", "nickName: "dingo"}

# PUT 메소드
PUT /users/1
{
    "userID": "kyungrae",
    "password": "1234",
    "nickName": "question"
}

# 수정 후
GET /users/1
{"userId": "kyungrae", "password": "1234", "nickName: "question"}
```

```
# 수정 전
GET /users/1
{"userId": "kyungrae", "password": "1234", "nickName: "dingo"}

# PUT 메소드
PUT /users/1
{
    "userID": "kyungrae",
    "password": ,
    "nickName": "question"
}

# 수정 후
GET /users/1
{"userId": "kyungrae", "password": null, "nickName: "dingo"}
```
