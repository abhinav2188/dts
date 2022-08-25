import instance from "../axiosInstance";
// import axios from "axios";

export async function postDeal(data) {
    console.log("postDeal()");
    const path = "/ext/deals";
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

export async function getAllDeals(pageNo, searchParams) {
    console.log("getAllDeals()");
    return instance.get("/ext/deals/all", {
        params: {
            pageNo,
            ...searchParams
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

export async function putDealSection2(dealId, data) {
    console.log("putDealSection2()");
    const path = "/ext/deals/" + dealId + "/s2";
    return instance.patch(path, data).then(
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

export async function putDealSection3(dealId, data) {
    console.log("putDealSection3()");
    const path = "/ext/deals/" + dealId + "/s3";
    return instance.patch(path, data).then(
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

export async function putDealSection4(dealId, data) {
    console.log("putDealSection4()");
    const path = "/ext/deals/" + dealId + "/s4";
    return instance.patch(path, data).then(
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

export async function getDeal(dealId) {
    console.log("getDeal()");
    const path = "/int/deals/" + dealId;
    return instance.get(path).then(
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


export async function addDealOwner(dealId, data) {
    // console.log("addDealOwner(" + userEmail + ")");
    const path = "/int/deals/" + dealId + "/add-auth";
    return instance.patch(path, data).then(
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

export async function removeDealOwner(dealId, data) {
    // console.log("removeDealOwner(" + userEmail+ ")");
    const path = "/int/deals/" + dealId + "/remove-auth";
    return instance.patch(path, data).then(
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
