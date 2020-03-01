const logoutUrl = "/logout";
const logout = () => {
    fetch(logoutUrl, {
        method: 'POST'
    }).then(response => {
        if (response.redirected && response.ok) {
            alert("로그아웃 되었습니다")
            window.location.href = "/"
        }
    })
};