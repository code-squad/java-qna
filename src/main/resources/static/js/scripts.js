$(".submit-write button[type=submit]").on("click", addAnswer);
function addAnswer(e) {
    console.log('되라');
    e.preventDefault();

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
//ApiAnswerController 에서 만들어져 data 에 json 파일로 담긴다.
function onError() {
    console.log('error');
    alert("로그인 해주세요 ");
    window.location.href = '/users/login';
}


function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.userId, data.formattedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);

    $(".qna-comment-count-num").html(data.question.count)
    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on("click", ".link-deleted-article", deletedAnswer);
function deletedAnswer(e) {
    console.log('deletedAnswer');
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr('href');
    console.log("url : " + url);

    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error: function (xhr,status) {
                console.log('error');
            },
            success : function (data, status) {
                console.log('success');
                if (data.valid) {
                    deleteBtn.closest('article').remove();

                    var count = $(".qna-comment-count-num").html();
                    count--;
                    $(".qna-comment-count-num").html(count)

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