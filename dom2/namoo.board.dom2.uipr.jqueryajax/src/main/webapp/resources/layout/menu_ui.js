/**
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 2.
 *
 * @title Menu UI JS
 *
 */

// init-func =====================================

// Namespace
var menu = menu || {};

// Initailize.
$(document).ready(function () {
    //
    menu.bindEvent();
});

// Bind Event.
menu.bindEvent = function () {
    //
    $('#menu_home').click(function () {
        document.location.href = nbConst.urlPath.home;
    });
    $('#menu_boards').click(function () {
        document.location.href = nbConst.urlPath.boards;
    });
    $('#menu_logout').click(function () {
        document.location.href = nbConst.urlPath.logout;
    });


}


// process-func ==================================