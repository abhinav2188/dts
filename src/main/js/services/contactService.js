import instance from "../axiosInstance";

export async function addDealContact(dealId, data) {
    console.log("addDealContact()");
    const path = "/ext/contacts/" + dealId;
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

export async function getAllDealContacts(dealId, pageNo) {
    console.log("getAllDeals()");
    const path = "/ext/contacts/" + dealId;
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

export async function deleteDealContact(contactId) {
    console.log("deleteDealContact()");
    const path = "/ext/contacts/" + contactId;
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

export async function getAllContacts(pageNo) {
    console.log("getAllConatacts()");
    const path = "/int/contacts";
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

