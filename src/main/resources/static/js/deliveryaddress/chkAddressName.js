document.getElementById("editAddressNameBtn").addEventListener("click", function () {
    // 누르면
    // unDuplicated -> false
    // readonly 취소
    var addressName = document.getElementById("addressName");
    var unDuplicated = document.getElementById("unDuplicated");

    unDuplicated.value = 'false';
    addressName.readOnly = false;
})


// 배송지 이름 중복 체크
document.getElementById("checkAddressNameBtn").addEventListener("click", function () {
    var addressName = document.getElementById("addressName");
    var resultMessage = document.getElementById("addressNameCheckResult");
    var unDuplicated = document.getElementById("unDuplicated");

    var container = document.getElementById("addAddressContainer"); // 특정 div 선택
    var editAddressNameBtn = container.querySelector("#editAddressNameBtn"); // 해당 div 내 버튼 찾기

    if (addressName.value === "") {
        console.log("중복 체크 버튼");
        resultMessage.style.color = "red";
        resultMessage.innerText = "배송지 이름을 입력해주세요."
        unDuplicated.value = 'false';
        return;
    }

    fetch(`/users/deliveryaddressinfo/checkDuplicateName?addressName=${addressName.value}`, {
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
                editAddressNameBtn.style.display = "none";
            } else {
                resultMessage.style.color = "green";
                resultMessage.innerText = data.message;
                unDuplicated.value = 'true';
                addressName.readOnly = true;
                editAddressNameBtn.style.display = "inline-block"
            }
        })
        .catch(error => {
            resultMessage.style.color = "red";
            resultMessage.innerText = "서버 오류 발생.";
            console.error("Error", error);
        });
});


// 엔터로 폼 제출 방지.
document.getElementById("addressName").addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // 🚀 폼 제출 방지
        console.log("엔터 키 입력 감지 - 폼 제출 방지됨");
    }
});


