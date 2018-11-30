$(".submit-write input[type = submit]").click(addAnswers);

function addAnswers(e) {
    e.preventDefault();

    var qureyString = $(".submit-write").serialize();

    var url = $(".submit-write").attr("action");

    $.ajax ({
    type : 'post' ,
    url : url,
    data : qureyString,
    dataType : 'json' ,
    error : onError,
    success : onSuccess,
    });
}
function onError() {
    console.log("error");
    alert("error");
}

function onSuccess(data,status) {
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.date, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);
    $("textarea[name = contents]").val("");
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