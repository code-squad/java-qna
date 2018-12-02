$(".submit-write button[type=submit]").click(addAnswer);
$(".delete-answer-form button[type=submit]").click(deleteAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".submit-write").serialize();

    var url = $(".submit-write").attr("action");

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess
    });
}

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this).parent();
    var url = deleteBtn.attr("action");
    console.log(url);

    $.ajax({
            type : 'delete',
            url : url,
//            data : queryString,
            dataType : 'json',
            error: onError,
            success : deleteBtn.closest('article.article').remove()  // 삭제
        });
}

function onError(data, status) {
    alert(data.responseText);
}
function onSuccess(data, status) {
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.id, data.user.name, data.createDate, data.comment, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);
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