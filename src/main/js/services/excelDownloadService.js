import instance from "../axiosInstance";

export async function getDealsExcel() {

    return instance.get("/int/download/db", {
        responseType: 'blob'
    }).then(
        response => {
            return response.data;
        }
    )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response);
            alert(error.response.status + ", " + error.response.data.responseMsg);
            return null;
        })
} 