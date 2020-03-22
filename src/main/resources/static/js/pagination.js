const FIRST_PAGE = 1;
const NUMBER_OF_BUTTON = 5;
let LAST_PAGE;
let section = 1;
let pageNavSection = document.querySelector('.pagination');

document.addEventListener('DOMContentLoaded', async function() {
    await changePage(FIRST_PAGE);
    makePageNaveBar();
});

const changePage = async (pageNumber) => {
    const response =
        await fetch(`/api/questions?page=${pageNumber}`, {method: 'GET'}).then(response => { return response.json()});

    LAST_PAGE = response.totalPages;

    let questionSection = document.querySelector(".list");
    const questionList = response.content;

    appendQuestionList(questionList, questionSection);
};

const changePageNavSection = (command) => {
    if (command === 'up') {
        section = Math.min(section+1, Math.ceil(LAST_PAGE/5));
        makePageNaveBar();
        return;
    }

    if (command === 'down') {
        section = Math.max(1, section-1);
        makePageNaveBar();
        return;
    }
}

const makePageNaveBar = () => {
    document.querySelectorAll(".page-button").forEach(el => el.remove());

    if (LAST_PAGE <= NUMBER_OF_BUTTON) {
        appendPageButton(FIRST_PAGE, LAST_PAGE);
        return;
    }

    const lastButton = Math.min(section * NUMBER_OF_BUTTON, LAST_PAGE);
    const startButton = (section-1) * NUMBER_OF_BUTTON + 1;

    pageNavSection.innerHTML = pageNavSection.innerHTML + `<li class="page-button" onclick="changePageNavSection('down')"><a class="page-link">«</a></li>`
    appendPageButton(startButton, lastButton);
    pageNavSection.innerHTML = pageNavSection.innerHTML + `<li class="page-button" onclick="changePageNavSection('up')"><a class="page-link">»</a></li>`
}

const appendPageButton = (startButton, lastButton) => {
    for (let pageNumber = startButton; pageNumber <= lastButton; pageNumber++) {
        pageNavSection.innerHTML = pageNavSection.innerHTML + `<li class="page-button" onclick="changePage(${pageNumber})"><a class="page-link">${pageNumber}</a></li>`
    }
}

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