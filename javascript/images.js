function updateImage(btn, url) {
  var fileName = $(btn).parent().siblings(".fileName");

  var params = {from:$(fileName).attr("id"), to: $(fileName).val()};
  var request = $.ajax({
    url: url,
    type: "post",
    data: params,
    datatype: "json",
    contenttype: "application/x-www-form-urlencoded; charset=utf-8"
  });
  request.done(handleUpdateImageResponse);
}

function handleUpdateImageResponse(response) {
  if (response.status != undefined && response.message != undefined) {
    addMsg(response.status, response.message);
  } else {
    addErrorMsg("no status response")
  }
}

function deleteImage(btn, url) {
  var fileName = $(btn).parent().siblings(".fileName");

  var params = {fileName:$(fileName).attr("id")};
  var request = $.ajax({
    url: url,
    type: "post",
    data: params,
    datatype: "json",
    contenttype: "application/x-www-form-urlencoded; charset=utf-8"
  });
  request.done(handleUpdateImageResponse);
}

function fileUpload(url) {
  var formData = new FormData();
  var f = $("#files").prop("files");
  for (var i = 0, len = f.length; i < len; i++) {
    formData.append("file" + i, f[i]);
  }
  
  var request = $.ajax({
    url: url,
	type: 'post',
	data: formData,
	datatype: "json",
	contentType: false,
	cache: false,
	processData: false
  });
  request.done(handleUpdateImageResponse);
}

function handleFileUploadResponse(response) {
  console.log("response");
}