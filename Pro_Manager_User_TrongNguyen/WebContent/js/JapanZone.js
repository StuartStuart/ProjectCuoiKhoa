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

/**
 * set value for styleJapanZone
 */
function setStyleJapanZone() {
	var statusJapanZone = document.getElementById("japanzone").style;
	document.getElementById("styleJapanZone").value = "style='display: "
			+ statusJapanZone.display + "'";
}
