$(".answer-write button[type=submit]").click(function (e) {
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    $.ajax({
        method: "POST",
        url: url,
        data: queryString,
        dataType: "json",
        success: function (data) {
            console.log(data);
            var answerTemplate = $('#answer-Template').html();
            var template = Handlebars.compile(answerTemplate);
            var html = template(data);
            $(".answer-article-ajax").append(html);
            $(".form-control").val("");
            $(".qna-comment-count strong").text(data.question.countOfAnswers);
        }
    });
});

$(".qna-comment-slipp-articles").on("click", "a[class='link-delete-comment']", function (e) {
   e.preventDefault();
   var deleteBtn = $(this);
   var url = $(this).attr("href");
   $.ajax({
       method: "DELETE",
       url: url,
       dataType: "json",
       error: function (jqXHR) {
           var jsonData = JSON.parse(jqXHR.responseText);
           if (jsonData.status === 405) {
                window.location.replace(jsonData.path);
           }
       },
       success: function(result) {
           if (result.valid) {
               deleteBtn.closest("article").remove();
               var countOfAnswers = Number($(".qna-comment-count strong").text());
               countOfAnswers = countOfAnswers - 1 < 0 ? 0 : countOfAnswers - 1;
               $(".qna-comment-count strong").text(countOfAnswers);
               return;
           }
           alert(result.errorMessage);
       }
   });
});

$(".qna-comment-slipp-articles").on("click", "a[class='link-modify-comment']", function (e){
    e.preventDefault();
    var url = $(this).attr("href");
    $.ajax({
        method: "GET",
        url: url,
        dataType: "json",
        error: function (jqXHR) {
            var jsonData = JSON.parse(jqXHR.responseText);
            if (jsonData.status === 405) {
                window.location.replace(jsonData.path);
            }
        },
        success: function (data) {
            console.log(data);
            var commentFormTemplate = $('#comment-form').html();
            var template = Handlebars.compile(commentFormTemplate);
            var html = template(data);
            $(".qna-comment-slipp-articles").remove();
            $(".qna-comment").append(html);
        }
    });
});
