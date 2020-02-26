const url = "/logout";
const logout = () => {
    fetch(url, {
        method: 'POST'
    }).then(response => {
        if (response.redirected && response.ok) {
            window.location.href = "/"
        }
    })
};