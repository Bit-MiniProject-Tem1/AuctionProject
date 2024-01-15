$(document).ready(function () {
    $('.remaining-time').each(function () {
        const $endDateString = $(this).find('.end_date').text();
        const $countdown = $(this); // 현재 카운트다운을 표시하는 요소에 대한 jQuery 객체
        const $days = $countdown.find('.days'); // 남은 날짜를 표시하는 요소
        const $hours = $countdown.find('.hours'); // 남은 시간을 표시하는 요소
        const $minutes = $countdown.find('.minutes'); // 남은 분을 표시하는 요소
        const $seconds = $countdown.find('.seconds'); // 남은 초를 표시하는 요소
        const $months = $countdown.find('.months'); // 남은 월을 표시하는 요소
        let countdownInterval; // 카운트다운을 갱신하기 위한 인터벌 변수
        const format = true; // 0을 포함한 두 자리 숫자로 표시할지 여부를 결정하는 변수 (true, false)

        // 0을 포함한 두 자리 숫자로 표시하는 함수
        function formatNumber(number) {
            return format ? (number < 10 ? `0${number}` : number.toString()) : number.toString();
        }

        // 감추기 함수
        function hideIfZero(value, $element) {
            if (value === '00') {
                $element.hide();
            } else {
                $element.show();
            }
        }

        // 카운트다운을 계산하는 함수
        function calculateCountdown() {
            // 현재 날짜와 시간을 가져옵니다.
            const now = new Date();
            // 목표 날짜와 시간을 설정합니다.
            const target = new Date($endDateString);
            // 목표 날짜까지의 시간 차이를 계산합니다.
            const diff = target - now;

            if (diff > 0) {
                // 시간 차이를 초, 분, 시간, 일로 분해합니다.
                const secDiff = Math.floor(diff / 1000);
                const minDiff = Math.floor(secDiff / 60);
                const hrDiff = Math.floor(minDiff / 60);
                const dayDiff = Math.floor(hrDiff / 24);

                // 남은 월 계산
                const remainingMonths = Math.floor(dayDiff / 30); // 간단하게 30일을 한 달로 가정
                const months = formatNumber(remainingMonths);
                hideIfZero(months, $months);

                // 남은 일 계산 (남은 일수에서 남은 월의 일수를 빼줌)
                const remainingDays = dayDiff - (remainingMonths * 30);
                const days = formatNumber(remainingDays);
                hideIfZero(days, $days);

                // 각 요소에 해당하는 값을 설정하여 화면에 표시합니다.
                $days.text(days + "일");
                $hours.text(formatNumber(hrDiff % 24) + "시간");
                $minutes.text(formatNumber(minDiff % 60) + "분");
                $seconds.text(formatNumber(secDiff % 60) + "초");
                $months.text(months + "개월");
            } else {
                // 카운트다운이 종료되면 메시지를 표시하고 인터벌을 중지합니다.
                $countdown.html('종료되었습니다.');
                clearInterval(countdownInterval);
            }
        }

        // 초기 카운트다운 값을 설정하고 1초마다 업데이트합니다.
        calculateCountdown();
        countdownInterval = setInterval(calculateCountdown, 1000);
    });
});