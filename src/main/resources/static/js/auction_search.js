document.addEventListener("DOMContentLoaded", function() {
    var searchForm = document.getElementById('searchForm');
    // 이 코드는 searchInput 엘리먼트에서 data-show-alert-th 데이터 속성 값을 가져와서, 이 값이 문자열 'true'와 일치하는지 확인하여 showAlertValue 변수에 할당합니다.
    var showAlertValue = document.getElementById('searchInput').dataset.showAlertTh === 'true';

    if (showAlertValue) {
        alert('검색 결과가 없습니다. 전체 항목의 제품을 보여드립니다.');
    }

// 알림 메시지를 표시하는 함수
    function showAlert(message) {
        alert(message);
    }

    // 검색 폼을 서버로 제출하는 함수
    function submitSearchForm() {
        var searchInput  = document.getElementById('searchInput');

        if (searchInput.value.trim() === '') {
            showAlert("검색어를 입력해주세요.");
            return ;
        }

        searchForm.submit();
    }

    // Enter 키를 처리하는 이벤트 핸들러
    function handleEnterKey(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            submitSearchForm();
        }
    }
    // Enter 키 이벤트 핸들러 등록
    searchForm.addEventListener('keypress', handleEnterKey);
});
