/**
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:wckang@nextree.co.kr">Kang Woo Chang</a>
 * @since 2015. 2. 10.
 */

var PANEL_OBJ = {
    panel_data : null,
    req_param : null
};

function sendRequest(url, sendData, callback, type) {
    //
    if(! sessionStorage.getItem("loginUserEmail")){
        location.href = "http://localhost/namooboard/login.html";
        return;
    }

    $.ajax({
        type: type || "GET",
        url: url,
        cache : false,
        contentType: "application/json",
        data : sendData
    })
    .done(function (result) {
        if (callback) callback(result);
    })
    .error(function(jqXHR, status, error){
        if(jqXHR.status == 404){
            location.href = "/namooboard/common/errors/error_404.html";
        }
        else{
            $("#msgLayer").load("/namooboard/common/message/error.html", function(){
                openErrLayerPop(jqXHR.responseJSON || {detailMessage : jqXHR.statusText});
            });
        }
    });
}

function validation() {
    //
    var isValid = true;
    $("[required]").each(function(){
        isValid = !! $(this).val();
        if(! isValid){
            alert("필수값을 입력하세요.");
            $(this).focus();
            return false;
        }
    });

    return isValid;
}

function loadPanel(targetEleId, url, data, callback) {
    //
    PANEL_OBJ.req_param = data;

    $("#" + targetEleId).children().remove();
    $("#" + targetEleId).load(url, function(){
        if(typeof PANEL_OBJ.panel_data.panel_init === "function") PANEL_OBJ.panel_data.panel_init();
        if(callback) callback();
    });
}

function getFormattedDate(dateValue) {
    //
    var date = new Date(dateValue);
    var month = date.getMonth() + 1;
    var pad = '00';
    var month = (pad + month).slice(-pad.length);

    return date.getFullYear() + "-" + month + "-" + date.getDate();
}

function openInfoLayerPop(result, callback) {
    //
    $("#msgLayer").load("/namooboard/common/message/info.html", function(){
        var $popRoot = $("#msgLayer #container");

        if(result) {
            $popRoot.find("#infoMsg").text(result);
        }

        $.blockUI({
            message : $popRoot
        });

        $popRoot.find("a.btn").click(function(){
            //$("#msgLayer").children().remove();
            $.unblockUI();
            if(callback) callback();
        });
    });
}

function openErrLayerPop(errorObj, callback) {
    //
    $("#msgLayer").load("/namooboard/common/message/error.html", function(){

        var $popRoot = $("#msgLayer #container");

        if(errorObj) {
            $popRoot.find("#errMsg").text(errorObj.detailMessage);
        }

        $.blockUI({
            message : $popRoot
        });

        $popRoot.find("a.btn").click(function(){
            //$("#msgLayer").children().remove();
            $.unblockUI();
            if(callback) callback();
        });
    });
}

function openConfirmLayerPop(confirmMsg, okFn, cancelFn) {
    //
    $("#msgLayer").load("/namooboard/common/message/confirm.html", function(){
        var $popRoot = $("#msgLayer #container");

        if(confirmMsg) {
            $popRoot.find("#confirmMsg").text(confirmMsg);
        }

        $.blockUI({
            message : $popRoot
        });

        $popRoot.find("#lyr_btn_ok").click(function(){
            $.unblockUI();
            if(okFn) okFn();
        });

        $popRoot.find("#lyr_btn_cancel").click(function(){
            $.unblockUI();
            if(cancelFn) cancelFn();
        });
    });
}

function logout() {
    sessionStorage.clear();
    location.href = "/namooboard/login.html";
}