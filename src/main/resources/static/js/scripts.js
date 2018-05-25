$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess});
}

function onError() {
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    console.log(answerTemplate);
    var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on("click",".link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    console.log("this : " + this);
    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log(url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr, status) {
            console.log('error');
        },
        success : function(data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

$(".qna-comment-slipp-articles").on("click", ".link-modify-article", showModifyAnswer);

function showModifyAnswer(e) {
    e.preventDefault();
    console.log("this : " + this);
    var modifyBtn = $(this);
    var url = modifyBtn.attr("href");
    var strings = url.split("/");
    var content = $("#comment_"+strings[5]).text().trim();
    var editableText = $("<textarea class=\"form-control\" id=\"contents\" name=\"contents\"></textarea>").val(content);
    $("#comment_"+strings[5]).replaceWith(editableText);
}

function onSucces2s(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    console.log(answerTemplate);
    var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.contents, data.question.id, data.id);
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