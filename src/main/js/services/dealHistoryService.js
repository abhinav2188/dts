import instance from "../axiosInstance";

export async function getDealHistory(dealId, pageNo) {
    console.log("getAllDeals()");
    const path = "/int/" + dealId + "/history";
    return instance.get(path, {
        params: {
            pageNo
        }
    }).then(
        response => {
            console.log("response data:", response.data);
            return response.data;
        }
    )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            alert(error.response.data.status + ", " + error.response.data.responseMsg);
            return null;
        })
}
