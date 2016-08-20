<%@ include file="header.jsp" %>
  <h1 style="text-align: center;">Admin Dashboard</h1>
  <p id="msg" class="success"></p>
  <center>
    <select id="select_id" class="dashboard_select" onchange="getTutorialById(this.value);">
      <option value="-1"></option>
      <c:forEach var="tutorial" items="${tutorials}">
        <option value="${tutorial.id()}">${tutorial.title()}</option>
      </c:forEach>
    </select>
  </center>
  </br>
  <table class="dashboard_tutorial">
    <tr>
      <th>Id</th>
      <td><p id="id"></p></td>
    </tr>
    <tr>
      <th>Category</th>
      <td>
        <table class="dashboard_tutorial_category">
          <td>
            <select id="category_select" class="dashboard_select" onchange="updateCategoryButton();">
              <optgroup id="associated_category_group" label="Associated Categories" value="1">
              </optgroup>
              <optgroup id="available_category_group" label="Available Categories" value="2">
                <c:forEach var="category" items="${categories}">
                  <option value="${category.id()}">${category.name()}</option>
                </c:forEach>
              </optgroup>
            </select>
          </td>
          <td>
            <input id="category_update_button" type="button" value="Remove Category" onclick="updateTutorialAssociatedCategory();"/>
          </td>
        </table>
      </td>
    </tr>
    <tr>
      <th>Title</th>
      <td><input id="title" type="text"/></td>
    </tr>
    <tr>
      <th>Content</th>
      <td><textarea rows="25" id="content"></textarea></td>
    </tr>
    <tr>
      <td><p style="text-align: center;margin: 0px auto;"><input type="button" style="display: inline-block;" value="Delete Tutorial" onclick="deleteTutorial();"/></p></td>
      <td><input type="button" value="Update Tutorial" class="submit" onclick="updateTutorialInfo();"/></td>
    </tr>
  </table>
  <script>
    // Will update category button text to "Add Category" if there aren't any tutorials associated with the tutorial
    var context = "${context}";
    var dashboard_select_url = context + "/dashboard_select";
    var dashboard_update_url = context + "/dashboard_update";
    var dashboard_delete_url = context + "/dashboard_delete";

    // Remember category changes
    var associated_category_group = "associated_category_group";
    var available_category_group = "available_category_group";
    var added_categories   = [];
    var removed_categories = [];

    /*
        AJAX Calls
    */

    function getTutorialById(tutorial_id) {
        if (tutorial_id == -1) {
            resetTutorial();
        } else {
            var params = "tutorial_id=" + encodeURIComponent(tutorial_id);
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var arry = JSON.parse(xmlhttp.responseText);
                    updateSelectedTutorial(arry);
                }
            };
            xmlhttp.open("POST", dashboard_select_url, true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send(params);
        }
    }
    function updateTutorialInfo() {
        var idval = encodeURIComponent($("#select_id").val());
        var titleval = encodeURIComponent($("#title").val());
        var contentval = encodeURIComponent($("#content").val());
        var associated = [];
        var available = [];
        $("#associated_category_group").children().each(function() {
            associated.push(Number($(this).val()));
        });
        //var params = JSON.stringify({id: idval, title: titleval, content: contentval, associated_categories: associated});
        var params = "tutorial_id=" + idval +
                     "&tutorial_title=" + titleval +
                     "&tutorial_content=" + contentval +
                     "&tutorial_associated_categories=" + JSON.stringify(associated);
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                console.log(xmlhttp.responseText);
                var arry = JSON.parse(xmlhttp.responseText);
                addTutorialSelect(arry);
            }
        };
        //xmlhttp.open("POST", dashboard_update_url, true);
        xmlhttp.open("POST", dashboard_update_url);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //xmlhttp.setRequestHeader("Content-type", "application/json;charset=UTF-8");
        xmlhttp.send(params);
    }
    function deleteTutorial() {
        var idval = encodeURIComponent($("#select_id").val());
        var params = "tutorial_id=" + idval;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                console.log(xmlhttp.responseText);
                var arry = JSON.parse(xmlhttp.responseText);
                removeTutorialSelect(arry);
            }
        };
        xmlhttp.open("POST", dashboard_delete_url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(params);
    }

    /*
        Callbacks
    */

    function updateSelectedTutorial(arry) {
        resetCategories();
        $("#id").html(arry[0].id);
        $("#title").val(arry[0].title);
        $("#content").val(arry[0].content);
        $(arry[0].categories).each(function() {
            var id = $(this)[0].id
            $("#available_category_group").children().each(function() {
                //console.log("ID: " + id + " Val: " + $(this).val());
                if ($(this).val() == id) {
                    insertCategory(associated_category_group, $(this));
                }
            });
            //insertCategory(associated_category_group, $("<option value='" + $(this)[0].id + "'>" + $(this)[0].name + "</option>"));
            //console.log(JSON.stringify($(this)[0].id));
            //console.log(JSON.stringify($(this)[0].name));
        });
    }

    function addTutorialSelect(arry) {
        removeTutorialSelect(arry);
        $("#select_id").append($('<option value=' + arry[1].id + '>' + arry[1].title + '</option>'));
    }
    function removeTutorialSelect(arry) {
        resetTutorial();
        $("#msg").html(arry[0].message);
        if (arry.length > 0) {
            $("#select_id option[value='" + arry[1].id + "']").remove();
        }
    }

    /*
        Category actions
    */
    function updateCategoryButton() {
        var optgroup_val = $('#category_select :selected').parent().attr('value');
        if (optgroup_val == 1) {
            $('#category_update_button').val("Remove Category");
        } else if (optgroup_val == 2) {
            $('#category_update_button').val("Add Category");
        } else {
            console.log("ERROR: Wrong category button value");
        }
    }
    function updateTutorialAssociatedCategory() {
        var category_button_value = $('#category_update_button').val();
        var selected_option = $('#category_select :selected');

        if (category_button_value == "Add Category") {
            insertCategory("associated_category_group", selected_option);
        } else if (category_button_value == "Remove Category") {
            insertCategory("available_category_group", selected_option);
        } else {
            console.log("ERROR: Wrong category button value");
            return;
        }
        updateCategoryButton();
    }

    // Given the destination category_group and adds the current selected option to the destination group
    function insertCategory(category_group, option) {
        var did_insert = false;
        var category_opts = $('#' + category_group).children();
        // TODO: Implement binary search to insert
        $(category_opts).each(function() {
            if ($(this).text().localeCompare(option.text()) > 0) {
                $(option).insertBefore($(this));
                did_insert = true;
                return false;
            }
        });
        if (!did_insert) {
            $('#' + category_group).append(option);
        }
    }

    /*
        Helper Functions
    */

    function resetTutorial() {
        $("#id").html("");
        $("#title").val("");
        $("#content").val("");
        $("#select_id").val("-1");
        resetCategories();
    }
    function resetCategories() {
        $("#associated_category_group").children().each(function() {
            insertCategory("available_category_group", $(this));
        });
    }
  </script>
<%@ include file="footer.jsp" %>
