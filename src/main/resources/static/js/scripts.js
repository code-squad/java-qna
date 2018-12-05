$(".submit-write input[type=submit]").click(addAnswer);

function addAnswer(e){
    console.log("Add answer");
    e.preventDefault();                                 //submit 이 자동으로 동작하는 것을 막는다

    var queryString = $(".submit-write").serialize();   //form date들을 자동으로 묶어준다.
    console.log("query : " + queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}

function onError() {
    console.log('error');
}

function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();

    //data 위에 ajax 의 data 가 아니다.
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").append(template);

//    var answerCountText = "개의 의견";
    $(".strong").html(data.question.countOfAnswer);
//    $(".qna-comment-count").prepend(data.question.sizeAnswers);
//    var template2 = answerTemplate.format(5);
    $("textarea[name=contents]").val("");
}




//$("a.link-delete-article").click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dateType : 'json',
        error : function(xhr,status){
            console.log("Error")
        },
        success : function(data, status) {
            console.log(data);
            if(data.valid){
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