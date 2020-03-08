// $(".answer-write input[type=submit]").click(addAnswer);
//
// function addAnswer(e) {
//     e.preventDefault();
//     console.log("button clicked!!");
//
//     var queryString = $(".answer-write").serialize();
//     console.log("queryString : " + queryString);
//
//     var url = $(".answer-write").attr("action");
//     console.log("url : " + url);
//
//     $.ajax({
//         type : 'post',
//         url: url,
//         data: queryString,
//         dataType : 'json',
//         error : onError,
//         success : onSuccess
//     });
//
//     function onError() {}
//
//     function onSuccess() {}
// }