$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();
  var url = $(".answer-write").attr("action");
  console.log("queryString : " + queryString);
  console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  });
}

function onError(data, status) {
  console.log("Error");
  console.log("data : " + data);
  console.log("status : " + status);
}

function onSuccess(data, status) {
  console.log("Success");
  console.log("data : " + data);
  console.log("status : " + status);
}
