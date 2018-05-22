
// 답변기능
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    // e는 클릭이 발생한 이벤트 정보가 들어가있다.
    e.preventDefault();
    console.log("addClick");

    var queryString = $(".answer-write").serialize();
    console.log("query is " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url is " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess});
}

function onError() {
    console.log("error");
}

function onSuccess(data, status) {
    // data는 answer데이터
    console.log("success");
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);

    $("textarea[name=contents]").val("");
}


// 삭제기능
// $("a.delete-answer").click(deleteAnswer);
$("button.link-delete-article").click(deleteAnswer);
function deleteAnswer(e) {
    console.log("delete button")
    e.preventDefault();
    // evenet를 막는다. 다른 url로 가지 못하도록

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    // 현재 클릭한 이벤트에 대해서 href의 값을 가져온다.
    console.log("url : " + url);
    
    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
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