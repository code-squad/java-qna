# step1. 회원 가입 및 사용자 목록 기능 구현


# 회원가입, 사용자 목록 기능 구현

## 실습 진행 방법

- [질문답변 게시판에 대한 github 저장소](https://github.com/code-squad/java-qna)를 기반으로 실습을 진행한다.
- 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
- 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
- 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.



## 영상과의 차이점

- 배포는 heroku를 사용한다.
- 템플릿 엔진은 handlebars를 사용한다.
- 로깅 라이브러리는 log4j2를 사용한다.

---



## Step1 진행요약

진행하면서 간단한 느낀점입니다.

## 1.1 MVC 간단 설명 및 mustache 초간단 설명

templates!! 복수명이 중요하다. 단수명이면 작동하지 않음



## 1.2 회원가입 기능 구현

GetMapping 사용시 개인정보가 노출위험이 있음.

get -> post로 받을 것.

setter를 이용해 유저 정보 세팅 -> to String 할 것.

getter가 필요하다 getter넣지 않으면 출력이 안됨.

## 1.3 사용자 목록 기능 구현

mustache / handlebars: 반복문

Springboot에선 src/main/resources/static은 URL에서 / 로지정

도움이 되었던 블로그

[창천항로](https://jojoldu.tistory.com/255)

template으로 간 html은 해당 controller의 설정한 매핑으로 설정해야 가능. 일반경로는 static.

## 1.4 회원 프로필 정보보기

@PathVariable이란?

해당 파라메터를 사용하면 URI의 일부를 변수로 전달할 수 있다.

@GetMapping("/users/{barid}")

public String abc(@PathVariable String barid) {

}

자바 함수형이 중요하다!

stream을 대해 공부할 것.



## 1.5 HTML의 중복 제거

과감하게 없앨것. 해당 static html을 전부 templates폴더로 이동시켜야한다.

xxx.html에 중복될 것을 모두 넣고 해당 코드에

{{> xxx}} 넣으면 된다. (겁나쉬움)



### 1.5.2 URL과 html 쉽게 연결하기

config 패키지를 생성하고 MvcConfig이름으로 클래스를 생성한다.

```
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        
		registry.addViewController("URL주소").setViewName("template html명");
		
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/form").setViewName("user/form");
 }
}
```



## 1.6 질문하기, 질문목록 기능구현

위에 있는 문제와 비슷함.

그러나! 리스트가 안떠서 3시간동안 삽질함.

원인: URL과 html연결하기위해서 "/"를 넣은것이 패착. 이걸 지우니 바로 떳음.



## 1.7 질문 상세보기

```
{{#each abc}}
{{/each}}

{{#abc}}	
{{/abc}}
```

차이점이 뭘까?

setter를 따로 끄집어내서 index를 객체 id설정했다. 쓰지않고 하는방법은 없을까?



## 회원정보 수정은 다음기회에...