$(".submit-write button[type=submit]").click(addAnswer);

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
        error: onError,
        success : onSuccess,
    });
}

function onError(){
    console.log("hello Error");
}

function onSuccess(data){
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.formattedCurDate, data.contents, data.question.id, data.id);

    $("#appendAnswerData").append(template);
    $("textarea[name=contents]").val("");

    console.log("hello Success");
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