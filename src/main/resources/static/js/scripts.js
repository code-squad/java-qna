$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click me");
    var queryString = $(".answer-write").serialize();
    console.log("queryString " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {

}

function onSuccess(data, status) {
    console.log(data);
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