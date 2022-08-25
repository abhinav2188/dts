import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import Form from "../../../components/Form";
import { updateUserAuth } from "../../../services/userService";

const formFields = [
    {
        label: "Roles",
        name: "roles",
        type: "dropdown",
        dropdownType: "USER_ROLES"
    },
    {
        label: "Active",
        name: "isActive",
        type: "boolean"
    }
]

const UpdateUserAuth = (props) => {

    const [formData, setFormData] = useState(props.userData);

    useEffect(() => {
        setFormData(props.userData)
    }, [props.userData])


    const [loading, setLoading] = useState(false);

    const dropdowns = {
        USER_ROLES: {
            values: [
                { value: "ADMIN" },
                { value: "APP_MODERATOR" },
                { value: "BACKEND_MODERATOR" }
            ]
        }
    };

    function handleSubmit() {
        setLoading(true);
        updateUserAuth(props.userData.id, formData).then((response) => {
            console.log(response);
            if (!!response) {
                if (response.status == "SUCCESS")
                    props.updateView(formData);
            }
            setLoading(false);
        })
    }


    return (
        formData ?
            <Form
                title="UPDATE User-Authorization"
                fields={formFields}
                formData={formData}
                setFormData={setFormData}
                dropdowns={dropdowns}
                onSubmit={handleSubmit}
                loading={loading}
            />
            :
            <p>Click on Update to update a user authorization</p>
    );
}
export default UpdateUserAuth;