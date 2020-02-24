# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)

h2 데이터베이스 연결
- build.gradle 파일에 compile group: 'com.h2database', name: 'h2', version: '1.4.192' 추가해
- com.codessquad.qna.domain 디렉토리에 User 클래스 생성
- User 클래스를 db와 연결하기 위해 @Entity 라는 annotation import 하여 사용
- 각각의 데이터들을 고유하게 식별하기 위하여 primary key 지정, @Id 라는 annotation 사용
- 별도의 작업을 하지 않아도 하나의 데이터를 처리할 때마다 값이 1씩 증가하도록 해주는 @GeneratedValue 
Annotation 사용.
- db에 데이터를 추가 할 때 userId 와 같은 값들이 null 이 되면 안되기 때문에 @Column 이라는 annotation 사용하여
null값이 들어오지 못하게 설정. Column(nullable = false)