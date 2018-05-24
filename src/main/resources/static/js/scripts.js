$(".answer-write input[type=button]").on('click', addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("Adding...");

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
    alert("로그인한 후에 답변을 달 수 있습니다.");
    console.log("Redirecting...");
    window.location.href = "/users/loginForm";
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.userId, data.date, data.content, data.question.questionId, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea[name=content]").val('');
    console.log("Success!");
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

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    console.log("Deleting...");
    e.preventDefault();

    var url = $(this).attr("href");
    console.log("url : " + url);

    var deleteBtn = $(this);

    $.ajax({
        type: 'delete',
        url: url,
        datatype: 'json',
        error: function (xhr, status) {
            console.log("Error");
        },
        success: function (data, status) {
            console.log("Success: " + data.success);
            if (data.success) {
                deleteBtn.closest("article").remove();
            } else {
                alert("Error: " + data.message);
            }
        }
    });
}