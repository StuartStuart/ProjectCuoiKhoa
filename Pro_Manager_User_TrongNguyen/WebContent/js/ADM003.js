/**
 * trở lại màn hình ADM002
 */
function backADM002() {
	window.location = "../ListUser.do?type=back";
}

/*
 * show and hide vùng trình độ tiếng Nhật
 */

function changeJapanZone() {
	var statusJapanZone = document.getElementById("adm003japanzone").style;

	if (statusJapanZone.display == 'none') {
		statusJapanZone.display = 'table-row-group';
	} else {
		statusJapanZone.display = 'none';
	}
}
