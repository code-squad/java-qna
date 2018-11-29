console.log("hell");
$(".submit-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("addAnswer 실행");
    e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

    var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
    console.log("query : "+ queryString);

//    var queryString = $("form[name=contents]").serialize();

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

function onError() {
    console.log('error');
}

function onSuccess(data,status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
//    console.log("answerTemplate : " + answerTemplate);
    var template = answerTemplate.format(data.writer.userId, data.formatTime, data.contents, data.question.id, data.id);
//    console.log("template : " + template);
    $(".qna-comment-slipp-articles").append(template);
    $(".answerCount").html("");
    $(".answerCount").append(data.question.answerCount);

    $("textarea[name=contents]").val("");

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