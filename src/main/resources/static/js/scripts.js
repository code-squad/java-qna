String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

function answerWrite() {
    var form = document.getElementById("submit-answer");
    var req = new XMLHttpRequest();
    req.addEventListener('load', function() {
        writeHandle(req);
    }, false);
    req.open(form.method, form.action);
    req.setRequestHeader('Accept', 'application/json');
    req.send(new FormData(form));
}

function writeHandle(req) {
    if (req.status != 200) {
        alert('답변을 작성할 수 없습니다.');
        return;
    }
    var data = JSON.parse(req.responseText);
    var template = document.getElementById('answerTemplate').innerHTML.format(data.user.name, data.formattedDate, data.contents, data.id, data.question.id, data.id);
    var contents = document.createElement('div');
    contents.innerHTML = template;
    contents = contents.firstElementChild;
    document.querySelector(".qna-comment-slipp-articles").prepend(contents);
    document.getElementById('answer-editor').value = '';
}

function answerDelete(e) {
    var form = e.parentElement;
    var req = new XMLHttpRequest();
    req.addEventListener('load', function() {
        deleteHandle(req, form.closest('article'));
    }, false);
    req.open('delete', form.action);
    req.setRequestHeader('Accept', 'application/json');
    req.send(new FormData(form));
}

function deleteHandle(req, article) {
    var data = JSON.parse(req.responseText);
    console.log(data);
    if (data.valid) {
        article.remove();
        return;
    }
    alert(data.errorMessage);
}