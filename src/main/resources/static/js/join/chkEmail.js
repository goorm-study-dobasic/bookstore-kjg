function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

document.getElementById("editEmailBtn").addEventListener("click", function () {
    // 누르면
    // unDuplicated -> false
    // readonly 취소
    var email = document.getElementById("email");
    var unDuplicated = document.getElementById("unDuplicated");

    unDuplicated.value = 'false';
    email.readOnly = false;

})


// 이메일 중복 체크
document.getElementById("checkEmailBtn").addEventListener("click", function () {
    var email = document.getElementById("email");
    var resultMessage = document.getElementById("emailCheckResult");
    var unDuplicated = document.getElementById("unDuplicated");

    var emailContainer = document.getElementById("emailContainer"); // 특정 div 선택
    var editEmailBtn = emailContainer.querySelector("#editEmailBtn"); // 해당 div 내 버튼 찾기

    if (!isValidEmail(email.value)) {
        console.log("isValid 호출")
        resultMessage.style.color = "red";
        resultMessage.innerText = "이메일 형식으로 입력해주세요."
        unDuplicated.value = 'false';
        return;
    }

    fetch(`/join/duplicate/email?email=${email.value}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "error") {
                resultMessage.style.color = "red";
                resultMessage.innerText = data.message;
                unDuplicated.value = 'false';
                editEmailBtn.style.display = "none";

            } else {
                resultMessage.style.color = "green";
                resultMessage.innerText = data.message;
                unDuplicated.value = 'true';
                email.readOnly = true;
                editEmailBtn.style.display = "inline-block"
            }
        })
        .catch(error => {
            resultMessage.style.color = "red";
            resultMessage.innerText = "서버 오류 발생.";
            console.error("Error", error);
        });
});



document.getElementById("email").addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // 🚀 폼 제출 방지
        console.log("엔터 키 입력 감지 - 폼 제출 방지됨");
    }
});
