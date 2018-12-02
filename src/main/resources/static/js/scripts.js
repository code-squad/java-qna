$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click");

    var queryString = $(".answer-write").serialize();
    console.log("queryString : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}

function onError(data) {
    console.log("error");
}

function onSuccess(data, status) {
    console.log("success : " + data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.formattedUpdatedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template); //prepend는 맨위에 붙음
    $(".answer-write textarea[name=contents]").val("");
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