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


$(".answer-writer button[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log('click me');
    e.preventDefault();

    const queryString = $(".answer-writer").serialize();

    const url = $(".answer-writer").attr("action");

    $.ajax({
        type: 'POST',
        data: queryString,
        dataType: 'json',
        url: url,
        error: function (xhr, status, error) {
            console.log(error);
        },
        success: onSuccess
    });

}


function onSuccess(data, status) {
    const answerTemplate = $("#answerTemplate").html();

    const {writer, createdAt, contents, id} = data;
    const template = answerTemplate.format(writer.name, createdAt, contents, id, id);

    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-writer textarea").val("");
}
