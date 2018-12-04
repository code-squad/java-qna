$(".submit-write button[type='submit']").on("click", addAnswer);

function addAnswer(e){
    e.preventDefault();
    var queryString = $(".submit-write").serialize();
    console.log("queryString : "+ queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: function(xhr) {
            console.log("error xhr");
            console.log(xhr);
            alert("error!")
        },
        success : function(data) {
            console.log("success");
            console.log(data);
            if(data.valid){
                var answer = data.data;
                var answerTemplate = $("#answerTemplate").html();
                var template = answerTemplate.format(answer.writer.name, answer.formattedModifiedDate, answer.contents, answer.question.id, answer.id, answer.writer.id);

                $("#appendAnswerData").append(template);
                $("textarea[name=contents]").val("");
                increaseAnswerCount();
                console.log("hello Success");
            } else {
                alert(data.errorMessage);
                $("textarea[name=contents]").val("");
            }

        }
    });
}

$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

function deleteAnswer(e){
    e.preventDefault();
    console.log("hello world")


    var deleteButton = $(this);
    console.log("deleteButton : " + deleteButton);

    var url = deleteButton.parent().attr("action");
    console.log(deleteButton.parent().attr("action"));

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(){
            console.log("hello error");
            alert("error!");
        },
        success : function(data){
            console.log("success");
            console.log(data);
            var valid = data.valid;
            if(valid){
                deleteButton.closest("article").remove();
                decreaseAnswerCount();
                console.log("complete");
            }
            else {
                alert(data.errorMessage);
            }
        }
    });
}

function increaseAnswerCount(question){
    var curCount = parseInt($(".qna-comment-count strong").html());
    $(".qna-comment-count strong").html(curCount + 1);
}

function decreaseAnswerCount(question){
    var curCount = parseInt($(".qna-comment-count strong").html());
    $(".qna-comment-count strong").html(curCount - 1);
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