# Step6 
[배포 url : https://hyunjun2.herokuapp.com/](https://hyunjun2.herokuapp.com/)

## Step5 리뷰어 피드백 참고 리팩토링
- [X] Jquery도 좋지만 바닐라 JS fetch API와 DOM을 직접 해보기. 아니면 가볍게 React나 Vue.js 하루 공부해보기 -> 추후 공부
- [ ] URL에 update, delete 제거하기. 
- [ ] 사용자 정의 예외 만들어서 처리해보기 


## Step6 구현 
### PageRequest 이용해 질문 목록 나누기. 
- initPage를 통해 전체 질문 페이지 개수 구하기. 이 목적으로만 인덱스가 0인 페이지를 만드는데 리팩토링 필요.
- createPages를 통해 전체 페이지 개수에 맞게 페이지를 생성한다. 이 때 pageWrapper로 page를 감싼다. 이유는 page의 기본 인덱스는 0부터 시작하는데, 이러면 페이지 번호 기능 구현이 어렵기 때문이다. 그래서 인덱스가 1부터 시작하는 pageWrapper로 감싼다. 
- 위에서 구현한 인덱스를 통해 질문 목록 아래 보이는 페이지 번호를 구현한다. 1~5까지 각 인덱스에 맞는 page를 찾아서 footerPageNumbers에 넣은뒤 모델을 통해 view로 전달한다. 

```java
    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        this.totalPages = page.getTotalPages();
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> footerPageNumbers = getFooterPageNumbers(pageWrappers);
        model.addAttribute("pageWrappers", footerPageNumbers);
        model.addAttribute("questions", getCurrentPage(pageWrappers, 1));

        Next next = new Next();
        if (this.totalPages > lastPage) {
            model.addAttribute("next", next);
        }

        Prev prev = new Prev();
        if (this.firstPage != 1) {
            model.addAttribute("prev", prev);
        }
        return "/index";
    }
```

### 페이지 번호를 좌우로 이동하는 기능 구현 
- 오른쪽 >> 버튼은 페이지 번호가 마지막 그룹이라면 없앤다. 기준은 마지막 그룹의 마지막 번호가 전체 페이지 번호랑 같으면 지운다.
- 왼쪽 << 버튼은 페이지 번호가 첫번째 그룹이라면 없앤다. 기준은 현재 그룹의 첫번째 번호가 1이라면 지운다. 

```java
        Next next = new Next();
        if (this.totalPages > lastPage) {
            model.addAttribute("next", next);
        }

        Prev prev = new Prev();
        if (this.firstPage != 1) {
            model.addAttribute("prev", prev);
        }
        return "/index";
```

주의) << 버튼 클릭하고 현재 그룹의 첫번째 페이지 번호와 마지막 번호의 차가 4가 아니라면 마지막 번호를 첫번째 번호 +4로 만들어 준다. 즉, 계속 일정한 간격(현재는 5)으로 페이지 번호를 구성한다. 

```java
@GetMapping("/movePrev")
    public String movePrev() {
        this.firstPage -= 5;
        this.lastPage -= 5;
        if (lastPage - firstPage != 4) {
            lastPage = firstPage + 4;
        }
        return "redirect:/" + firstPage;
    }
```
## 질문 삭제 버그 
- 질문 삭제가 안될 때 : 답변의 삭제 button class의 이름과 질문 삭제 button class 이름이 같아서 질문 삭제를 누르면 답변 ajax로 넘어가기 때문에 에러가 발생한다.
- "link-delete-article" -> "link-delete-question-article"

```javascript
<button class="link-delete-question-article" type="submit">삭제</button>
```


## 질문 삭제 시 삭제한 질문 제외한 질문 목록 출력 기능 구현 
- 삭제 시 질문의 deleted 상태가 true로 변하기 때문에 QuestionRepostioy에 메서드를 추가한다. 

```java
Page findAllByDeletedFalse(Pageable pageable);
```

- 변경된 메서드로 질문을 가져온다. 

```java
public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, QUESTIONS_OF_PAGE);
        Page page = questionRepository.findAllByDeletedFalse(pageRequest);
        return page;
    }
```
