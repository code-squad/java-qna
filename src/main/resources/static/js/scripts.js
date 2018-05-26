//$(".question-write button[type = submit]").click(addAnswer);
$(".question-write button[type='submit']").on("click", addAnswer);

function addAnswer(e){
    e.preventDefault();
    console.log("click!!");

    var queryString = $(".question-write").serialize();
    console.log("query : " + queryString);

    var url = $(".question-write").attr("action");
    console.log("url :" + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
        });
}

function onError(){
console.log("Fail");
alert("Failed");
}

function onSuccess(data, status){
console.log("Success : " + data);
var answerTemplate = $("#answerTemplate").html();
var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
console.log("template : " + template);
$(".qna-comment-slipp-articles").prepend(template);
$(".question-write textarea").val("");
}

//$(".delete-answer button[type = submit]").click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", ".delete-answer button[type='submit']", deleteAnswer);

function deleteAnswer(e){
e.preventDefault();
console.log(this);
var deleteBtn = $(this);
var url = $(this).attr("value");
console.log("url : " + url);

$.ajax({
    type : 'delete',
    url : url,
    dataType : 'json',
    error : function(xhr, status){
        console.log("error");
        alert("Error");

    },
    success : function(data, status){
        console.log(data);
        if(!data.valid){
            alert(data.errorMessage);
        }else{
        $(deleteBtn).closest("article").remove();
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