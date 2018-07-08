function focusInputNumber() {
	$("input.number-only").keydown(function(e){
		var keyCode = e.which;
		// Chi cho phep nhap 0-9, dau "."" va dung backspace + tab de chinh du lieu
		var isValidCharacter = (keyCode >= 48 && keyCode <= 57)
			|| (keyCode >=96 && keyCode <= 105) // ban phim phu
			|| keyCode == 110 
			|| keyCode == 190 // dau "." o ban phim phu
			|| keyCode == 8   // backspace
			|| keyCode == 9 // tab
		var val = $(this).val();
		if (!isValidCharacter) {
			return false;
		} 
		// loai bo truong hop dau "." o dau hoac co nhieu dau "."
		else if ((!val || val.indexOf(".") > -1) && (keyCode == 110 || keyCode == 190)) { 
			return false;
		}

		return true;
	});
}
