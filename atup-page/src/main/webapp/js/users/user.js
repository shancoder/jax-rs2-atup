/*POST*/
function createUser() {
    var userName = jQuery.trim(jQuery("#userName0").val());
    var password = jQuery("#password0").val();
    var hashPassword = md5(password);
    var postData = JSON.stringify({userName: userName, passWord: hashPassword, userRole: 4, status: 0});
    restSet(ATUP_USER_URI + USER_PATH, POST_METHOD, postData, renderCreate);
}
/*POST*/
function signUp() {
    var userName = jQuery.trim(jQuery("#userName0").val());
    var password = jQuery("#password0").val();
    var hashPassword = md5(password);
    var postData = JSON.stringify({userName: userName, passWord: hashPassword, userRole: 4, status: 0});
    restSet(ATUP_USER_URI + SIGNUP_PATH, POST_METHOD, postData, renderSignUp);
}
/*PUT*/
function updateUser() {
    var userName = jQuery.trim(jQuery("#userName").val());
    var password = jQuery("#password").val();
    var hashPassword = md5(password);
    var userRole = jQuery("#role").val();
    var putData = JSON.stringify({userName: userName, passWord: hashPassword, userRole: userRole});
    restSet(ATUP_USER_URI + USER_PATH, PUT_METHOD, putData, renderUpdate);
}
/*GET*/
function signIn() {
    var userName = jQuery("#userName").val();
    var password = jQuery.trim(jQuery("#password").val());
    var hashPassword = md5(password);
    var url = ATUP_USER_URI + SIGNIN_PATH + "?user=" + userName + "&password=" + hashPassword;

    restGet(url, GET_METHOD, renderSignIn);
}
/*RENDER*/
function renderCreate(data) {
    jQuery('#resultDiv').html("id=" + data.userId);
}
function renderUpdate(data) {
    var usersDiv = jQuery("#usersDiv");
    usersDiv.html("<div><span style='width:100px;display:inline-block;'>User ID</span>");
    usersDiv.append("<span style='width:100px;display:inline-block;'>User Name</span>");
    usersDiv.append("<span style='width:100px;display:inline-block;'>User Role</span></div>");
    usersDiv.append("<div><span style='width:100px;display:inline-block;'>");
    usersDiv.append(data.userId);
    usersDiv.append("</span><span style='width:100px;display:inline-block;'>");
    usersDiv.append(data.userName);
    usersDiv.append("</span><span style='width:100px;display:inline-block;'>");
    usersDiv.append(data.userRole);
    usersDiv.append("</span></div>");
}
function renderSignIn(data) {
    if (data.userId != null && data.userName != null && data.userRole != null) {
        storage.setItem("userId", data.userId);
        storage.setItem("userName", data.userName);
        storage.setItem("userRole", data.userRole);
        window.location.href = "index.html";
    } else {
        //TODO
    }
}
function renderSignUp(data) {
    if (data.userId != null && data.userName != null && data.userRole != null) {
        storage.setItem("userId", data.userId);
        storage.setItem("userName", data.userName);
        storage.setItem("userRole", data.userRole);
        window.location.href = "index.html";
    } else {
        //TODO
    }
}