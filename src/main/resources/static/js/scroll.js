document.addEventListener('DOMContentLoaded', function () {
    // 최초 로드 시 실행되는 코드

    // toggleHeart 함수를 전역 스코프에 선언
    window.toggleHeart = function(id) {
        const likeStatus = $("#likeStatus_" + id);

        if (likeStatus.hasClass("bi-heart")) {
            $.ajax({
                url: '/like/add/' + id,
                type: 'post',
                success: (obj) => {
                    console.log(obj)
                    $("i[name='heart']").each(function() {
                        if($(this).attr("data-auction-id") == id) {
                            $(this).addClass("bi-heart-fill");
                            $(this).removeClass("bi-heart");
                        }
                    });
                    $("span[name='likeSum']").each(function() {
                        if($(this).attr("data-count-id") == id) {
                            $(this).text(obj.item.likeSum);
                        }
                    });
                    let likelist = obj.item.likeList;

                    let htmlStr = "";

                    for (let i = 0; i < likelist.length; i++) {
                        htmlStr += `<li style="text-align: left" class="content_box">
                            <a class="a_tag" href="/auction/goods/${likelist[i].id}">
                                <div class="content_boxes">
                                    <div class="list-img">
                                        <figure class="img_wrapper">
                                            <img class="d-block w-100" alt="" src="${likelist[i].representativeImgUrl}">
                                        </figure>
                                    </div>
                                    <div class="list-title">${likelist[i].title}</div>
                                </div>
                            </a>
                        </li>`;
                    }
                    $("#heart-items").html(htmlStr);
                },
                error: (err) => {
                    // 에러 처리 로직 추가
                }
            })
        } else {
            $.ajax({
                url: '/like/delete/' + id,
                type: 'post',
                success: (obj) => {

                    $("i[name='heart']").each(function() {
                        if($(this).attr("data-auction-id") == id) {
                            $(this).removeClass("bi-heart-fill");
                            $(this).addClass("bi-heart");
                        }
                    });
                    $("span[name='likeSum']").each(function() {
                        if($(this).attr("data-count-id") == id) {
                            $(this).text(obj.item.likeSum);
                        }
                    });
                    let likelist = obj.item.likeList;

                    let htmlStr = "";

                    if(likelist.length > 0) {
                        for (let i = 0; i < likelist.length; i++) {
                            htmlStr += `<li style="text-align: left" class="content_box">
                            <a class="a_tag" href="/auction/goods/${likelist[i].id}">
                                <div class="content_boxes">
                                    <div class="list-img">
                                        <figure class="img_wrapper">
                                            <img class="d-block w-100" alt="" src="${likelist[i].representativeImgUrl}">
                                        </figure>
                                    </div>
                                    <div class="list-title">${likelist[i].title}</div>
                                </div>
                            </a>
                            <a th:onclick="|javascript:toggleHeart('${likelist.id}')|" class="favorite-link">
                        <i th:id="'likeStatus_' + ${likelist.id}" class="bi-heart favorite-icon"></i>관심&nbsp;<span th:id="'likeSum_' + ${likelist.id}" th:text="${likeSum}"></span>
                    </a>
                        </li>`;
                        }
                        $("#heart-items").html(htmlStr);
                    } else {
                        htmlStr += `<li style="text-align: left" class="content_box">
                            <a class="a_tag" href="/auction/goods-list">
                                <div class="content_boxes">
                                    <div class="list-img">
                                        <figure class="img_wrapper">
                                            <img class="d-block w-100" alt="" src="https://png.pngtree.com/png-vector/20221125/ourmid/pngtree-no-image-available-icon-flatvector-illustration-picture-coming-creative-vector-png-image_40968940.jpg">
                                        </figure>
                                    </div>
                                    <div class="list-title-default">관심 등록 상품이 존재하지 않습니다.</div>
                                </div>
                            </a>
                        </li>`;
                        $("#heart-items").html(htmlStr);
                    }
                },
                error: (err) => {
                    // 에러 처리 로직 추가
                }
            })
        }
    };
});