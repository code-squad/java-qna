$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $(".answer-write").serialize();
  console.log("query : " + queryString);

  var url = $(".answer-write").attr("action");
  console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'text',
    error: onError,
    success: onSuccess
  });
}

function onError(e) {
  console.log(e);
}

function onSuccess(data, status) {
  console.log(data);
}

