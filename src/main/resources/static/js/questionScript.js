const addAnswer = function (e) {
  e.preventDefault(); // submit이 자동으로 동작하는 것을 막는다.
  const submit = $('.submit-write');
  const queryString = submit.serialize(); // form data를 자동으로 묶어준다.
  const url = submit.attr('action');
  
  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: function (error) {
      alert(error.responseJSON.message);
      window.location.assign("/users/login");
    },
    success: function (data, status) {
      const answerTemplate = $('#answerTemplate').html();
      const template = answerTemplate.format(
          data.id,
          data.comment,
          data.formattedUpdatedDateTime,
          data.writer.id,
          data.writer.userId,
          data.writer.userProfileImage,
          data.question.id);
      $('.qna-comment-slipp-articles').append(template);
      $('#answer-count').html(data.question.answerCountExceptDeleted);
      $('textarea[name=comment]').val('');
    },
  });
};

const deleteAnswer = function (e) {
  e.preventDefault();
  const deleteButton = $(this);
  const url = deleteButton.parent().attr('action');
  
  $.ajax({
    type: 'delete',
    url: url,
    dataType: 'json',
    error: function (error) {
      alert(error.responseJSON.message);
      window.location.assign("/users/login");
    },
    success: function (data, status) {
      if (data.valid) {
        deleteButton.closest('article').remove();
        $('#answer-count').html(data.object.question.answerCountExceptDeleted);
      } else {
        alert(data.errorMessage);
      }
    },
  });
};
// Jquery on() function
// (event [, selector] [, data], handler)
// 선택된 요소에 하나 이상의 event handler function을 붙인다.
// event는 이벤트 유형
// selector는 하위 항목을 필터링하는 것, null이라면 해당 요소를 포함한 하위항목전체
// data 핸들러로 전달될 데이터
// handler 이벤트가 트리거 될 때, 실행될 function false로 주면 return false로 동작함.
// .off로 제거할 수 있음. parameter가 없다면 전체 이벤트 제거, 필터링 parameter가 존재한다면 정확히 일치하는 이벤트만 제거함.
// .one은 한 번만 동작하고 제거되는 것.
$('.submit-write button[type=submit]').on('click', addAnswer);
// 모든 delete-answer-form의 button에 이벤트를 주는 것이 아니라, 상위 요소에 이벤트를 주고, 이벤트 타겟에 함수 적용
// Delegated event handlers have the advantage that they can process events from descendant elements that are added to the document at a later time.
// Delegated event handlers는 문서에 나중에 추가된 요소의 이벤트를 처리할 수 있음.
// 많은 요소를 모니터링해야 할 때, 오버헤드가 훨씬 낮아질 수 있음. (1000개의 요소에 이벤트를 거는 것 보다는, 1개 상위요소에 이벤트 버블링을 주는 것이 좋다.)
// SVG에서는 동작하지 않는다고 한다.
// #이벤트 버블링, #이벤트 델리게이션
$('.qna-comment-slipp-articles').on('click', '.delete-answer-form button[type="submit"]', deleteAnswer);
