const joinForm = document.fr;

function sendit() {
    const uid = joinForm.id;
    if (uid.value == "") {
        swal({
            text: "아이디를 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            uid.focus();
        });
        return false;
    }
    const uemail = joinForm.email;
    if (uemail.value == "") {
        swal({
            text: "이메일을 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            uemail.focus();
        });
        return false;
    }
    const unick = joinForm.nick;
    if (unick.value == "") {
        swal({
            text: "이메일을 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            unick.focus();
        });
        return false;
    }
    const upw = joinForm.pw;
    if (upw.value == "") {
        swal({
            text: "비밀번호를 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
    if (upw.value.length < 6) {
        swal({
            text: "비밀번호는 6글자 이상이어야 합니다.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
    const upwCheck = joinForm.pw_check;
    if (upwCheck.value == "") {
        swal({
            text: "비밀번호를 확인해주세요.",
            closeOnClickOutside: false
        }).then(function () {
            upwCheck.focus();
        });
        return false;
    }
    if (upw.value != upwCheck.value) {
        swal({
            text: "비밀번호가 일치하지 않습니다.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
}