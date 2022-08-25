import axios from "axios";

function getJWTtoken() {
    const savedUser = JSON.parse(window.sessionStorage.getItem("userDetails"));
    return savedUser.token;
}

let instance = axios.create({
    baseURL: "http://localhost:8080/api/"
})

instance.defaults.headers.common['Authorization'] = getJWTtoken();

instance.defaults.headers.common['Access-Control-Allow-Origin'] = "*";

instance.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded';

// 2 mins timeout
instance.defaults.timeout = 100 * 60 * 2;

instance.interceptors.request.use(
    (req) => {
        console.log("requesting: ", req.url);
        console.log("params: ", req.params);
    },
    error => {
        console.log("error requesting: ", error);
        Promise.reject(error);
    }
);

instance.interceptors.response.use((response) => {
    console.log("response url: ");
    console.log("response: ", response);
    return response;
}, (error) => {
    console.log(error);
    console.log("interceptor error: ", error.code);
    // const status = error.response.status;
    // if (status == 403)
    //     Navigate("/forbidden", { replace: true });
    return Promise.reject(error);
});

export default instance;
