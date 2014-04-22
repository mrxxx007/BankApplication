function checkLoginForm() {
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

function isCorrectEmail(input) {
	return input.match(".+@.+\.[a-zA-Z]{2,3}");
}

function isCorrectPhone(input) {
	return input.match("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
}