$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("add answer");
    e.preventDefault();

    var queryString = $(".submit-write").serialize();
    console.log("query : "+ queryString);

    var url = $(".submit-write").attr("action");
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

function onError(data, status) {
    alert(data.responseText);
}
function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.id, data.user.name, data.createDate, data.comment, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template); // prepend
    $("textarea[name=comment]").val("");
    $(".qna-comment-count strong").html(data.question.notDeletedAnswersSize);
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