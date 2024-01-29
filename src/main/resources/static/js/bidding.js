function save() {


	$("#order").on("click", () => {

		var parameter = {
			payment: $("#payment").val(),
			biddingPrice: $("")
		}

		var param = JSON.stringify(parameter);
		var url = "/auction/bidding/info"
		$.ajax({
			url: url,
			method: 'POST',
			url: '/auction/bidding/info',
			contentType: "application/json",
			data: param,
			success: (obj) => {
				alert(obj.item.msg);
				location.href = "/";
			},
			error: (err) => {
				console.log(err);
				alert(err.responseJSON.errorMessage);
			}

		});
	});
}
