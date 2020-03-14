//index 변수를 별도로 선언해 준 이유는 브라우저의 scope 이슈 때문에 그렇다.
//브라우저의 스코프는 기본적으로 공용 공간으로 쓰이기 때문에 함수 이름이 같으면 계속 덮어쓰기가 된다.
//따라서 index 객체 안에서만 scope가 돌도록 함수의 범위를 설정한 것으로 이해하면 된다.

const index = {
  init: function () {
    const _this = this;

    $('#btn-save').on('click', function () {
      _this.save();
    });

    $('#btn-update').on('click', function () {
      _this.update();
    });

    $('#btn-delete').on('click', function () {
      _this.delete();
    });

    $('#btn-userregister').on('click', function () {
      _this.register();
    });

    $('#btn-userupdate').on('click', function () {
      _this.userUpdate();
    });

    $('#btn-answer-submit').on('click', function () {
      _this.answerSubmit();
    });

    $('#btn-answer-update').on('click', function () {
      _this.answerUpdate();
    });

    $('#btn-answer-delete').on('click', function () {
      _this.answerDelete();
    })
  },

  register: function () {
    const data = {
      userId: $('#userId').val(),
      password: $('#password').val(),
      name: $('#name').val(),
      email: $('#email').val()
    };

    $.ajax({
      type: 'POST',
      url: '/api/v1/users',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('회원가입을 환영합니다.');
      window.location.href = '/users/show';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },

  userUpdate: function () {
    const data = {
      userId: $('#userId').val(),
      password: $('#password').val(),
      name: $('#name').val(),
      email: $('#email').val()
    };
    const Id = $('#Id').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/users/' + Id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('회원정보를 수정했습니다.');
      window.location.href = '/users/show';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },

  save: function () {
    const data = {
      title: $('#title').val(),
      content: $('#content').val()
    };

    $.ajax({
      type: 'POST',
      url: '/api/v1/posts',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('글이 등록되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },

  update: function () {
    const data = {
      title: $('#title').val(),
      content: $('#content').val()
    };
    const Id = $('#Id').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/posts/' + Id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('글이 수정되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  delete: function () {
    const data = {
      deleteStatus: true
    };
    const Id = $('#Id').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/posts/delete/' + Id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('글이 삭제되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  answerSubmit: function () {
    const data = {
      content: $('#answer-content').val(),
      postId: $('#postId').val()
    };
    const postId = $('#postId').val();

    $.ajax({
      type: 'POST',
      url: '/api/v1/answers/',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('답변이 등록되었습니다.');
      window.location.href = '/posts/show/' + postId;
    })
  },

  answerUpdate: function () {
    const data = {
      content: $('#content').val()
    };
    const answerId = $('#answerId').val();
    const postId = $('#questionId').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/answers/' + answerId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('답변이 수정되었습니다.');
      window.location.href = '/posts/show/' + postId;
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  answerDelete: function () {
    const data = {
      deleteStatus: true
    };
    const answerId = $('#answerId').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/answers/delete/' + answerId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('답변이 삭제되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },
};

index.init();