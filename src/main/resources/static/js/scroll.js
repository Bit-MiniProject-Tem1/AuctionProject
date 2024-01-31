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
                },
                error: (err) => {
                    // 에러 처리 로직 추가
                }
            })
        }
    };
});