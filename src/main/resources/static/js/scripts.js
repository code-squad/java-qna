$(".answer-write input[type=submit]").click(addAnswer);
$(".delete-answer-form").click(deleteAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click");

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    // 클라이언트(리퀘스트)에서 서버로 준 데이터
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : createTemplate
    });
}

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr("action");
    console.log("url : " + url)

    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error : onError,
            success : deleteTemplate(deleteBtn)
        });
}

// 서버에서 클라이언트로 준 데이터
function createTemplate(data) {
    console.log("> " + status);
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.postingTime, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);
    $(".answer-write textarea").val("");
}

function deleteTemplate(deleteBtn) {
    return function(data, status) {
        console.log("> " + status);
        console.log(data);
        if (!data) {
            alert("사용자 권한이 없습니다. " + data);
        } else {
            deleteBtn.closest("article").remove();
        }
    }
}

function onError(error, status) {
    console.log("> " + status);
    console.log(error);
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
