$(".answer-write button[type=submit]").click(function (e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    $.ajax({
        method: "POST",
        url: url,
        data: queryString,
        dataType: "json",
        success: onCreationSuccess
    });
});

$(".qna-comment-slipp-articles").on("click", "a[class='link-delete-article']", function (e) {
   e.preventDefault();
   var deleteBtn = $(this);
   var url = $(this).attr("href");
   $.ajax({
       method: "DELETE",
       url: url,
       dataType: "json",
       error: function(jqXHR) {
           var jsonData = JSON.parse(jqXHR.responseText);
           if (jsonData.status === 405) {
                window.location.replace(jsonData.path);
           }
       },
       success: function(result) {
           if (result.valid) {
               deleteBtn.closest("article").remove();
               return;
           }
           alert(result.errorMessage);
       }
   });
});

function onCreationSuccess(data) {
    console.log(data);
    var answerTemplate = $('#answer-Template').html();
    var template = Handlebars.compile(answerTemplate);
    var html = template(data);
    $(".answer-article-ajax").append(html);
    $(".form-control").val("");

    $(".qna-comment-count").text(data.question.countOfAnswers + 1 + '개의 의견');
}

