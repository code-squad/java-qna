console.log("aaaa");
$(".submit-write button[type=submit]").click(addAnswer);
function addAnswer(e) {
    console.log('되라');
    e.preventDefault();

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
        success : onSuccess,
    });
}

function onError() {
    console.log('error');
}


function onSuccess(data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.user.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
            $(".qna-comment-slipp-articles").prepend(template);
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