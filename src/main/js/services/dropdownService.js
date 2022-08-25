import instance from "../axiosInstance";

const API_URL = "http://localhost:8080/api"

export async function getDropdownKeys() {
    console.log("getDropdownKeys()");
    return instance.get("/int/dropdown/keys")
        .then(
            response => {
                console.log("response data:", response.data);
                return response.data.data;
            }
        )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            alert(error.response.data.status + ", " + error.response.data.responseMsg);
            return null;
        })
}

export async function getDropdownValues(dropdownType, formType, dealId) {
    console.log("getDropdownValues()");
    return instance.get("/ext/dropdown", {
        params: {
            dropdownType, formType, dealId
        }
    })
        .then(
            response => {
                console.log("response data:", response.data);
                return response.data.data;
            }
        )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            alert(error.response.data.status + ", " + error.response.data.responseMsg);
            return null;
        })
}

export async function postDropdownValue(data) {
    console.log("getDropdownValues()");
    return instance.post("/int/dropdown", data)
        .then(
            response => {
                console.log("response data:", response.data);
                return response.data.data;
            }
        )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            alert(error.response.data.status + ", " + error.response.data.responseMsg);
            return null;
        })
}

export async function deleteDropdownValue(valueId) {
    console.log("getDropdownValues()");
    return instance.delete("/int/dropdown/value", {
        params: {
            valueId
        }
    })
        .then(
            response => {
                console.log("response data:", response.data);
                return response.data.data;
            }
        )
        .catch(error => {
            console.log("error:", error);
            console.log(error.response.data);
            // alert(error.response.data.status + ", " + error.response.data.responseMsg);
            return null;
        })
}
