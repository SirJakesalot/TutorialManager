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
                <option value="0">Option 1</option>
                <option value="2">Option 2</option>
                <option value="6">Option 6</option>
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
      <td><input type="button" value="Delete Tutorial" class="submit" onclick="deleteTutorial();"/></td>
      <td><input type="button" value="Update Tutorial" class="submit" onclick="updateTutorialInfo();"/></td>
    </tr>
  </table>
  <script>
    $(document).ready(updateCategoryButton);
    var context = "${context}";
    var dashboard_select_url = context + "/dashboard_select";
    var dashboard_update_url = context + "/dashboard_update";
    var dashboard_delete_url = context + "/dashboard_delete";

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
        var params = "tutorial_id=" + idval + "&tutorial_title=" + titleval + "&tutorial_content=" + contentval;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var arry = JSON.parse(xmlhttp.responseText);
                addTutorialSelect(arry);
            }
        };
        xmlhttp.open("POST", dashboard_update_url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(params);
    }
    function deleteTutorial() {
        var idval = encodeURIComponent($("#select_id").val());
        var params = "tutorial_id=" + idval;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
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
        $("#id").html(arry[0].id);
        $("#title").val(arry[0].title);
        $("#content").val(arry[0].content);
    }

    function addTutorialSelect(arry) {
        removeTutorialSelect(arry);
        $("#select_id").append($('<option value=' + arry[1].id + '>' + arry[1].title + '</option>'));
    }
    function removeTutorialSelect(arry) {
        resetTutorial();
        $("#msg").html(arry[0].message);
        $("#select_id option[value='" + arry[1].id + "']").remove();
    }
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
    function addTutorialAssociatedCategory() {
        var selected_option = $('#category_select :selected');
        $('#associated_category_group').append(selected_option);
        $('#category_select').val(selected_option.val());
        updateCategoryButton();
    }
    function removeTutorialAssociatedCategory() {
        var selected_option = $('#category_select :selected');
        $('#available_category_group').append(selected_option);
        $('#category_select').val(selected_option.val());
        updateCategoryButton();
    }
    function updateTutorialAssociatedCategory() {
        var category_button_value = $('#category_update_button').val();
        if (category_button_value == "Add Category") {
            addTutorialAssociatedCategory();
        } else if (category_button_value == "Remove Category") {
            removeTutorialAssociatedCategory();
        } else {
            console.log("ERROR: Wrong category button value");
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
    }
  </script>
<%@ include file="footer.jsp" %>
