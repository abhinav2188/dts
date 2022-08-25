import instance from "../axiosInstance";

export async function addDealInteraction(dealId, data) {
    console.log("addDealInteraction()");
    const path = "/ext/interactions/" + dealId;
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

export async function getAllDealInteractions(dealId, pageNo) {
    console.log("getAllDeals()");
    const path = "/ext/interactions/" + dealId;
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

export async function deleteDealInteraction(interactionId) {
    console.log("deleteDealInteraction()");
    const path = "/ext/interactions/" + interactionId;
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

