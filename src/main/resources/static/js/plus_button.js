document.addEventListener("DOMContentLoaded", function() {
    // 첫 번째 컨테이너와 '더 보기' 버튼을 선택합니다.
    var container1 = document.querySelector('.container.search-goods-list1');
    var loadMoreBtn1 = document.getElementById('loadMoreBtn1');

    // 두 번째 컨테이너와 '더 보기' 버튼을 선택합니다.
    var container2 = document.querySelector('.container.search-goods-list2');  // 다른 클래스 이름을 적절히 입력하세요.
    var loadMoreBtn2 = document.getElementById('loadMoreBtn2');

    // 세 번째 컨테이너와 '더 보기' 버튼을 선택합니다.
    var container3 = document.querySelector('.container.search-goods-list3');  // 다른 클래스 이름을 적절히 입력하세요.
    var loadMoreBtn3 = document.getElementById('loadMoreBtn3');

    // 초기에 표시할 상품 수와 증가할 수를 설정합니다.
    var itemsToShow1 = 4;
    var itemsToShow2 = 4;
    var itemsToShow3 = 4;
    var increment = 4;
    var maxItems = 12; // 최대 보여줄 항목 수

    // 첫 번째 컨테이너에 대한 현재 수에 따라 항목을 표시하거나 숨기는 함수를 작성합니다.
    function showHideItems1() {
        var items = container1.querySelectorAll('.search-goods');
        for (var i = 0; i < items.length; i++) {
            if (i < itemsToShow1) {
                items[i].style.display = 'block';
            } else {
                items[i].style.display = 'none';
            }
        }
    }

    // 두 번째 컨테이너에 대한 현재 수에 따라 항목을 표시하거나 숨기는 함수를 작성합니다.
    function showHideItems2() {
        var items = container2.querySelectorAll('.search-goods');
        for (var i = 0; i < items.length; i++) {
            if (i < itemsToShow2) {
                items[i].style.display = 'block';
            } else {
                items[i].style.display = 'none';
            }
        }
    }

    // 세 번째 컨테이너에 대한 현재 수에 따라 항목을 표시하거나 숨기는 함수를 작성합니다.
    function showHideItems3() {
        var items = container3.querySelectorAll('.search-goods');
        for (var i = 0; i < items.length; i++) {
            if (i < itemsToShow3) {
                items[i].style.display = 'block';
            } else {
                items[i].style.display = 'none';
            }
        }
    }

    // 초기에 각 컨테이너에 대한 일부 항목을 표시합니다.
    showHideItems1();
    showHideItems2();
    showHideItems3();

    // 첫 번째 '더 보기' 또는 '접기' 버튼 클릭 시 상태를 변경합니다.
    loadMoreBtn1.addEventListener('click', function() {
        if (itemsToShow1 < maxItems) {
            itemsToShow1 += increment;
            if (itemsToShow1 >= maxItems) {
                loadMoreBtn1.innerText = '접기';
            }
        } else {
            itemsToShow1 = 4;
            loadMoreBtn1.innerText = '더 보기';
        }

        showHideItems1();

        // 더 보기 버튼을 눌렀을 때만 스크롤합니다.
        if (loadMoreBtn1.innerText === '더 보기') {
            document.querySelector('.home_product').scrollIntoView({ behavior: 'smooth' });
        }
    });

    // 두 번째 '더 보기' 또는 '접기' 버튼 클릭 시 상태를 변경합니다.
    loadMoreBtn2.addEventListener('click', function() {
        if (itemsToShow2 < maxItems) {
            itemsToShow2 += increment;
            if (itemsToShow2 >= maxItems) {
                loadMoreBtn2.innerText = '접기';
            }
        } else {
            itemsToShow2 = 4;
            loadMoreBtn2.innerText = '더 보기';
        }

        showHideItems2();

        // 더 보기 버튼을 눌렀을 때만 스크롤합니다.
        if (loadMoreBtn2.innerText === '더 보기') {
            document.querySelector('.home_product:nth-child(3)').scrollIntoView({ behavior: 'smooth' });
        }
    });

    // 세 번째 '더 보기' 또는 '접기' 버튼 클릭 시 상태를 변경합니다.
    loadMoreBtn3.addEventListener('click', function() {
        if (itemsToShow3 < maxItems) {
            itemsToShow3 += increment;
            if (itemsToShow3 >= maxItems) {
                loadMoreBtn3.innerText = '접기';
            }
        } else {
            itemsToShow3 = 4;
            loadMoreBtn3.innerText = '더 보기';
        }

        showHideItems3();

        // 더 보기 버튼을 눌렀을 때만 스크롤합니다.
        if (loadMoreBtn3.innerText === '더 보기') {
            document.querySelector('.home_product:last-child').scrollIntoView({ behavior: 'smooth' });
        }
    });
});
