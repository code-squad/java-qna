// $(".answer-write button[type=submit]").click(addAnswer);
// $("a.link-delete-article").click(deleteAnswer);

$(".answer-write button[type=submit]").on("click", addAnswer);
// $(document).on("click", "a.link-delete-article", deleteAnswer);
$(document).on("click", "a.link-delete-comment", deleteAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click me");
    var queryString = $(".answer-write").serialize();
    console.log("queryString " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {

}

function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.comment, data.question.id, data.id);
    $(".answer-write").before(template);
    $(".answer-write textarea").val("");
}

function deleteAnswer(e) {
    e.preventDefault();

    var delBtn = $(this);
    var url = delBtn.attr("href");
    console.log("url is " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);
            console.log(data.message);

            if(data.valid) {
                delBtn.closest("article").remove();
            }else {
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