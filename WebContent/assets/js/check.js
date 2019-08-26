function login_check() {
	// 로그인 페이지할 때 모든 항목이 채워져있는지 확인 후 submit하는 함수
	if(document.login.email.value == "") {
		alert("이메일을 입력해주세요.");
		document.login.email.focus();
		return;
	}
	if(document.login.password.value == "") {
		alert("비밀번호를 입력해주세요.");
		document.login.email.focus();
		return;
	}
	document.login.submit();
}

function register_check() {
	// 반드시 채워져야하는 항목이 채워져있는지 확인 후 submit하는 함수
	var email = document.register.email;
	var nickname = document.register.nickname;
	var password = document.register.password;
	var repassword = document.register.repassword;
	var email_check = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var check_number = password.value.search(/[0-9]/g);
	var check_english = password.value.search(/[a-z]/ig);
	
	if(email.value == "") {
		alert("이메일을 입력해주세요.");
		email.focus();
		return;
	}
	if(nickname.value == "") {
		alert("닉네임을 입력해주세요.");
		nickname.focus();
		return;
	}
	if(password.value == "") {
		alert("비밀번호를 입력해주세요.");
		password.focus();
		return;
	}
	if(repassword.value == "") {
		alert("비밀번호를 다시 한 번 입력해주세요.");
		repassword.focus();
		return;
	}
	if(password.value != repassword.value) {
		alert("비밀번호가 일치하지 않습니다.");
		repassword.value = "";
		repassword.focus();
		return;
	}
	if(email.value.match(email_check) == null) {
		alert("올바른 이메일을 입력해주세요.");
		email.value = "";
		email.focus();
		return;
	}
	if(password.value.length < 4) {
		alert("비밀번호는 4자 이상, 20자 이하여야 합니다.");
		password.value = "";
		repassword.value = "";
		password.focus();
		return;
	}
	if(check_number < 0 || check_english < 0) {
		alert("비밀번호는 영어와 숫자가 반드시 포함되어야합니다.");
		password.value = "";
		repassword.value = "";
		password.focus();
		return;
	}
	document.register.submit();
}