$(".answer-write button[type=submit]").on("click", addAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");

    $.ajax({
        type : 'POST',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : function (data, status) {
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.formattedModifiedDate, data.contents, data.question.id, data.id);
            $(".qna-comment-slipp-articles").append(template);
            $(".answer-write textarea[name=contents]").val("");
        }
    });
}

function deleteAnswer(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");

    $.ajax({
        type : 'DELETE',
        url : url,
        dataType : 'json',
        error : onError,
        success : function (data, status) {
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    })
}

function onError(data) {
    console.log("error");
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