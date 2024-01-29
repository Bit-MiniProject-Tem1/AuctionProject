

function save() {
	var radioVal = $('input[name="payment-option"]:checked').val();
	console.log(radioVal);
		var parameter = {
			payment: radioVal,
			// biddingPrice: $("#biddingPrice").val()
		};

		var param = JSON.stringify(parameter);
		var url = "/";
		console.log(param);
		console.log(parameter);

		$.ajax({
			url: url,
			method: 'POST',
			contentType: "application/json",
			data: param,
			success: () => {
				alert("success");
				location.href = "/";
			},
			error: (err) => {
				console.log(err);
				alert(err.responseJSON.errorMessage);
			}
		});
}
