$(document).ready(function () {
    // 현재 URL에서 "target" 파라미터 값을 가져옴
    var urlParams = new URLSearchParams(window.location.search);
    var targetParamValues = urlParams.get("target");

    // ','로 구분된 값이 있을 경우 배열로 분리
    var selectedValues = targetParamValues ? targetParamValues.split(',') : [];

    // 가져온 파라미터 값과 일치하는 모든 체크박스에 checked 속성 추가
    selectedValues.forEach(function (paramValue) {
        $('input[name="goods-target"][value="' + paramValue + '"]').prop('checked', true);
    });

    // 체크박스 변경 이벤트 핸들러
    $('input[name="goods-target"]').change(function () {
        var currentURL = new URL(window.location.href);
        var paramKey = "target";

        // 이미 있는 파라미터 확인
        var existingParamValues = currentURL.searchParams.getAll(paramKey);

        // 선택된 값
        var selectedValues = $('input[name="goods-target"]:checked').map(function () {
            return this.value;
        }).get();

        // 기존 값 제거
        existingParamValues.forEach(function (existingValue) {
            currentURL.searchParams.delete(paramKey, existingValue);
        });

        // 선택된 값 추가
        if (selectedValues.length > 0) {
            currentURL.searchParams.append(paramKey, selectedValues.join(','));
        } else {
            // 선택된 값이 없으면 해당 파라미터 제거
            currentURL.searchParams.delete(paramKey);
        }

        // 현재 URL에 파라미터가 없는 경우
        if (!existingParamValues.length && !selectedValues.length) {
            return;
        }
        window.location.href = currentURL;
    });

    // 현재 URL 가져오기
    var currentUrl = window.location.href;

    // closing 파라미터의 존재 여부 확인
    var hasClosingParam = currentUrl.includes('closing=E');

    // #closing-auction-checkbox 체크 여부 설정
    $('#closing-auction-checkbox').prop('checked', hasClosingParam);

    $('#closing-auction-checkbox').change(function () {
        var currentUrl = window.location.href;
        var hasClosingParam = currentUrl.includes('closing=E');
        var isChecked = $('#closing-auction-checkbox').is(':checked');

        if (isChecked && !hasClosingParam) {
            currentUrl += (currentUrl.includes('?') ? '&' : '?') + 'closing=E';
        } else if (!isChecked && hasClosingParam) {
            currentUrl = currentUrl.replace('?closing=E', '').replace('&closing=E', '');
        }

        window.location.href = currentUrl;
    });
});
