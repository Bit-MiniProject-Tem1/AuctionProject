document.addEventListener("DOMContentLoaded", function () {
    // 각 컨테이너와 '더 보기' 버튼을 선택합니다.
    var containers = [
        document.querySelector('.home_product_1'),
        document.querySelector('.home_product_2'),
        document.querySelector('.home_product_3')
    ];

    var loadMoreBtns = [
        document.getElementById('loadMoreBtn1'),
        document.getElementById('loadMoreBtn2'),
        document.getElementById('loadMoreBtn3')
    ];

    // 초기에 표시할 상품 수와 증가할 수를 설정합니다.
    var itemsToShow = [4, 4, 4];
    var increments = [4, 4, 4];
    var maxItems = 11; // 최대 보여줄 항목 수

    // 각 컨테이너에 대한 현재 수에 따라 항목을 표시하거나 숨기는 함수를 작성합니다.
    function showHideItems(containerIndex) {
        var container = containers[containerIndex];
        var items = container.querySelectorAll('.search-goods');
        for (var i = 0; i < items.length; i++) {
            if (i < itemsToShow[containerIndex]) {
                items[i].style.display = 'block';
            } else {
                items[i].style.display = 'none';
            }
        }
    }

    // 초기에 각 컨테이너에 대한 일부 항목을 표시합니다.
    for (var i = 0; i < containers.length; i++) {
        showHideItems(i);
    }

    // 각 '더 보기' 또는 '접기' 버튼 클릭 시 상태를 변경하고 스크롤합니다.
    for (var i = 0; i < loadMoreBtns.length; i++) {
        loadMoreBtns[i].addEventListener('click', function (index) {
            return function () {
                if (itemsToShow[index] < maxItems) {
                    itemsToShow[index] += increments[index];
                    if (itemsToShow[index] >= maxItems) {
                        loadMoreBtns[index].innerText = '접기';
                    }
                } else {
                    itemsToShow[index] = 4;
                    loadMoreBtns[index].innerText = '더 보기';
                }

                showHideItems(index);

                // '더 보기' 버튼을 눌렀을 때만 스크롤합니다.
                if (loadMoreBtns[index].innerText === '더 보기') {
                    containers[index].scrollIntoView({ behavior: 'smooth' });
                }
            };
        }(i));
    }
});
