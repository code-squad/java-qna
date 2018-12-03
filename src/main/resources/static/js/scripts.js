$(".submit-write input[type=submit]").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

function addAnswer(e) {
    e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

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
            success : onSuccess,
        });
}

function onError(){
    console.log("error");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.commenter.name, data.formattedCreateDate, data.contents, data.question.index, data.index);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(".delete-answer-form button[type='submit']").click(deleteAnswer);


function deleteAnswer(e) {
    e.preventDefault();
    var url = $(this).parents("form").attr("action");
    var deleteBtn = $(this);


    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log(xhr);
            console.log(status);
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