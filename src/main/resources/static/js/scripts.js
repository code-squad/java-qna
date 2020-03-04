$(".answer-write button[type=submit]").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article",
    deleteAnswer);
$(".link-modify-article").click(updateAnswer);

function onError() {
  console.log("error")
}

function addAnswer(e) {
  e.preventDefault();
  let queryString = $(".answer-write").serialize();

  let url = $(".answer-write").attr("action");

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: onError,
    success: function (data) {
      if (data.valid) {
        let answerTemplate = $("#answerTemplate").html();
        let answer = data.answer;
        let question = data.question;
        let template = answerTemplate.format(
            answer.writer,
            answer.createdTime,
            answer.contents,
            answer.question.id,
            answer.id);
        $(".qna-comment-slipp-articles").prepend(template);
        $(".qna-comment-count strong").html(question.countOfAnswer);
        $("textarea[name=contents]").val("");
      } else {
        alert(data.errorMessage);
      }

    }
  });
}

function updateAnswer(e) {
  e.preventDefault();
  let updateBtn = $(this);
  console.log(updateBtn);
}

function deleteAnswer(e) {
  e.preventDefault();
  var deleteBtn = $(this);
  // console.log(deleteBtn);
  var url = deleteBtn.attr("href");
  // console.log(url);

  $.ajax({
    type: 'DELETE',
    url: url,
    dataType: 'json',
    error: onError,
    success: function (data, status) {
      // console.log(data);
      if (data.valid) {
        var question = data.question;
        deleteBtn.closest("article").remove();
        $(".qna-comment-count strong").html(question.countOfAnswer);
      } else {
        alert(data.errorMessage);
      }
    }
  })
}

String.prototype.format = function () {
  let args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};






