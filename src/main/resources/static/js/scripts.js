$(".answer-write input[type='submit']").on("click", addAnswer);

function addAnswer(e) {
  console.log("click me");
  e.preventDefault();

  var queryString = $(".answer-write").serialize();
  console.log("query: " + queryString);

  var url = $(".answer-write").attr("action");
  console.log("url: " + url);

  $.ajax({
    type : "post",
    url : url,
    data : queryString,
    dataType : "json",
    error : onError,
    success : onSuccess
  });
}

function onError() {
  console.log("오류");
}

function onSuccess(data, status) {
  console.log("성공");
  console.log(data);
  console.log(status);
  var count = $("p.qna-comment-count");
  console.log("카운트 : " + count);

  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(data.writer.name, data.createDate, data.contents, data.question.id, data.id);
  $(".qna-comment-slipp-articles").prepend(template);
  //댓글창 초기화
  $("textarea[name=contents]").val("");

}

//동적으로 html 추가
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

  var deleteBtn = $(this);
  var url = deleteBtn.attr("href");
  console.log("url : " + url);

  $.ajax({
     type : "delete",
      url : url,
      dateType : "json",
      error : function (xhr, status) {
          console.log(status);
      },
      success : function (data, status) {
          if (data.valid) {
              deleteBtn.closest("article").remove();
          } else {
              alert("삭제할 수 없습니다.");
          }
      }
  });


}
