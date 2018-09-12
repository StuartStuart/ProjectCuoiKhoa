/*
 * show and hide vùng trình độ tiếng Nhật
 */
function changeJapanZone() {
	var statusJapanZone = document.getElementById("japanzone").style;

	if (statusJapanZone.display == 'none') {
		statusJapanZone.display = 'table-row-group';
	} else {
		statusJapanZone.display = 'none';
	}
}
