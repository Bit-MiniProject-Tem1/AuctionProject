$(document).ready(function () {
    $('#drop-topCategory').change(function () {
        var selectedValue = $(this).val();

        var currentURL = window.location.href;
        var baseURL = currentURL.split('/').slice(0, 3).join('/');

        $.ajax({
            url: baseURL + "/getSubCategory",
            type: "GET",
            data: {topCategoryId: selectedValue},
            success: function (data) {
                $('#drop-subCategory option[value!="none"]').remove();
                $.each(data, function (i) {
                    $("#drop-subCategory").append(
                        '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                    );
                });
            },
            error: function () {
                console.log("error");
            }
        });
    });
});