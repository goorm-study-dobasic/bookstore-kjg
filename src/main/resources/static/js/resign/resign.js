document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("resigned").addEventListener("click", function () {

        var email = document.getElementById("email");
        var header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        var token = document.querySelector("meta[name='_csrf']").getAttribute("content");

        let confirmed = confirm("정말 회원탈퇴를 하시겠습니까?");
        if (confirmed) {

            // 탈퇴 요청을 서버로 보냄
            fetch("/users/delete", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    [header] : token
                },
                body: JSON.stringify({ "email" : email.value})
            })
                .then(response => {
                    if (response.ok) {
                        alert("회원탈퇴가 완료되었습니다.");
                        fetch("/logout", {
                            method: "POST",
                            headers :{
                            [header] : token
                        }
                        })
                            .then(response => {
                                if (response.ok) {
                                    window.location.href = "/";
                                }
                            })
                            .catch(error => console.error("로그아웃 중 에러 발생", error));
                    } else {
                        alert("회원탈퇴 처리 중 오류가 발생했습니다.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("회원탈퇴 처리 중 오류가 발생했습니다.ㅋㅋ");
                });
        }
    });
});
