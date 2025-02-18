document.querySelector("form").addEventListener("submit", function (event) {
    var unDuplicated = document.getElementById("unDuplicated");
    var resultMessage = document.getElementById("addressNameCheckResult");
    var addressName = document.getElementById("addressName");

    if (unDuplicated.value === 'false') {
        console.log("폼 체크 버튼");
        resultMessage.innerText = "배송지 이름 중복 체크를 통과해주세요";
        resultMessage.style.color = "red";
        event.preventDefault();
    }

    if (addressName.value === '') {
        console.log("폼 체크 버튼");
        resultMessage.innerText = "배송지명을 입력해주세요";
        resultMessage.style.color = "red";
        event.preventDefault();
    }
});

