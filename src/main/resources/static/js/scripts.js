function answerWrite() {
    var req = new XMLHttpRequest();
    var form = document.getElementById("submit-answer");
    req.open('POST', form.action);
    req.setRequestHeader('Accept', 'application/json');
    req.addEventListener('load', function(){
        responseHandle(req);
    }, false);
    req.send(new FormData(form));
}

function responseHandle(req) {
    if (req.status != 200) {
        console.log('answer write err!!');
        return;
    }
    var data = JSON.parse(req.responseText);
    var arr = [data.user.name, data.modifiedDate, data.contents, data.id, data.question.id, data.id];
    var template = document.getElementById('answerTemplate').innerHTML;
    for (var i = 0; i < arr.length; i++) {
        template = template.replace(new RegExp("{[0-9]}"), arr[i]);
    }
    var contents = document.createElement('div');
    contents.innerHTML = template;
    contents = contents.firstElementChild;
    document.querySelector(".qna-comment-slipp-articles").prepend(contents);
    document.getElementById('answer-editor').value = '';
}