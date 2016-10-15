function getTutorial(id, url) {
  if (id == "-2") {
	resetTutorial();
	$(".tableTutorial :input").prop("disabled", true);
  } else if (id == "-1") {
    resetContent();
    $(".tableTutorial :input").prop("disabled", false);
  } else {
	resetCategories();
	var params = {id:id};
	var request = $.ajax({
	  url: url,
	  type: "post",
	  data: params,
	  datatype: "json",
	  contenttype: "application/x-www-form-urlencoded; charset=utf-8"
	});
	request.done(handleSelectTutorialResponse);
  }
}

function handleSelectTutorialResponse(response) {
  if (response.status != undefined) {
    addMsg(response);
  } else {
	$(".tableTutorial :input").prop("disabled", false);
    $("#id").html(response.id);
    $("#title").val(response.title);
    $("#content").val(response.content);
    $(response.categories).each(function() {
      $("#cat_" + this.id).prop("checked", true);
    });
  }
}

function resetTutorial() {
  $("#selectTutorial").val("-2");
  resetContent();
}
function resetContent() {
  $("#id").html("");
  $("#title").val("");
  $("#content").val("");
  resetCategories();
}

function resetCategories() {
  $("#tutorialCategories :input").prop("checked", false);
}

function updateTutorialInfo(url) {
  var id = $("#selectTutorial").val();
  var title = $("#title").val();
  var content = $("#content").val();
  var categories = [];
  $("#tutorialCategories input:checked").each(function() {
    categories.push(Number($(this).val()));
  });
  
  var params = {id: id, title: title, content: content, categories: JSON.stringify(categories)};
  var request = $.ajax({
	url: url,
	type: "post",
	data: params,
	datatype: "json",
	contenttype: "application/x-www-form-urlencoded; charset=utf-8"
  });
  request.done(handleUpdateTutorialResponse);
}
function handleUpdateTutorialResponse(response) {
  if (response.status != undefined) {
    addMsg(response);
  } else {
	addMsg({status: "ERROR", message: "Non-status response"});
  }
}