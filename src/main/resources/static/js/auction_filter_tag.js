$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var targetParamValues = urlParams.get("target");

    var selectedValues = targetParamValues ? targetParamValues.split(',') : [];

    selectedValues.forEach(function (paramValue) {
        $('input[name="goods-target"][value="' + paramValue + '"]').prop('checked', true);
        addFilterTag(paramValue);
    });

    function addFilterTag(paramValue) {
        var filterName;
        switch (paramValue) {
            case 'baby':
                filterName = '아기';
                break;
            case 'children':
                filterName = '아이';
                break;
            case 'female':
                filterName = '여성';
                break;
            case 'male':
                filterName = '남성';
                break;
            case 'unisex':
                filterName = '유니섹스';
                break;
        }

        var newFilterTag = $('<div class="filter-tag"><i class="bi bi-funnel"></i><span>' + filterName + '</span></div>');

        $('.filter-tag-group').append(newFilterTag);

        var sortOption = urlParams.get('sort');
        var buttonElement = $('.filter-sorting .btn');

        switch (sortOption) {
            case 'byViews':
                buttonElement.text('조회순');
                break;
            case 'byRegistration':
                buttonElement.text('등록순');
                break;
            case 'byClosingSoon':
                buttonElement.text('마감 임박순');
                break;
            case 'byLowPrice':
                buttonElement.text('낮은 가격순');
                break;
            case 'byHighPrice':
                buttonElement.text('높은 가격순');
                break;
            case 'byMostBids':
                buttonElement.text('입찰 많은순');
                break;
            case 'byMostInterest':
                buttonElement.text('관심 많은순');
                break;
            default:
                buttonElement.text('조회순');
                break;
        }
    }

    $('input[name="goods-target"]').change(function () {
        var currentURL = new URL(window.location.href);
        var paramKey = "target";
        var existingParamValues = currentURL.searchParams.getAll(paramKey);
        var selectedValues = $('input[name="goods-target"]:checked').map(function () {
            return this.value;
        }).get();

        existingParamValues.forEach(function (existingValue) {
            currentURL.searchParams.delete(paramKey, existingValue);
        });

        if (selectedValues.length > 0) {
            currentURL.searchParams.append(paramKey, selectedValues.join(','));
        } else {
            currentURL.searchParams.delete(paramKey);
        }

        if (!existingParamValues.length && !selectedValues.length) {
            return;
        }
        window.location.href = currentURL;
    });

    var currentUrl = window.location.href;
    var hasClosingParam = currentUrl.includes('closing=E');

    $('#closing-auction-checkbox').prop('checked', hasClosingParam);

    if (hasClosingParam) {
        var newFilterTag = $('<div class="filter-tag"><i class="bi bi-funnel"></i><span>' + '마감경매포함' + '</span></div>');

        $('.filter-tag-group').append(newFilterTag);
    }

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

    $('.filter-sorting .dropdown-item').click(function () {
        var koreanSortOption = $(this).text().trim();
        var englishSortOption;

        switch (koreanSortOption) {
            case '조회순':
                englishSortOption = 'byViews';
                break;
            case '등록순':
                englishSortOption = 'byRegistration';
                break;
            case '마감 임박순':
                englishSortOption = 'byClosingSoon';
                break;
            case '낮은 가격순':
                englishSortOption = 'byLowPrice';
                break;
            case '높은 가격순':
                englishSortOption = 'byHighPrice';
                break;
            case '입찰 많은순':
                englishSortOption = 'byMostBids';
                break;
            case '관심 많은순':
                englishSortOption = 'byMostFavorite';
                break;
            default:
                englishSortOption = 'unknown';
                break;
        }

        var currentUrl = window.location.href;

        var urlWithoutSort = currentUrl.replace(/([&?]sort=)[^&]*(&|$)/, '$2');

        var newUrl = urlWithoutSort + (urlWithoutSort.includes('?') ? '&' : '?') + 'sort=' + encodeURIComponent(englishSortOption);


        window.location.href = newUrl;
    });
});