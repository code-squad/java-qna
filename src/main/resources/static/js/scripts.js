// 클릭하는 순간 addAnswer()를 실행할 것
// .은 클래스명을 의미, 그 자식 중 button이고 submit일 때
// js고치고 새로고침 한번해야 적용이 됩니다.
$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("add answer");
    e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

    // form data들을 자동으로 묶어준다.(key-value 형태로)
    // key는 html에 있는 name이 됩니다.
    var queryString = $(".submit-write").serialize();
    console.log("query : "+ queryString);

    var url = $(".submit-write").attr("action");  // action에 있던 url을 읽어와 설정한다
    console.log("url : " + url);

    $.ajax({
        type : 'post', // delete, put도 그대로 사용가능
        url : url,  // 서버의 path로 씁니다.
        data : queryString,  // 아까 묶어둔 데이터
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}

// 성공할때와 실패할때 정의해놓아야함
function onError() {
    console.log('error');
}
function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.user.id, data.user.name, data.createDate, data.comment, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template); // prepend
    $("textarea[name=comment]").val("");
    $(".qna-comment-count strong").html(data.question.notDeletedAnswersSize);
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