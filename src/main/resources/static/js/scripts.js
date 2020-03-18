String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

function makeAnswerTemplate(data) {
    const answerTemplate = $("#answerTemplate").html();
    const {writer, createdAt, contents, id, question} = data;
    return answerTemplate.format(writer.name, createdAt, contents, question.id, id);
}

$(".answer-writer button[type='submit']").on("click", addAnswer);

function addAnswer(e) {
    e.preventDefault();

    const queryString = $(".answer-writer").serialize();
    const url = $(".answer-writer").attr("action");

    $.ajax({
        type: 'POST',
        data: queryString,
        dataType: 'json',
        url: url,
        error: function (xhr, status, error) {
            console.log("failure");
        },
        success: function (data, status) {
            const template = makeAnswerTemplate(data);
            $(".qna-comment-slipp-articles").prepend(template);
            $(".answer-writer textarea").val("");

            const commentCountEl = $(".qna-comment-count strong");
            commentCountEl.text(parseInt(commentCountEl.text()) + 1);
        }
    });
}

$(".qna-comment-slipp-articles").on('click', ".link-delete-answer", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    const deleteBtn = $(this);
    const url = deleteBtn.attr("href");

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log('error');
        },
        success: function (data, status) {
            if (data.valid) {
                deleteBtn.closest("article").remove();
                const commentCountEl = $(".qna-comment-count strong");
                commentCountEl.text(parseInt(commentCountEl.text()) - 1);
            } else {
                alert(data.errorMessage);
            }
        }
    })
}

$(".qna-comment-slipp-articles").on('click', ".link-modify-article", showUpdateInput);

function showUpdateInput(e) {
    e.preventDefault();

    const answerTemplate = $("#answerUpdateTemplate").html();
    const template = answerTemplate.format($(this).attr("href"), $(this).closest("article").find(".comment-doc").text().trim());

    $(this).closest("article").append(template);
    $(this).closest("article").find(".article-header").remove();
    $(this).closest("article").find(".comment-doc").remove();
    $(this).closest("article").find(".article-util").remove();
}

$(".qna-comment-slipp-articles").on('click', "#comment-modify-button", updateAnswer);

function updateAnswer(e) {
    e.preventDefault();

    const updateBtn = $(this);
    const queryString = updateBtn.closest(".modify-answer-form").serialize();
    const url = updateBtn.closest(".modify-answer-form").attr("action");

    $.ajax({
        type: 'PUT',
        url: url,
        dataType: 'json',
        data: queryString,
        error: function (xhr, status, error) {
            console.log("failure")
        },
        success: function (data, status) {
            console.log(data);
            if (!data) {
                alert("잘못된 접근입니다.");
            } else {
                const template = makeAnswerTemplate(data);
                updateBtn.closest("article").remove();
                $(".qna-comment-slipp-articles").prepend(template);
                $(".answer-writer textarea").val("");
            }
        }
    })
}

