import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { getDropdownValues } from "../../../services/dropdownService";
import { postParty } from "../../../services/partyService";


const formFields = [
    {
        name: "partyName",
        label: "Party Name",
        type: "text"
    },
    {
        name: "address",
        label: "Address",
        type: "text"
    },
    {
        name: "authority",
        label: "Authority",
        type: "dropdown",
        dropdownType: "AUTHORITY_TYPE"
    },
    {
        name: "email",
        label: "Email",
        type: "text"
    },
    {
        name: "mobile",
        label: "Mobile",
        type: "text"
    }
]

const initialData = {
    partyName: "",
    address: "",
    authority: "",
    mobile: "",
    email: ""
}

const AddParty = ({ addPartyToView }) => {

    const [formData, setFormData] = useState(initialData);

    const [dropdowns, setDropdowns] = useState({
        AUTHORITY_TYPE: {
            values: []
        }
    });

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    useEffect(() => {
        getDropdownValues(null, "PARTY_DETAILS", null).then(
            response => {
                if (response) {
                    setDropdowns(response.dropdownKeyDetailsMap)
                }
            }
        )
    }, [flag])

    let [loading, setLoading] = useState(false);

    function handleSubmit() {
        setLoading(true);
        postParty(formData).then(response => {
            console.log(response);
            if (!!response) {
                addPartyToView(response.data);
            }
            setLoading(false);
            setFormData(initialData);
        })
    }

    return (
        <div className="py-8">
            <Form
                title="ADD New Party"
                fields={formFields}
                formData={formData}
                setFormData={setFormData}
                dropdowns={dropdowns}
                onSubmit={handleSubmit}
                loading={loading}
                reloadDropdown={reloadDropdown}
            />
        </div>
    );
}

export default AddParty;