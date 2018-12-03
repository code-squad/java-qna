$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".submit-write").serialize();
    console.log("query : " + queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: function () {
            alert("error");
        },
        success : function (data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.name, data.formattedCreateDate, data.contents, data.question.id, data.id);
            $(".qna-comment-slipp-articles").append(template);
            $("textarea[name=contents]").val("");
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