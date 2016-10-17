function getTutorial(id, url) {
  if (id == "-1") {
	resetTutorial();
    $("#deleteTutorial").prop("disabled", true);
	$(".tableTutorial :input").prop("disabled", true);
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
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
    $("#deleteTutorial").prop("disabled", false);
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
  $("#selectTutorial").val("-1");
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

function updateTutorial(url) {
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
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
	addMsg("ERROR", "No status response");
  }
}

function addTutorial(url) {
  var title = $("#title").val();
  var content = $("#content").val();
  var categories = [];
  $("#categories input:checked").each(function() {
    categories.push(Number($(this).val()));
  });
  
  if (title) {
    var params = {title: title, content: content, categories: JSON.stringify(categories)};
    var request = $.ajax({
	  url: url,
	  type: "post",
	  data: params,
	  datatype: "json",
	  contenttype: "application/x-www-form-urlencoded; charset=utf-8"
    });
    request.done(handleAddTutorialResponse);
  }
}
function handleAddTutorialResponse(response) {
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
	addMsg("ERROR", "No status response");
  }
}

function deleteTutorial(url) {
  var id = $("#selectTutorial").val();
  if (id != "-1") {
    var params = {id: id};
    var request = $.ajax({
	  url: url,
	  type: "post",
	  data: params,
	  datatype: "json",
	  contenttype: "application/x-www-form-urlencoded; charset=utf-8"
    });
    request.done(handleDeleteTutorialResponse);
  }
}

function handleDeleteTutorialResponse(response) {
  if (response.status != undefined && response.message != undefined) {
    $("#selectTutorial option:selected").remove();
    $("#selectTutorial").val("-1");
    addMsg(response.status, response.message);
  } else {
	addMsg("ERROR", "No status response");
  }
}


function addCategory(url) {
  var name = $("#name").val();
  
  if (name) {
    var params = {name: name};
    var request = $.ajax({
	  url: url,
	  type: "post",
	  data: params,
	  datatype: "json",
	  contenttype: "application/x-www-form-urlencoded; charset=utf-8"
    });
    request.done(handleAddCategoryResponse);
  }
}
function handleAddCategoryResponse(response) {
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
	addMsg("ERROR", "No status response");
  }
}


function deleteCategory(url) {
  var id = $("#selectCategory").val();
  var params = {id: id};
  var request = $.ajax({
    url: url,
    type: "post",
    data: params,
    datatype: "json",
    contenttype: "application/x-www-form-urlencoded; charset=utf-8"
  });
  request.done(handleDeleteTutorialResponse);
}

function handleDeleteCategoryResponse(response) {
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
	addMsg("ERROR", "No status response");
  }
}


