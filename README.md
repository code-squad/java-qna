# 질문 답변 게시판

#### 헤로쿠 배포 주소

https://serene-journey-91670.herokuapp.com/

## Step1

### 1. 학습정리

#### gradle

소프트웨어 빌드 자동화 시스템으로 `build.gradle` 파일을 통해 라이브러리 의존성 설정할 수 있다.

```
// 1.  플러그인 적용
apply plugin: 'java'

// 2.  라이브러리를 다운로드할 repository 설정
repositories {
    mavenCentral()
}

// 3. 의존성  설정
dependencies {
    compile 'org.springframework.boot:spring-boot-starter-web'
    testCompile 'org.springframework을.boot:spring-boot-starter-test'
}
```

##### 의존성 설정 옵션

1. compile: 프로젝트의 소스가 컴파일시 사용되는 설정
2. testCompile: 프로젝트의 테스트 소스가 컴파일될 때 요구되는설정

##### 의존성 명시하는 방법

```
// (방법 1) group: '~',, name: '~', version: '~' 형식순으로 명시한다.
compile group: 'org.springframework.boot', name: 'spring-boot-devtools', version: '2.2.4.RELEASE'

// (방법 2) 'group':'name':'version' 명시한다.
compile 'pl.allegro.tech.boot:handlebars-spring-boot-starter:0.3.0'
```


#### Live reloading

소스 파일이 수정되었을 때 재실행 없이 변경된 사항을 적용하는 방법이다.

1. spring-boot-devtools 의존성 주입
2. `application.properties` 파일에 내용 추가
```
spring.devtools.livereload.enabled=true
spring.freemarker.cache=false
```
3. File->Settings->Compiler->Build project automatically 체크
4. ctrl+shift+a->registry->compiler.allow.automake.when.app.running 체크
5. Edit configurations->Running Application Update Policies 설정

### 2. 고민사항

Question 객체에 writer 필드 타입을 User로 하고 싶었지만 input tag의 userId와 setMethod가 매핑 되어 있는 것 같아 일단 String 타입으로 지정해두었다.

### 3. 질문사항

html에서 하이퍼링크 경로를 작성할 때, 앞에 `/`를 붙이면 절대경로 `/` 없이 작성하면 상대경로로 동작하는 것 같습니다.
```html
<!-- 절대 경로 이동 -->
<form method="post" action="/users">
</form>

<!-- 상대 경로 이동 -->
<form method="post" action="users">
</form>
```
유지보수 관점에서 볼 때, 어는 방법이 더 좋을까요?
