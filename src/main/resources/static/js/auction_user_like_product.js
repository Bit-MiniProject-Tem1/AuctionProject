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
                                <div class="list-title-default">관심 등록 상품이 존재하지 않습니다.</div>
                            </div>
                        </li>`;
        $("#recent-items").html(html);
    }
}