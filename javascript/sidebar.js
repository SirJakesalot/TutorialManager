
/**
 * handleSidebarResponse will iterate over json response and populate the
 * categoryList div.
 * @param response The response from the AJAXRequest
 */
function handleSidebarResponse(response) {
  var json = JSON.parse(response);
  /* Display status message and nothing else */
  if (json.status != undefined) {
    addMsg(json);
  }
  /* Add all categories to the sidebar */
  if (json.categories != undefined) {
    $.each(json.categories, function(i, category) {
      if (category.status != undefined) {
        addMsg(category);
      } else {
        addCat(category);
      }
    });
  }
  /* Add all tutorials to their respective categories */
  if (json.tutorials != undefined) {
    $.each(json.tutorials, function(i, tutorial) {
      if (tutorial.status != undefined) {
        addMsg(tutorial);
      } else {
        if (tutorial.categories != undefined) {
          $.each(tutorial.categories, function(i, category) {
            if (category.status != undefined) {
              addMsg(category);
            } else {
              addTut(tutorial, category.id);
            }
          });
        }
      }
    });
  }
  prepareCategoryList();
}

/**
* addCat will append a category object to the categoryList.
* @param category Object representing a category's attributes
*/
function addCat(category) {
  var id = "cat_" + category.id;
  var name = category.name;
  var $cat = $("<li />").html(name);
  var $tuts = $("<ul />").attr("id", id);
  $cat.append($tuts);
  $("#catList").append($cat);
}
/**
* addTut will append a tutorial object to its respective category in categoryList.
* @param tutorial Object representing a tutorial's attributes
* @param cat_id The category's id for the tutorial to be appended
*/
function addTut(tutorial, cat_id) {
  var id = tutorial.id;
  var title = tutorial.title;
  var $link = $("<a />").attr("href", "tutorial?tutorial_id=" + id).attr("text", title);
  var $tut = $("<li />").append($link);
  $("#cat_" + cat_id).append($tut);
}
/**
 * prepareCategoryList Setup categoryList to expand/collapse on clicks.
 */
function prepareCategoryList() {
    $('#catList').find('li:has(ul)').click( function(event) {
        if (this == event.target) {
            $(this).toggleClass('expanded');
            $(this).children('ul').toggle('medium');
        } else {
            return true;
        }
        return false;
    }).addClass('collapsed').children('ul').hide();
};
function expandCategory(element) {
  if (element == event.target) {
    $(element).toggleClass('expanded');
    $(element).children('ul').toggle('medium');
    return false;
  }
  return true;
}

function expandSidebar(element) {
  if ($(element).text() == "<<") {
    $(element).html(">>");
  } else {
    $(element).html("<<");
  }
  $(".sidebar").animate({
    "width": "toggle",
    "padding-left": "toggle",
    "padding-right": "toggle"
  }, 600, function(){});
}
