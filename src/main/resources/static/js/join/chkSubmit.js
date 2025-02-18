document.querySelector("form").addEventListener("submit", function (event) {
    var unDuplicated = document.getElementById("unDuplicated");
    var resultMessage = document.getElementById("emailCheckResult");
    var chkPassword = document.getElementById("chkPassword");
    var chkPasswordResultMessage = document.getElementById("passwordCheckResult");

    if (unDuplicated.value === "") {
        resultMessage.innerText = "이메일 인증을 진행해주세요";
        console.log("이메일 인증을 진행해주세요");
        event.preventDefault();
        return;
    }

    if (unDuplicated.value === 'false') {
        resultMessage.innerText = "이메일 중복 체크를 통과해주세요";
        resultMessage.style.color = "red";
        console.log("이메일 중복 체크를 통과해주세요");
        event.preventDefault();
        return;
    }

    if (chkPassword.value === 'false') {
        chkPasswordResultMessage.innerText = "패스워드가 동일하지 않습니다.";
        event.preventDefault();
    }
});

