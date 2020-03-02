$(".answer-write button[type=submit]").click(addAnswer);
$(".update-answer-form button[type=submit]").click(updateAnswer);
$(".delete-answer-button").click(deleteAnswer);

function addAnswer(e) {
  e.preventDefault();
  var queryString = $(".answer-write").serialize();
  // console.log("query : " + queryString);

  var url = $(".answer-write").attr("action");
  // console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: function (xhr) {
      console.log("error")
      console.log(xhr)
    },
    success: function (data) {
      // console.log(data.createdTime)
      // console.log(data.contents)
      // console.log(data.id)
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(
          data.writer,
          data.createdTime,
          data.contents,
          data.question.id,
          data.id);
      $(".qna-comment-slipp-articles").prepend(template);

      $("textarea[name=contents]").val("");
      console.log("success")
    }
  });
}

function updateAnswer(e) {
  e.preventDefault();
  var url = $(this).attr("action");
  console.log(url);
}

function deleteAnswer(e) {
  e.preventDefault();

  var url = $(".delete-answer-form").attr("action");
  console.log(url);

  $.ajax({
    type: 'DELETE',
    url: url,
    dataType: 'json',
    error: function (xhr, status) {
      console.log(xhr);
    },
    success: function (data, status) {
      console.log("success");
    }
  })
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






