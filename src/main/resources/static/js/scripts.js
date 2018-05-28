$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onCreateSuccess
    });
}

function onError() {
}

function onCreateSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on("click", ".link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    console.log("this : " + this);
    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log(url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log('error');
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

$(".qna-comment-slipp-articles").on("click", ".link-modify-article", showModifyAnswer);

function showModifyAnswer(e) {

    e.preventDefault();
    console.log("this : " + this);
    var modifyBtn = $(this);
    var url = modifyBtn.attr("href");

    $.ajax({
        type: 'get',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log('error');
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                var strings = url.split("/");
                var content = $("#comment_" + strings[5]).text().trim();
                console.log(content);
                var editableText = $('<textarea class="form-control" id="answer-contents-' + strings[5] + '"' + ' name="contents"></textarea>').val(content);
                $("#comment_" + strings[5]).replaceWith(editableText);

                var str = '<button class="link-modify-submit-article" id="answer-modify-submit" href="/api/questions/' + strings[3] + '/answers/' + strings[5] + '/formm">수정 후 등록</button>';
                $('#modify-article-' + strings[5]).replaceWith(str);
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

$(".qna-comment-slipp-articles").on("click", "#answer-modify-submit", modifyAnswer);

function modifyAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var modifyBtn = $(this);
    var url = modifyBtn.attr("href");
    console.log(url);
    var strings = url.split("/");
    console.log(strings);
    console.log(strings[5]);
    var content = $('#answer-contents-' + strings[5]).val();
    console.log(content);

    var queryString = 'contents=' + content;
    console.log("query : " + queryString);

    $.ajax({
        type: 'PUT',
        url: url,
        data: queryString,
        dataType: 'json',
        error: function () {
        },
        success: onModifySuccess
    });
}

function onModifySuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    console.log(answerTemplate);
    var template = answerTemplate.format(data.writer.userId, data.formattedModifiedDate, data.contents, data.question.id, data.id);
    $('#answer-'+data.id).replaceWith(template);
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};