const FIRST_PAGE = 1;
let LAST_PAGE;

document.addEventListener('DOMContentLoaded', async function() {
    await changePage(FIRST_PAGE);
    let pageNavSection = document.querySelector('.pagination');
    makePageNaveBar(pageNavSection);
});

const changePage = async (pageNumber) => {
    const response =
        await fetch('/api/page/' + pageNumber, {method: 'GET'}).then(response => { return response.json()});

    LAST_PAGE = response.totalPages;

    let questionSection = document.querySelector(".list");
    const questionList = response.content;

    appendQuestionList(questionList, questionSection);
};

const makePageNaveBar = (pageNavSection) => {
    pageNavSection.innerHTML = pageNavSection.innerHTML + `<li onclick="changePage(${FIRST_PAGE})"><a class="page-link">«</a></li>`
    for (let pageNumber = FIRST_PAGE; pageNumber <= LAST_PAGE; pageNumber++) {
        pageNavSection.innerHTML = pageNavSection.innerHTML + `<li onclick="changePage(${pageNumber})"><a class="page-link">${pageNumber}</a></li>`
    }
    pageNavSection.innerHTML = pageNavSection.innerHTML + `<li onclick="changePage(${LAST_PAGE})"><a class="page-link">»</a></li>`
};

const appendQuestionList = (questionList, questionSection) => {
    document.querySelectorAll(".question").forEach(e => e.remove());

    questionList.forEach(question => {
        questionSection.innerHTML = questionSection.innerHTML + `
                <li class="question">
                  <div class="wrap">
                      <div class="main">
                          <strong class="subject">
                              <a href="/questions/${question.id}">${question.title}</a>
                          </strong>
                          <div class="auth-info">
                              <i class="icon-add-comment"></i>
                              <span class="time">${question.formattedCreatedAt}</span>
                              <a href="/users/{{writer.id}}" class="author">${question.writer.userId}</a>
                          </div>
                          <div class="reply" title="댓글">
                              <i class="icon-reply"></i>
                              <span class="point">${question.id}</span>
                          </div>
                      </div>
                  </div>
                </li>
              `
    })
};