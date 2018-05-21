
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("addClick");

    var queryString = $(".answer-write").serialize();
    console.log("query is " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url is " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {
    console.log("error");
}

function onSuccess(data, status) {
    console.log("success");
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
}