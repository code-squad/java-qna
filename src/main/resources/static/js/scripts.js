$(".answer-write button[type=submit]").click(function (e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    $.ajax({
        method: "POST",
        url: url,
        data: queryString,
        dataType: "json",
        success: onSuccess
    });
});

var onSuccess = function(data) {
    console.log(data);
    var answerTemplate = $('#answer-Template').html();
    var template = Handlebars.compile(answerTemplate);
    var html = template(data);
    $(".answer-article-ajax").append(html);
    $(".form-control").val("");

    $(".qna-comment-count").text(data.question.countOfAnswers + 1 + '개의 의견');
};
