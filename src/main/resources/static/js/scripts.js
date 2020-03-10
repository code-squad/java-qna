$(".answer-write button[type=submit]").click(function (e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    $.ajax({
        method: "POST",
        url: url,
        data: queryString,
        dataType: "json",
        success: function(data, status) {
            console.log(data);
            var answerTemplate = $('#answerTemplate').html();
            var result = answerTemplate.format(data.writer, data.createDateTimeToString, data.contents, data.id, data.question.id);
            $(".answer-article-ajax").append(result);
            $(".form-control").val("");
        }
    });
});

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
