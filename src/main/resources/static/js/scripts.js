$(".answer-write input[type=button]").click(addAnswer);

function addAnswer(e) {
    console.log("hello");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'put',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError(data, status) {
    console.log("Error!");
    console.log(data);
}

function onSuccess(data, status) {
    console.log(data);
}