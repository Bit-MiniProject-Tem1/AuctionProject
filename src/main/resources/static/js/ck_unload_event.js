$(() => {
    checkUnload = true;
    $(window).on("beforeunload", function () {
        if (checkUnload) {
            $.ajax({
                method: 'put',
                url: '/auction/img/notSave',
                cache: false,
                error: (err) => {
                    console.log(err);
                    alert(err.responseJSON.errorMessage);
                }
            });
        }
    });
});