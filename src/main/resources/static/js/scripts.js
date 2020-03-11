//$(".answer-write input[type=submit]").click(addAnswer);
//$(".delete-answer-form").click(deleteAnswer);

$(".answer-write input[type='submit']").on("click", addAnswer);
$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);
//$(".qna-comment-slipp-articles").on("click", ".link-modify-article", updateAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    console.log("query : " + queryString);
    console.log("url : " + url);

    // 클라이언트(리퀘스트)에서 서버로 준 데이터 (data -> queryString -> contents="타이핑 값")
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : createTemplate
    });
}

// 서버에서 클라이언트로 준 데이터 (data -> createAnswer 메서드의 Answer return 객체값)
function createTemplate(data) {
    console.log("> " + status);
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.postingTime, data.contents, data.question.id, data.id, data.writer.id);
    $(".qna-comment-slipp-articles").append(template);
    $(".answer-write textarea").val("");
}

function deleteAnswer(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    var url = deleteBtn.parent().attr("action");
    console.log("url : " + url)

    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error : onError,
            success : deleteTemplate(deleteBtn)
        });
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

//function updateAnswer(e) {
//    e.preventDefault();
//    var updateBtn = $(this);
//    var url = updateBtn.parent().attr("href");
//    alert("url > " + url);
//}

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
