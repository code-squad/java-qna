//index 변수를 별도로 선언해 준 이유는 브라우저의 scope 이슈 때문에 그렇다.
//브라우저의 스코프는 기본적으로 공용 공간으로 쓰이기 때문에 함수 이름이 같으면 계속 덮어쓰기가 된다.
//따라서 index 객체 안에서만 scope가 돌도록 함수의 범위를 설정한 것으로 이해하면 된다.

var index = {
  init: function () {
    var _this = this;

    $('#btn-save').on('click', function () {
      _this.save();
    });

    $('#btn-update').on('click', function () {
      _this.update();
    });

    $('#btn-delete').on('click', function () {
      _this.delete();
    });

    $('#btn-register').on('click', function () {
      _this.register();
    });
  },

  register: function () {
    var data = {
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
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },

  save: function () {
    var data = {
      title: $('#title').val(),
      author: $('#author').val(),
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
  }
};

index.init();