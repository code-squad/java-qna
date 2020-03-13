String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

document.querySelectorAll("#answer__modify--btn").forEach((btn, index) => {
    btn.addEventListener("click", e => {
        document.querySelectorAll(".comment-doc")[index].remove();
        document.querySelectorAll(".article-util__comment")[index].remove();
        document.querySelectorAll(".comment__modify--input")[index].classList.remove("hidden");
    })
});


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
        success: onSuccess
    });
}


function onSuccess(data, status) {
    const answerTemplate = $("#answerTemplate").html();

    const {writer, createdAt, contents, id, question} = data;
    const template = answerTemplate.format(writer.name, createdAt, contents, question.id, id);

    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-writer textarea").val("");

    const commentCountEl = $(".qna-comment-count strong");
    commentCountEl.text(parseInt(commentCountEl.text()) + 1);
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

