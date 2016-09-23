
function msgClose() {
    var div = this.parentElement;
    div.style.opacity = "0";
    setTimeout(function() { div.style.display = "none"; }, 600);
}

function msg_appear(span) {
    var div = span.parentElement;
    div.style.opacity = "1";
    setTimeout(function() { div.style.display = "block"; }, 600);
}

function addMsg(json) {
    var status = json.status;
    var message = json.message;
    var $div = $("<div />").addClass(status)
                           .html(message)
                           .fadeIn( "slow" );

    var $span = $('<span />').addClass("closebtn")
                             .html('&times;')
                             .click(msgClose);

    $div.append($span);
    $("#msgs").append($div);
}
function addInfoMsg() {
    addMsg({status: 'info', message: 'info message'});
}
function addErrorMsg() {
    addMsg({status: 'error', message: 'error message'});
}
function addWarningMsg() {
    addMsg({status: 'warning', message: 'warning message'});
}
function addSuccessMsg() {
    addMsg({status: 'success', message: 'success message'});
}

function load_fb() {
    window.fbAsyncInit = function() {
      FB.init({
        appId      : '1417659781582505',
        xfbml      : true,
        version    : 'v2.6'
      });
    };

    (function(d, s, id){
       var js, fjs = d.getElementsByTagName(s)[0];
       if (d.getElementById(id)) {return;}
       js = d.createElement(s); js.id = id;
       js.src = "//connect.facebook.net/en_US/sdk.js";
       fjs.parentNode.insertBefore(js, fjs);
     }(document, 'script', 'facebook-jssdk'));
}
