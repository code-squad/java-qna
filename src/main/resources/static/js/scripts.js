$(".submit-write input[type='submit']").on("click", addAnswers);

function addAnswers(e) {
    console.log("add answer");
    e.preventDefault();

    var queryString = $(".submit-write").serialize();
    console.log("query : " + queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax ({
    type : 'post' ,
    url : url,
    data : queryString,
    dataType : 'json' ,
    error : onError,
    success : onSuccess,
    });
}
function onError() {
    console.log("error");
    alert("로그인을 해주세요.");
}

function onSuccess(data, status) {
    console.log(data);
    $(".qna-comment-count strong").html(data.question.answerSize);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.date, data.contents, data.question.id, data.id, data.writer.id);
    $(".qna-comment-slipp-articles").append(template);
    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswers);
function deleteAnswers(e) {

    var deleteBtn = $(this).parent();
    console.log("delete");
    e.preventDefault();

    var url = deleteBtn.attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr, status) {
            console.log(xhr);
            console.log("delete error!");
            alert("error!");
        },
        success : function(data, status) {
            console.log("success!");
            alert("success");
            if(data.valid) {
                deleteBtn.closest("article").remove();
            }
        }
    })
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