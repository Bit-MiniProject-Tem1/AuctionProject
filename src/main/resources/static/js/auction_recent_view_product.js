let recentItems = [];
var productId;
getCookie("itemCookie");
function setCookie(name, value, days) {

    let isDuplicate = false;


    for (let i = 0; i < recentItems.length; i++) {
        if (recentItems[i] == value) {
            isDuplicate = true;
            recentItems.splice(i, 1);
            recentItems.unshift(value);
            break;
        }
    }

    if (!isDuplicate) {
        recentItems.unshift(value);
    }

    const MAX_COOKIES = 10;
    if (recentItems.length > MAX_COOKIES) {
        recentItems.shift();
    }


    let str = "";


    for(let i = 0; i < recentItems.length; i++) {
        if(i != recentItems.length - 1) {
            str += recentItems[i] + ",";
        } else {
            str += recentItems[i];
        }
    }

    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + str + expires + "; path=/";
}

function getCookie(value) {
    let str = document.cookie;
    let tmp1 = '';
    let tmp2 = [];

    if (str !== '') {
        const cookies = str.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            const cookieParts = cookie.split('=');
            const name = cookieParts[0];
            const cookieValue = cookieParts[1];

            if (name === value) {
                tmp1 = cookieValue;
                break;
            }
        }


    if (tmp1 !== '') {
        tmp2 = tmp1.split(",");
    }

    for (let i = 0; i < tmp2.length; i++) {
        recentItems[i] = tmp2[i];
    }

    displayRecentItems();
    return recentItems;
}


function displayRecentItems() {
    if (recentItems.length > 0) {
        $.ajax({
            url: "/auction/recent-items",
            type: "get",
            data: { recentItems: JSON.stringify(recentItems) },
            success: (obj) => {
                let htmlStr = "";

                for (let i = 0; i < obj.length; i++) {
                    htmlStr += `<li style="text-align: left" class="content_box">
                            <a class="a_tag" href="/auction/goods/${obj[i].id}">
                                <div class="content_boxes">
                                    <div class="list-img">
                                        <figure class="img_wrapper">
                                            <img class="d-block w-100" alt="" src="${obj[i].representativeImgUrl}">
                                        </figure>
                                    </div>
                                    <div class="list-title">${obj[i].title}</div>
                                </div>
                            </a>
                            <i class="bi bi-trash trash-icon" data-product-id="${obj[i].id}"></i>
                        </li>`;
                }
                $("#recent-items").html(htmlStr);

                $(".trash-icon").click(removeItem);
            },
            error: (err) => {
                console.log(err);
            }
        });
    } else {
        let html = "";

        html += `<li style="text-align: left" class="content_box">
                            <div class="content_boxes">
                                <div class="list-img">
                                    <figure class="img_wrapper">
                                        <img class="d-block w-100" alt="" src="https://png.pngtree.com/png-vector/20221125/ourmid/pngtree-no-image-available-icon-flatvector-illustration-picture-coming-creative-vector-png-image_40968940.jpg">
                                    </figure>
                                </div>
                                <div class="list-title-default">조회 할 상품이 존재하지 않습니다.</div>
                            </div>
                        </li>`;
        $("#recent-items").html(html);
    }
}

    function removeItem(event) {
    const productId = $(event.target).data('product-id');

    for (let i = 0; i < recentItems.length; i++) {
        if (productId == recentItems[i]) {
            recentItems.splice(i, 1);
            break;
        }
    }

    displayRecentItems();

    deleteCookie(productId);
}

function deleteCookie(productId) {

    var pastDate = new Date();
    pastDate.setTime(pastDate.getTime() - 1);
    document.cookie = "itemCookie=; expires=" + pastDate.toUTCString() + "; path=/";

    // 쿠키에서 값을 가져옴
    var cookieValue = getCookie("itemCookie");

    // 쿠키 값이 존재하는 경우에만 처리
    if (cookieValue) {

        var filteredItems = [];

        for (let i = 0; i < cookieValue.length; i++) {
            if (cookieValue[i] != productId) {
                filteredItems.push(cookieValue[i]);
            }
        }
        var currentDate = new Date();
        currentDate.setDate(currentDate.getDate() + 7); // 예시로 하루 뒤에 만료되도록 설정
        document.cookie = "itemCookie=" + filteredItems.join(',') + "; expires=" + currentDate.toUTCString() + "; path=/";
    }
}
}

