document.addEventListener('DOMContentLoaded', function () {
    // 최초 로드 시 실행되는 코드

    // toggleHeart 함수를 전역 스코프에 선언
    window.toggleHeart = function(id) {

        var likeStatus = $("#likeStatus_" + id);
        console.log(likeStatus);

        if (likeStatus.hasClass("bi-heart")) {
            $.ajax({
                url: '/like/add/' + id,
                type: 'post',
                success: (obj) => {
                    console.log(likeStatus);
                    likeStatus.addClass("bi-heart-fill");
                    likeStatus.removeClass("bi-heart");
                    $("#likeSum").text(obj.item.likeSum);
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
                    likeStatus.removeClass("bi-heart-fill");
                    likeStatus.addClass("bi-heart");
                    $("#likeSum").text(obj.item.likeSum);
                },
                error: (err) => {
                    // 에러 처리 로직 추가
                }
            })
        }
    };
});