$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click");

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onSuccess(data, status) {
    console.log("> " + status);
    console.log(data);
    createTemplate(data);
}

function createTemplate(data) {
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.postingTime, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);
    $(".answer-write textarea").val("");
}

function onError(error, status) {
    console.log("> " + status);
    console.log(error);
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
