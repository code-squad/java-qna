$(".submit-write button[type=submit]").on("click", addAnswer);

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
//        TODO : 어떤식으로 활용?
            alert("error");
        },
        success : function (data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.name, data.formattedCreateDate, data.contents, data.question.id, data.id);
            $(".qna-comment-slipp-articles").append(template);
            $("textarea[name=contents]").val("");

            //의견개수 동적반영
            $("p.qna-comment-count").text(data.question.countOfAnswer + "개의 의견");
        }
    });
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    //this가 어디서 호출되느냐에 따라 달라져서 여기서 지정해두고 써야
    var deleteBtn = $(this);
    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr, status) {
            console.log("error");
        },
        success : function(data, status) {
            console.log(data);
            if(data.valid) {
            //자기자신으로부터 가장 가까운 article을 찾아가기
                deleteBtn.closest("article").remove();
            //TODO : 삭제 시 의견개수 동적반영 어떻게?
            } else {
                alert(data.errorMessage);
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