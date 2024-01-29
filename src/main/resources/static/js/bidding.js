<<<<<<< HEAD
var goodsId;

function isValid() {

  	const price = document.getElementById('price');

	if (!price.value.trim()) {
		alert('입력해 주세요.');
		price.value = '';
		price.focus();
		return false;
	}

	return true;
}

/**
 * 게시글 등록(생성/수정)
 */
=======
>>>>>>> e949af7b3f9a9cbeae7c2cdf9fe64fd1fcb18b0d
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
<<<<<<< HEAD

$(() => {
	$("#register").on("click", () => {
		//굿즈, 사용자가 입찰가격 ajax -> bid link
	});
});
=======
// function isValid() {
//
//   	const price = document.getElementById('price');
//
// 	if (!price.value.trim()) {
// 		alert('입력해 주세요.');
// 		price.value = '';
// 		price.focus();
// 		return false;
// 	}
//
// 	return true;
// }
//
// /**
//  * 게시글 등록(생성/수정)
//  */
// function save() {
//
// 	if ( !isValid() ) {
// 		return false;
// 	}
//
// 	const price = document.getElementById('price');
// 	const params = {
// 		biddingPrice: form.title.value,
// 		payment: form.writer.value,
// 		content: form.content.value,
// 		deleteYn: 'N'
// 	};
//
// 	fetch('/api/bidding', {
// 		method: 'POST',
// 		headers: {
// 			'Content-Type': 'application/json',
// 		},
// 		body: JSON.stringify(params)
//
// 	}).then(response => {
// 		if (!response.ok) {
// 			throw new Error('Request failed...');
// 		}
//
// 		alert('저장되었습니다.');
// 		location.href = '/board/list';
//
// 	}).catch(error => {
// 		alert('오류가 발생하였습니다.');
// 	});
// }
>>>>>>> e949af7b3f9a9cbeae7c2cdf9fe64fd1fcb18b0d
