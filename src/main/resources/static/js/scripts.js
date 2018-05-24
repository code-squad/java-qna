// $(".answer-write input[type=submit]").click(addAnswer);
$(".answer-write input[type='submit']").on("click", addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click!!");

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
    console.log("error");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);

    $("textarea[name=contents]").val("");
}

// $("a.link-delete-article").click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);

    // this - 호출 주체
    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                // else 안쓰고 바로 alert 하면 안됨
                alert(data.errorMessage);
            }
        }
    })
}

// 단순한 템플릿 엔진?
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};