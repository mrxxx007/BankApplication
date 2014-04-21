function checkForm() {
	var name=$("#name").val();
	if (name.length<2) {
		$("#nameError").html("Please, enter the name");
		return false;
	}
	return true;
}

function isNumeric(input){
    var RE = /^-{0,1}\d*\.{0,1}\d+$/;
    return (RE.test(input));
}