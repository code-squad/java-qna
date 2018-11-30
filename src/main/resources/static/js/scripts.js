// answer 생성
$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault(e);
    var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
    console.log("query : "+ queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess
    });
}

function onError() {
    console.log('error');
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".question-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val(""); // contents 내용 초기화
}

// answer 삭제
$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    console.log(deleteBtn);
    var url = deleteBtn.attr("href");

    console.log(url);

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
                alert(data.message);
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