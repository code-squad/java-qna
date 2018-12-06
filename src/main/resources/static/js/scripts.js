$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("add answer");
    e.preventDefault(); // 클릭이벤트 막아주는

   var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
   console.log("query : "+ queryString);

   var url = $(".submit-write").attr("action");
       console.log("url : " + url);

    $.ajax({
           type : 'post',
           url : url,
           data : queryString,
           dataType : 'json',
           error: onError,
           success : onSuccessAddAnswer,
       });
}

function onError() {
    console.log('error');
    location.href = "/user/login"
}

function onSuccessAddAnswer(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);
    $(".qna-comment-count strong").html(data.question.answersSize);
    console.log("answersize : " + data.question.answersSize)
    $("textarea[name=contents]").val("");
}

//$("a.link-delete-article").click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
      console.log("deleteAnswer");
      e.preventDefault(); // 클릭이벤트 막아주는

      var deleteBtn = $(this);
      var url = $(this).attr("href");
      console.log("url : " + url);

      $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);
            if (data.valid) {
                  console.log(data);
                deleteBtn.closest("article").remove();
                 $(".qna-comment-count strong").html(data.object.countOfAnswer);
            } else {
                alert(data.errorMessage);
            }
        }
      });

/*
    var queryString = $(".delete-answer-form").serialize();
    console.log("query : " + queryString);

    var url = $(".delete-answer-form").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onErrorDeleteAnswer,
        success : onSuccessDeleteAnswer,
    });
    */

}

function onSuccessDeleteAnswer(data, status) {
    console.log('delete success');
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

function onErrorDeleteAnswer() {
    console.log('delete error');
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};