$(".answer-write input[type='submit']").on("click", addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click me");

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    })
}

function onError(data, status) {
    console.log("failure");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    console.log("실행1");
    var template = answerTemplate.format(data.writer.name, data.formattedCreatedDate,
        data.contents, data.id, data.id);
    console.log("실행2");
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}

// json을 동적으로 처리하기 위한 템플릿 복사.
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
