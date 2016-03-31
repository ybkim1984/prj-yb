/**
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:wckang@nextree.co.kr">Kang Woo Chang</a>
 * @since 2015. 2. 10.
 */

var $EMPTY_LIST = $("#emptyList");

/**
 * 게시판 목록 조회
 */
function findList() {
    //
    var data = {};
    var callback = function (result) {

        if (!result || result.length <= 0) {
            $EMPTY_LIST.show();
            $("#hideMsg").show();
        }
        else {
            drawBoardList(result);
        }
    };

    var url = "http://localhost/namooboard/sp/board";
    sendRequest(url, data, callback);
}

function getBoardInfo(usid, callback) {
    //
    var url = "/namooboard/sp/board/" + usid;
    var data = {};
    sendRequest(url, data, callback);
}

/**
 * 게시판 목록 그리기
 * @param list
 */
function drawBoardList(list) {
    //
    var listHtml = [];

    $.each(list, function(idx, item){
        var boardHtml = '';
        boardHtml += '<a href="javascript:;" class="list-group-item hidden-xs">';
        boardHtml += item.name;
        boardHtml += '</a>';

        var $boardHtml = $(boardHtml).click(function(){
            showBoard(item.usid);
        });
        listHtml.push($boardHtml);
    });

    $("#div_list").html(listHtml);
}

/**
 * 페이지 Navigation 그리기
 *
 * @param usid 게시판 ID
 * @param criteria 페이지 정보
 */
function drawPagination(criteria) {
    //
    if(criteria.totalItemCount <= 0) return;

    var pageHtml = '<li><a href="javascript:;" onclick="pstl.findPostList(1)">«</a></li>';

    var startPage = parseInt((criteria.page - 1) / criteria.navigateLimit) * criteria.navigateLimit + 1;
    var endPage = startPage + criteria.navigateLimit - 1;

    if(criteria.page > criteria.navigateLimit){
        pageHtml += '<li><a href="javascript:;" onclick="pstl.findPostList(' + (startPage - 1) + ')">◁</a></li>';
    }

    endPage = endPage < criteria.totalPageCount ? endPage : criteria.totalPageCount;
    for(var i = startPage; i <= endPage; i++){
        pageHtml += '<li';
        if(criteria.page == i){
            pageHtml += ' class="active"';
        }
        pageHtml += '>';
        pageHtml += '<a href="javascript:;" onclick="pstl.findPostList(' + i + ')">' + i + '</a></li>';
    }

    if(endPage < criteria.totalPageCount){
        pageHtml += '<li><a href="javascript:;" onclick="pstl.findPostList(' + (endPage + 1) + ')">▷</a></li>';
    }

    pageHtml += '<li><a href="javascript:;" onclick="pstl.findPostList(' + criteria.totalPageCount + ')">»</a></li>';

    $(".pagination").html(pageHtml);
}