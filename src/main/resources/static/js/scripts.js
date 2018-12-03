$(".submit-write button[type=submit]").on("click", addAnswer);
$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type=submit]", deleteAnswer);

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

    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error: onError,
            success : function test(data, status) {
                          deleteBtn.closest('article').remove();  // 삭제
                          var answerSize = $(".qna-comment-count strong");
                          var beforeValue = parseInt(answerSize.html());
                          answerSize.html(beforeValue - 1);

                     }
        });
}

function onError(data, status) {
    alert(data.responseJSON.errorMessage);
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