function loginError(){
    console.log("로그인 에러");
    alert("로그인이 필요합니다.");
    location.href = '/user/loginForm';
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
           var template = answerTemplate.format(data.writer.pid, data.writer.userId, data.answerDate, data.contents, data.question.pid, data.pid);
           $(".qna-comment-slipp-articles").append(template);
           $(".qna-comment-count strong").html(data.question.answersSize);
           $("textarea[name=contents]").val("");
        }});
}

$(".delete-answer-form button[type=submit]").click(deleteAnswer);
function deleteAnswer(e){
    e.preventDefault();

//    세션안의 로그인 유저 정보를 어떻게 가져옴?
//    var loginUser = $.session.get('loginUser');
//    console.log(loginUser);

    var url = $(this).parent().attr("action");   //this
//    var deleteBtn = $(this);
//    console.log(deleteBtn);
//    var url = $(".delete-answer-form").attr("action");
    console.log("url : " + url);

    console.log("delete click!");
    $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error : function(xhr, status){
                alert("수정 또는 삭제할 수 없습니다");
            },
            success : function(data, status){
                console.log("data");
                if (data.valid) {
                                deleteBtn.closest("article").remove();
                            } else {
                                alert(data.errorMessage);
                            }
            }});
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