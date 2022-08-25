import instance from "../axiosInstance";

async function loginUser(email, password) {

    console.log("login(" + email + ')');
    const path = "/auth/login";

    return instance.post(path, {
        email,
        password,
    })
        .then(response => {
            console.log(response.data);
            if (response.data.data != null) {
                return response.data.data;
            }
            return false;
        })
        .catch(errorResp => {
            console.log(errorResp.response.data);
            alert(errorResp.response.data.responseMsg);
            return false;
        })
}

async function registerUser(email, password, mobile) {
    console.log("register(" + email + ')');
    const path = "/auth/register";
    return instance.post(path, {
        email,
        password,
        mobile
    })
        .then(response => {
            console.log(response.data);
            if (response.data.data != null) {
                alert(response.data.responseMsg);
            }
            return true;
        })
        .catch(errorResp => {
            console.log(errorResp.response.data);
            alert(errorResp.response.data.responseMsg);
            return false;
        })
}

export { loginUser, registerUser };