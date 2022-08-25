import axios from "axios";
import { useEffect } from "react";
import { Navigate } from "react-router-dom";

let instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL + "/api/"
});

instance.interceptors.request.use(
    (req) => {
        if (window.sessionStorage.getItem("userDetails")) {
            const savedUser = JSON.parse(window.sessionStorage.getItem("userDetails"));
            req.headers.Authorization = "Bearer " + savedUser.token;
            console.log("added auth");
        }
        req.headers['Access-Control-Allow-Origin'] = '*';
        return req;
    },
    error => {
        Promise.reject(error);
    }
);

// instance.interceptors.response.use((response) => {
//     console.log("interceptor: " + response);
//     return response;
// }, (error) => {
//     console.log(error);
//     console.log("interceptor error: " + error.code);
//     // const status = error.response.status;
//     // if (status == 403)
//     //     Navigate("/forbidden", { replace: true });
//     return Promise.reject(error);
// });


const AxiosInterceptor = (props) => {



    useEffect(() => {

        // const reqInterceptor = instance.interceptors.request.use(
        //     (req) => {
        //         if (window.sessionStorage.getItem("userDetails")) {
        //             const savedUser = JSON.parse(window.sessionStorage.getItem("userDetails"));
        //             req.headers.Authorization = "Bearer " + savedUser.token;
        //             console.log("added auth");
        //         }
        //         req.headers['Access-Control-Allow-Origin'] = '*';
        //         return req;
        //     },
        //     error => {
        //         Promise.reject(error);
        //     }
        // );

        const responseInterceptor = instance.interceptors.response.use((response) => {
            console.log("interceptor: " + response);
            return response;
        }, (error) => {
            console.log(error);
            console.log("interceptor error: " + error.code);
            // const status = error.response.status;
            // if (status == 403)
            //     Navigate("/forbidden", { replace: true });
            return Promise.reject(error);
        })
        return () => {
            instance.interceptors.response.eject(responseInterceptor);
            // instance.interceptors.response.eject(reqInterceptor);
        }
    }, [])

    return props.children;

}

export default instance;
export { AxiosInterceptor };