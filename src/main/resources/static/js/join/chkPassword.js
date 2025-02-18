function checkPasswordMatch() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const checkMessage = document.getElementById("passwordCheckResult");
    const passwordIsChecked = document.getElementById("chkPassword");

    if (password === "" || confirmPassword === "") {
        checkMessage.innerText = "";
        return;
    }

    if (password === confirmPassword) {
        checkMessage.innerText = "비밀번호가 일치합니다.";
        checkMessage.style.color = "green";
        passwordIsChecked.value = 'true';
    } else {
        checkMessage.innerText = "비밀번호가 다릅니다.";
        checkMessage.style.color = "red";
        passwordIsChecked.value = 'false';
    }
}
