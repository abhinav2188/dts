import instance from "../axiosInstance";

export async function addDealConsultant(dealId, data) {
    console.log("addDealConsultant()");
    const path = "/ext/consultants/" + dealId;
    return instance.post(path, data).then(
        response => {
            console.log("response data:", response.data);
            alert(response.data.responseMsg);
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

export async function getAllDealConsultants(dealId, pageNo) {
    console.log("getAllDealConsultants()");
    const path = "/ext/consultants/" + dealId;
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

export async function deleteDealConsultant(consultantId) {
    console.log("deleteDealConsultant()");
    const path = "/ext/consultants/" + consultantId;
    return instance.delete(path)
        .then(
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

