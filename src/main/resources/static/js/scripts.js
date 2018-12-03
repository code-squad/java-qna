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
        error: function() {
            console.log("hello Error");
        },
        success : function(data) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.name, data.formattedCurDate, data.contents, data.question.id, data.id, data.writer.id);

            $("#appendAnswerData").append(template);
            $("textarea[name=contents]").val("");

            console.log("hello Success");
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
        },
        success : function(data){
            console.log("data : " + data);
            if(data === true){
                deleteButton.closest("article").remove();
                console.log("complete");
            }
            else {
                alert("permission denied.");
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