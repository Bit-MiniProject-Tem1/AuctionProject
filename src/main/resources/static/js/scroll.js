document.addEventListener('DOMContentLoaded', function () {

    var Content = document.querySelector('.dropdown-content');

    var listBox = document.querySelector('.list_box');

    var Box = document.querySelectorAll('.content_box');

    // 최대 표시 아이템 수
    var Item = 5;

    // 최대 높이를 설정.
    var maxHeight = 300;

    // 아이템 수가 한계를 초과하는지 확인.
    if (Box.length > Item) {
        // 스크롤바를 활성화하기 위해 클래스를 추가.
        listBox.classList.add('scrollable');

        // 드롭다운 콘텐츠의 최대 높이를 설정.
        Content.style.maxHeight = maxHeight + 'px';
    }
});


function toggleHeart() {
    const heartIcon1 = document.getElementsByClassName('favorite-icon')[0];
    const ClassName1 = "bi-heart";
    const ClassName2 = "bi-heart-fill";

    if (heartIcon1.classList.contains(ClassName1)) {
        heartIcon1.classList.remove(ClassName1);
        heartIcon1.classList.add(ClassName2);
    } else if (heartIcon1.classList.contains(ClassName2)) {
        heartIcon1.classList.remove(ClassName2);
        heartIcon1.classList.add(ClassName1);
    }
}

