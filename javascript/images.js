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
  if (response.status == undefined) {
    addErrorMsg("Response does not include a status")
  } else {
    addMsg(response);
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