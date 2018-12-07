$("#comment > button[type=submit]").click(addComment);
$(".link-modify-article.pop").click(popupModify);
$(".delete-answer-button[type=button]").click(deleteComment);
$("#userId").focusout(duplicationCheck);
$("form[name=join] > button[type=submit]").click(join);

String.prototype.format = function() {
  var args = arguments;
       return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
         ? args[number]
             : match
        ;
  });
};

function popupModify(e) {
    var url = $(this).attr('href');
    console.log('url : ' + url);
    e.preventDefault();
    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function () {
            console.log('Failure PopupModify Ajax');
        },
        success : function (data) {
            console.log(data);
            var source = $('#commentModifyTemplate').html();
            var template = Handlebars.compile(source);
            var target = {
                commentId : data.commentId,
                contents : data.contents,
                userId : data.user.userId,
                id : data.user.id
            };
            $('#modifyModal').html(template(target));
            $('#modifyModal').dialog();
        }
    });
}

function addComment(e) {
    console.log("addComment Method Call!");

    e.preventDefault();
    var queryString = $("form[name=comment]").serialize();
    console.log("queryString : " + queryString);
    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function () {
            console.log('failure');
            window.location.href = '/users/loginFail'
        },
        success : function (data) {
            console.log(data);
            var commentTemplate = $('#commentTemplate').html();
            var template = commentTemplate.format(data.user.id, data.user.userId, data.reportingDate, data.contents,
                               data.question.id, data.commentId);
            countComment(1);
            $('.qna-comment-slipp-articles').prepend(template);
            $('textarea[name=contents]').val('');
            $(".link-modify-article.pop").click(popupModify)
            $(".delete-answer-button").click(deleteComment);
        }
    });
}

function modifyComment() {
    console.log("modifyComment Method Call!");
    var queryString = $("form[name=modify-comment]").serialize();
    console.log("queryString : " + queryString);
    var url = $(".modify-comment-form").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function () {
            console.log('failure');
        },
        success : function (data) {
            console.log(data);
            $('#article-' + data.commentId + ' > .article-header > .article-header-text > .article-header-time').val(data.reportingDate);
            $('#article-' + data.commentId + ' > .article-doc.comment-doc > p').html('');
            $('#article-' + data.commentId + ' > .article-doc.comment-doc > p').text(data.contents);
            $('textarea[name=contents]').val('');
            $('#modifyModal').dialog('close');
        }
    });
}

function countComment(num) {
    var count = Number($('.qna-comment-count strong').html()) + num;
    $('.qna-comment-count strong').html('');
    $('.qna-comment-count strong').append(count);
}

function deleteComment(e) {
    console.log("댓글 삭제");
    e.preventDefault();
    if(confirm("삭제하시겠습니까?!")) {
        var url = $(".delete-answer-button").closest("form").attr('action');
        console.log('Url : ' + url);
        var queryString = $(".delete-answer-button").closest("form").serialize();
        console.log('queryString' + queryString);
        $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error : function () {
                console.log('failure');
            },
            success : function (data) {
                console.log(data);
                $('#article-' + data).html('');
                countComment(-1);
            }
        });
    }
}

function duplicationCheck(e) {
     console.log("아이디 중복확인 메소드 호출!");
     var queryString = $("form[name=join]").serialize();
     console.log(queryString);
     var url = '/api/users/duplicationCheck';
     console.log("url : " + url);

     $.ajax({
         type : 'post',
         url : url,
         data : queryString,
         dataType : 'json',
         error : function (xhr) {
             console.log("아이디 중복확인 Ajax 실패!");
             console.log(xhr);
         },
         success : function (data) {
             console.log(data);
             if(!data.valid) {
                 alert(data.errorMessage);
             }
             if(data.valid) {
                 $(".btn.btn-success.clearfix.pull-right").removeAttr('disabled');
             }
         }
     });
 }

function join(e) {
    e.preventDefault();
    console.log("회원가입");
    var queryString = $("form[name=join]").serialize();
    console.log(queryString);
    var url = '/api/users';
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function (xhr) {
            console.log("회원가입 실패!");
            console.log(xhr);
        },
        success : function (data) {
            console.log(data);
            if(!data.valid) {
                alert(data.errorMessage);
            }
            if(data.valid) {
                window.location.href = '/';
            }
        }
    });
}