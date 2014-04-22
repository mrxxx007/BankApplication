function showDivByCheckbox(checkBoxObj, divObj) {
	$(checkBoxObj).is(":checked") ?
		$(divObj).show() :
		$(divObj).hide();
}

// returns true if empty
function checkForEmpty(element, errorOutput) {
	if ($(element).val().length == 0) {
		$(errorOutput).html("you have to fill in this field");
		return true;
	}
	return false;
}

function clearErrorFields() {
	$("#emailError").html("");
	$("#nameError").html("");
	$("#cityError").html("");
	$("#genderError").html("");
	$("#phoneError").html("");
	$("#errorCreditInitBalance").html("");
	$("#errorSavingInitBalance").html("");
}

function checkForm() {
	clearErrorFields();

	var checkResult = true;

	if (checkForEmpty($("#name"), nameError)) {
		checkResult = false;
	} else {
		var name = $("#name").val();
		var nameArr = name.split(" ");
		for(var i = 0; i < nameArr.length; i++) {
			var n = nameArr[i];
			if (n.length < 3) {
				checkResult = false;
				$("#nameError").html("the name is incorrect or too short");
				break;
			}
		}
	}

	if (checkForEmpty(city, cityError)) {
		checkResult = false;
	}

	if (!($("#genderM").is(":checked") || $("#genderF").is(":checked"))) {
		$("#genderError").html("you have to select gender");
		checkResult = false;
	}

	if (checkForEmpty($("#email"), emailError)) {
		checkResult = false;
	} else if (!isCorrectEmail($("#email").val())) {
		$("#emailError").html("the format of e-mail is incorrect");
		checkResult = false;
	}

	if (checkForEmpty(phone, phoneError)) {
    	checkResult = false;
    } else if (!isCorrectPhone($("#phone").val())) {
    	$("#phoneError").html("phone number is incorrect");
    	checkResult = false;
    }

    if ($("#creditCheck").is(":checked")) {
    	if (checkForEmpty($("#creditInitBalance"), errorCreditInitBalance)) {
    		checkResult = false;
    	} else if (!isNumeric($("#creditInitBalance").val())) {
    		$("#errorCreditInitBalance").html("not a number");
    		checkResult = false;
    	}
    }

    if ($("#savingCheck").is(":checked")) {
		if (checkForEmpty($("#savingInitBalance"), errorSavingInitBalance)) {
			checkResult = false;
		} else if (!isNumeric($("#savingInitBalance").val())) {
			$("#errorSavingInitBalance").html("not a number");
			checkResult = false;
		}
	}


	return checkResult;
}