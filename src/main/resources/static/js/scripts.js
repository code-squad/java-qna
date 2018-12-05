function loginError(){
    console.log("로그인 에러");
    alert("로그인이 필요합니다.");
    location.href = '/users/loginForm';
}

$(".submit-write input[type=submit]").click(addAnswer);     //$뒤 html태그에서 클릭의 이벤트가 발생시 addAnswer메소드를 실행하라
function addAnswer(e) {
    e.preventDefault();     //서버로 데이터 전송 차단
    console.log("input click!");

    var queryString = $(".submit-write").serialize();
    console.log("query : " + queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);
    //서버로 데이터 전송
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : loginError,
        success : function(data, status){
           console.log(status);
           console.log(data);
           var answerTemplate = $("#answerTemplate").html();
           var template = answerTemplate.format(data.writer.pid, data.writer.userId, data.date, data.contents, data.question.pid, data.pid);
           $(".qna-comment-slipp-articles").append(template);
           $(".qna-comment-count strong").html(data.question.answersSize);
           $("textarea[name=contents]").val("");
        }});
}

//$(".delete-answer-form button[type=submit]").click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);
function deleteAnswer(e){
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.parent().attr("action");
    console.log("this : " + deleteBtn);
    console.log("url : " + url);
    console.log("delete click!");
    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error : function(xhr, status){
                console.log(status);
                alert("수정 또는 삭제할 수 없습니다");
            },
            success : function(data, status){
                console.log(status);
                console.log(data);
                if(data.valid) {
                    deleteBtn.closest("article").remove();
                    console.log(data.answersSize);
                    $(".qna-comment-count strong").html(calculateAnswerSize());
                }
                else {
                    alert(data.errorMessage);
                }
            }});
}

function calculateAnswerSize() {
    var answersSize = $(".qna-comment-count strong").html();
    answersSize = parseInt(answersSize);
    return --answersSize;
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