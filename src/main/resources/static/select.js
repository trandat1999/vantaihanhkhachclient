let laixe = document.getElementsByName('laixe')[0];
let phuxe = document.getElementsByName('phuxe')[0];
laixe.options[0].selected = "true";
phuxe.options[1].selected = "true";
laixe.addEventListener('change', function(event) {
	let index = laixe.selectedIndex;
	for (let i = 0; i < laixe.options.length; i++) {
		if (i === index) {
			phuxe.options[i].disabled = "true";
		} else {
			phuxe.options[i].disabled = "";
		}
	}
});
phuxe.addEventListener('change', function(event) {
	let index = phuxe.selectedIndex;
	for (let i = 0; i < phuxe.options.length; i++) {
		if (i === index) {
			laixe.options[i].disabled = "true";
		} else {
			laixe.options[i].disabled = "";
		}
	}
});
