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


