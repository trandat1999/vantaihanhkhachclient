let max = document.getElementsByName('xekhach')[0].options[0].dataset.max;
document.getElementsByName('soLuongKhach')[0].max = max;
document.getElementById('maxKhach').textContent = `(Max : ${max})`;
document.getElementsByName('xekhach')[0]
	.addEventListener(
		'change',
		function() {
			let i = document.getElementsByName('xekhach')[0].selectedIndex;
			let max = document.getElementsByName('xekhach')[0].options[i].dataset.max;
			document.getElementsByName('soLuongKhach')[0].max = max;
			document.getElementById('maxKhach').textContent = `(Max : ${max})`;
		})
