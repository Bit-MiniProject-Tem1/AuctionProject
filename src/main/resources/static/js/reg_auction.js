const uploadFiles = [];

$(document).ready(function () {
    $("#uploadFiles").on("change", (e) => {
        const files = e.target.files;

        const fileArr = Array.prototype.slice.call(files);

        for (file of fileArr) {
            imageLoader(file);
        }
    });

    $("#register").on("click", () => {
        let dt = new DataTransfer();

        for (i in uploadFiles) {
            const file = uploadFiles[i];
            console.log(i);
            dt.items.add(file);
        }

        $("#uploadFiles")[0].files = dt.files;

        const formData = new FormData($("#insertForm")[0]);
        $.ajax({
            enctype: 'multipart/form-data',
            url: '/board/board',
            type: 'post',
            data: formData,
            processData: false,
            contentType: false,
            success: (obj) => {
                console.log(obj);

                alert(obj.item.msg);
                location.href = "/board/board-list";
            },
            error: (err) => {
                console.log(err);
                alert(err.responseJSON.errorMessage);
            }
        });
    });
});

const imageLoader = (file) => {
    // 추가된 파일 배열에 담기
    uploadFiles.push(file);
    let reader = new FileReader();
    reader.onload = (e) => {
        let img = document.createElement("img");
        img.setAttribute("style", "width: 100%; height: 100%; z-index: none;");

        if (file.name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)) {
            img.src = e.target.result;
        } else {
            img.src = "/images/defaultFileImg.png";
        }

        $("#attachZone").append(makeDiv(img, file));
    }

    reader.readAsDataURL(file);
}

const makeDiv = (img, file) => {
    let div = document.createElement("div");

    let btn = document.createElement("input");
    btn.setAttribute("type", "button");
    btn.setAttribute("value", "x");
    btn.setAttribute("deleteFile", file.name);

    btn.onclick = (e) => {

        const ele = e.srcElement;

        const deleteFile = ele.getAttribute("deleteFile");

        for (let i = 0; i < uploadFiles.length; i++) {
            if (deleteFile === uploadFiles[i].name) {
                uploadFiles.splice(i, 1);
            }
        }

        let dt = new DataTransfer();

        for (i in uploadFiles) {
            const file = uploadFiles[i];
            dt.items.add(file);
        }

        $("#uploadFiles")[0].files = dt.files;

        const parentDiv = ele.parentNode;
        $(parentDiv).remove();
    }

    let fileNameP = document.createElement("span");
    fileNameP.textContent = file.name;

    div.appendChild(img);
    div.appendChild(btn);
    div.appendChild(fileNameP);

    return div;
}