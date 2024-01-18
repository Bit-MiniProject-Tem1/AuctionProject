var fileNo = 0;
var filesArr = new Array();
var representativeImg;

/* 첨부파일 추가 */
function addFile(obj) {
    var maxFileCnt = 5;   // 첨부파일 최대 개수
    var attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
    var remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
    var curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    }

    for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {

        const file = obj.files[i];

        // 첨부파일 검증
        if (validation(file)) {
            // 파일 배열에 담기
            var reader = new FileReader();
            reader.onload = function () {
                filesArr.push(file);
            };
            reader.readAsDataURL(file)

            // 목록 추가
            let htmlData = '';
            htmlData += '<div id="file' + fileNo + '" class="filebox">';
            htmlData += '   <p class="name">' + file.name + '</p>';
            htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ')"><i class="bi bi-x-circle"></i></a>';
            if (fileNo == 0) {
                htmlData += '   <a class="represent" onclick="representativeFile(' + fileNo + ')"><i class="bi bi-check-circle-fill"></i></a>';
                representativeImg = file;
                console.log(representativeImg);
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
    // 초기화
    document.querySelector("input[type=file]").value = "";
}

/* 첨부파일 검증 */
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

/* 첨부파일 삭제 */
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

function representativeFile(num) {
    var check;
    for (var i = 0; i < filesArr.length; i++) {
        check = $("#file" + i + " .represent i");
        if (i != num) {
            check.addClass("bi-check-circle");
            check.removeClass("bi-check-circle-fill");
            representativeImg = filesArr[num];
        }
    }

    check = $("#file" + num + " .represent i");
    check.addClass("bi-check-circle-fill");
    check.removeClass("bi-check-circle");

    representativeImg = filesArr[num];
    console.log(representativeImg);
}

$(() => {
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

        // for (var i = 0; i < filesArr.length; i++) {
        //     // 삭제되지 않은 파일만 폼데이터에 담기
        //     if (!filesArr[i].is_delete) {
        //         formData.append("uploadFiles", filesArr[i]);
        //     }
        // }

        let dt = new DataTransfer();
        for (var i = 0; i < filesArr.length; i++) {
            // 삭제되지 않은 파일만 폼데이터에 담기
            if (!filesArr[i].is_delete) {
                dt.items.add(filesArr[i]);
            }
        }

        $("#uploadFiles")[0].files = dt.files;

        var formData = new FormData($("#insertForm")[0]);

        formData.set("currentBiddingPrice", 0);
        formData.set("representativeImgName", representativeImg.name);
        formData.set("description", theEditor.getData());
        // formData.set("startingPrice", document.getElementById("startingPrice").value.replace(',', ''));
        // formData.set("immediatePrice", document.getElementById("immediatePrice").value.replace(',', ''));

        $.ajax({
            method: 'POST',
            url: '/auction/register',
            data: formData,
            cache: false,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: (obj) => {
                console.log(obj);

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

/* 폼 전송 */
// function submitForm() {
//     if ($("#title").val() === '') {
//         alert('제목을 입력하세요.');
//         $("#title").focus();
//         return false;
//     }
//
//     if ($("#drop-topCategory").val() === '') {
//         alert('카테고리를 선택하세요.');
//         $("#drop-topCategory").focus();
//         return false;
//     }
//
//     if ($("#startingPrice").val() === '') {
//         alert('시작가를 입력하세요.');
//         $("#startingPrice").focus();
//         return false;
//     }
//
//     if ($("#immediatePrice").val() === '') {
//         alert('즉시입찰가를 입력하세요.');
//         $("#immediatePrice").focus();
//         return false;
//     }
//
//     if ($("#endDate").val() === '') {
//         alert('경매 마감일을 입력하세요.');
//         $("#endDate").focus();
//         return false;
//     }
//
//     // for (var i = 0; i < filesArr.length; i++) {
//     //     // 삭제되지 않은 파일만 폼데이터에 담기
//     //     if (!filesArr[i].is_delete) {
//     //         formData.append("uploadFiles", filesArr[i]);
//     //     }
//     // }
//
//     let dt = new DataTransfer();
//     for (var i = 0; i < filesArr.length; i++) {
//         // 삭제되지 않은 파일만 폼데이터에 담기
//         if (!filesArr[i].is_delete) {
//             dt.items.add(filesArr[i]);
//         }
//     }
//
//     $("#uploadFiles")[0].files = dt.files;
//
//     var formData = new FormData($("#insertForm")[0]);
//
//     formData.set("currentBiddingPrice", 0);
//     formData.set("representativeImgName", representativeImg.name);
//     formData.set("description", theEditor.getData());
//     // formData.set("startingPrice", document.getElementById("startingPrice").value.replace(',', ''));
//     // formData.set("immediatePrice", document.getElementById("immediatePrice").value.replace(',', ''));
//
//     $.ajax({
//         method: 'POST',
//         url: '/auction/register',
//         data: formData,
//         cache: false,
//         enctype: 'multipart/form-data',
//         processData: false,
//         contentType: false,
//         success: (obj) => {
//             console.log(obj);
//
//             alert(obj.item.msg);
//             location.href = "/auction/goods-list";
//         },
//         error: (err) => {
//             console.log(err);
//             alert(err.responseJSON.errorMessage);
//         }
//     });
// }