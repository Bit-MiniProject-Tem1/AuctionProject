var originFileArr;
var originRepresentativeImgName;
var startingPrice;
var immediatePrice;
var description;

var fileNo = 0;
var filesArr = new Array(); //originFiles
var representativeImg;
var representativeName;

const deletefilesArr = new Array();

function addFile(obj) {
    var maxFileCnt = 5;
    var attFileCnt = document.querySelectorAll('.filebox').length;
    var delFileCnt = deletefilesArr.length;
    var remainFileCnt = maxFileCnt - attFileCnt;
    var curFileCnt = obj.files.length;

    if (curFileCnt > remainFileCnt + delFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    }

    for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
        const file = obj.files[i];

        if (validation(file)) {
            var reader = new FileReader();
            reader.onload = function () {
                filesArr.push(file);
            };
            reader.readAsDataURL(file)

            console.log(originRepresentativeImgName);
            let htmlData = '';
            htmlData += '<div id="file' + fileNo + '" class="filebox">';
            htmlData += '   <p class="name">' + file.name + '</p>';
            htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ')"><i class="bi bi-x-circle"></i></a>';
            if (originRepresentativeImgName == null && fileNo == 0) {
                htmlData += '   <a class="represent" onclick="representativeFile(' + fileNo + ')"><i class="bi bi-check-circle-fill"></i></a>';
                representativeImg = file;
                representativeName = representativeImg.name;

            } else {
                htmlData += '   <a class="represent" onclick="representativeFile(' + fileNo + ')"><i class="bi bi-check-circle"></i></a>';
            }
            htmlData += '</div>';
            $('.file-list').append(htmlData);
            fileNo++;
        } else {
            continue;
        }
    }

    document.querySelector("input[type=file]").value = "";
}

function validation(obj) {
    const fileTypes = ['image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

function deleteFile(num) {
    if (document.querySelector("#file" + num).hasAttribute('data-saveDB')) {
        deletefilesArr.push(num);
    } else {
        filesArr[num].is_delete = true;
    }
    console.log(deletefilesArr);
    document.querySelector("#file" + num).remove();
}

function representativeFile(num) {
    var check;
    for (var i = 0; i < filesArr.length; i++) {
        check = $("#file" + i + " .represent i");
        if (i != num) {
            check.addClass("bi-check-circle");
            check.removeClass("bi-check-circle-fill");
        }
    }

    if (originFileArr != null) {
        for (var i = 0; i < originFileArr.length; i++) {
            check = $("#file" + originFileArr[i].id + " .represent i");
            console.log(originFileArr[i].id);
            if (originFileArr[i].id != num) {
                check.addClass("bi-check-circle");
                check.removeClass("bi-check-circle-fill");
            } else {
                check = $("#file" + num + " .represent i");
                check.addClass("bi-check-circle-fill");
                check.removeClass("bi-check-circle");

                representativeName = originFileArr[i].fileName;
            }
        }
    }

    if (num <= 5) {
        check = $("#file" + num + " .represent i");
        check.addClass("bi-check-circle-fill");
        check.removeClass("bi-check-circle");

        representativeImg = filesArr[num];
        representativeName = filesArr[num].name;
    }

    console.log(representativeName);
}

$(() => {
    let editor;

    ClassicEditor.create(document.querySelector('#editor'), {
        toolbar: ['heading', '|', 'undo', 'redo', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'imageUpload'],
        shouldNotGroupWhenFull: false,
        language: "ko",
        ckfinder: {
            uploadUrl: '/auction/img/upload'
        }
    }).then(newEditor => {
        editor = newEditor;
        if (description != null) {
            editor.setData(description);
        }
    }).catch(error => {
        console.error(error);
    });

    const today = new Date();
    const minDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3);
    let date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -5);

    if (new Date(date) < minDate) {
        date = minDate.toISOString().slice(0, -5);
    }

    $("#endDate").val(date);
    $("#endDate").attr("min", date);

    $("#update").on("click", () => {
        if ($("#title").val() === '') {
            alert('제목을 입력하세요.');
            $("#title").focus();
            return false;
        }

        if ($("#immediatePrice").val() === '') {
            alert('즉시입찰가를 입력하세요.');
            $("#immediatePrice").focus();
            return false;
        }

        if ($("#endDate").val() === '') {
            alert('경매 마감일을 입력하세요.');
            $("#endDate").focus();
            return false;
        }

        if ($("#startingPrice").val() > $("#immediatePrice").val()) {
            alert('즉시 입찰가는 시작가보다 낮을 수 없습니다.');
            $("#immediatePrice").focus();
            return false;
        }

        let dt = new DataTransfer();
        for (var i = 0; i < filesArr.length; i++) {
            if (!filesArr[i].is_delete) {
                dt.items.add(filesArr[i]);
            }
        }

        $("#uploadFiles")[0].files = dt.files;

        var formData = new FormData($("#insertForm")[0]);

        formData.set("deleteAuctionImgList", deletefilesArr);
        formData.set("currentBiddingPrice", 0);
        formData.set("representativeImgName", representativeName);
        formData.set("description", editor.getData());
        if ($("#drop-topCategory").val() === "" || $("#drop-topCategory").val() == null) {
            formData.set("categoryId", categoryId);
        }

        console.log(formData);

        $.ajax({
            method: 'put',
            url: '/auction/goods-update',
            data: formData,
            cache: false,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: (obj) => {
                alert(obj.item.msg);
                location.href = "/auction/goods-list";
            },
            error: (err) => {
                console.log(err);
                alert(err.responseJSON.errorMessage);
            }
        });
    });

    $("#register").on("click", () => {
        if ($("#title").val() === '') {
            alert('제목을 입력하세요.');
            $("#title").focus();
            return false;
        }

        if ($("#drop-topCategory").val() === '') {
            alert('카테고리를 선택하세요.');
            $("#drop-topCategory").focus();
            return false;
        }

        if ($("#startingPrice").val() === '') {
            alert('시작가를 입력하세요.');
            $("#startingPrice").focus();
            return false;
        }

        if ($("#immediatePrice").val() === '') {
            alert('즉시입찰가를 입력하세요.');
            $("#immediatePrice").focus();
            return false;
        }

        if ($("#endDate").val() === '') {
            alert('경매 마감일을 입력하세요.');
            $("#endDate").focus();
            return false;
        }

        if ($("#startingPrice").val() < 5000) {
            alert('경매는 시작가는 5000원 이상이어야 합니다.');
            $("#startingPrice").focus();
            return false;
        }

        if ($("#startingPrice").val() > $("#immediatePrice").val()) {
            alert('즉시 입찰가는 시작가보다 낮을 수 없습니다.');
            $("#immediatePrice").focus();
            return false;
        }

        let dt = new DataTransfer();
        for (var i = 0; i < filesArr.length; i++) {
            if (!filesArr[i].is_delete) {
                dt.items.add(filesArr[i]);
            }
        }

        $("#uploadFiles")[0].files = dt.files;

        var formData = new FormData($("#insertForm")[0]);

        formData.set("currentBiddingPrice", 0);
        formData.set("representativeImgName", representativeImg.name);
        formData.set("description", editor.getData());

        $.ajax({
            method: 'POST',
            url: '/auction/register',
            data: formData,
            cache: false,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: (obj) => {
                alert(obj.item.msg);
                location.href = "/auction/goods-list";
            },
            error: (err) => {
                console.log(err);
                alert(err.responseJSON.errorMessage);
            }
        });
    });
});