import instance from "../axiosInstance";

export async function addDealAttachment(dealId, formData) {
    console.log("addDealAttachment()");
    const path = "/ext/" + dealId + "/docs";
    return instance.post(path, formData).then(
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


export async function getAllDealAttachments(dealId) {
    console.log("getDealAttachments()");
    const path = "/ext/" + dealId + "/docs";
    return instance.get(path).then(
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

export async function deleteDealAttachment(dealId, docId) {
    console.log("deleteDealAttachment(" + docId + ")");
    const path = "/ext/" + dealId + "/docs";
    return instance.delete(path, {
        params: {
            docId
        }
    }).then(
        response => {
            console.log("response data:", response.data);
            alert(response.data.responseMsg);
            return response.data;
        }
    )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            alert(error.status + ", " + error.response.data.responseMsg);
            return null;
        })
}
