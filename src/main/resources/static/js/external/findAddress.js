<!-- Daum 주소 검색 API -->
function findAddr() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("detailAddr").value = '';
            } else {
                document.getElementById("detailAddr").value = '';
            }
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("streetAddr").value = addr;
            document.getElementById("detailAddr").focus();
        }
    }).open();
}