import instance from "../axiosInstance";

export async function validateToken() {

    console.log("validateToken()");
    const path = "/token";

    instance.get(path).then(
        response => {
            console.log(response);
            if (response.data) {
                console.log(response.data);
                return response.data;

            }
            else return null;
        }
    ).catch(error => {
        console.error(error);
        return false;
    })

}
