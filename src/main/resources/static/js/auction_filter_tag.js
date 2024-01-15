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
        var selectedValues = $('input[name="goods-target"]:checked').map(function () {
            return this.value;
        }).get();

        var currentURL = window.location.href;
        var paramKey = "target";

        // 이미 있는 파라미터 확인
        var existingParamIndex = currentURL.indexOf(paramKey + '=');

        if (existingParamIndex > -1) {
            var paramStartIndex = existingParamIndex + paramKey.length + 1;
            var paramEndIndex = currentURL.indexOf('&', paramStartIndex);

            var existingParamValue = paramEndIndex === -1 ?
                currentURL.slice(paramStartIndex) :
                currentURL.slice(paramStartIndex, paramEndIndex);

            // ','로 구분된 값이 있을 경우 배열로 분리
            var existingParamValues = existingParamValue.split(',');

            // 기존 파라미터에서 선택된 값 제외
            var filteredValues = existingParamValues.filter(function (value) {
                return selectedValues.indexOf(value) === -1;
            });

            // 새로운 파라미터 값 구성
            var newParamValue = filteredValues.concat(selectedValues).join(',');

            var newURL = currentURL.slice(0, paramStartIndex) + newParamValue +
                (paramEndIndex === -1 ? '' : currentURL.slice(paramEndIndex));
        } else {
            var newParam = paramKey + '=' + selectedValues.join(',');
            var newURL = currentURL.indexOf('?') > -1 ? currentURL + '&' + newParam : currentURL + '?' + newParam;
        }

        window.location.href = newURL;
    });

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
