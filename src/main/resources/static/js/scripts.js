$(".answer-write button[type=submit]").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();
  var url = $(".answer-write").attr("action");
  console.log("queryString : " + queryString);
  console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  });
}

function deleteAnswer(e) {
  e.preventDefault();

  var cur = $(this);
  var url = cur.attr("href");
  console.log("url : " + url);

  $.ajax({
    type: 'delete',
    url: url,
    dataType: 'json',
    error: function (xhr, status) {
      console.log("error");
    },
    success: function (data, status) {
      console.log(data);
      if (data.valid) {
        cur.closest("article").remove();
      } else {
        alert(data.errorMessage);
      }
    }
  });
}

function onError(data, status) {
  console.log("Error");
  console.log("data : " + data);
  console.log("status : " + status);
}

function onSuccess(data, status) {
  console.log("Success");
  console.log("data : ");
  console.log(data);
  console.log("status : " + status);

  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(data.user.userId, data.lastModifiedDateTime, data.contents, data.question.id, data.id);
  $(".qna-comment-slipp-articles").prepend(template);
  $("textarea[name=contents]").val("");
}

String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
