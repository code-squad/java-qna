$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click me");
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