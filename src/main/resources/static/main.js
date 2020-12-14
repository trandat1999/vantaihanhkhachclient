let zoomInBtn = document.getElementsByClassName('zoomInBtn')[0];
let isMin = false;
let titleDiv = document.getElementsByClassName('title')[0];
let menu = document.getElementsByClassName('menu')[0];
function zoomIn() {
	isMin ? titleDiv.classList.remove('min') : titleDiv.classList.add('min');
	menu.style.width = isMin ? "15%" : "8%";
	zoomInBtn.style.transform = isMin ? "rotate(0deg)" : "rotate(180deg)";
	isMin = !isMin;
}