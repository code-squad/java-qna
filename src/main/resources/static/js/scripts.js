$(".answer-write input[type=button]").click(addAnswer);

function addAnswer(e) {
    console.log("hello");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'put',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError(data, status) {
    console.log("Error!");
    console.log(data);
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.userId, data.date, data.content, data.question.questionId, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    console.log("Complete!");
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args [number]
            : match
            ;

    });
};