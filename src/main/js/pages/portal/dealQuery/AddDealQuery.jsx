import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { getDropdownValues } from "../../../services/dropdownService";
import { addDealQuery } from "../../../services/queryService";

const formName = "BROCHURES_QUERY";

const initialData = {
    brochures: "",
    recipients: ""
};

const formFields = [
    {
        label: "Recipients",
        name: "recipients",
        type: "dropdown",
        dropdownType: "DEAL_RECIPIENTS",
        multiple: true
    },
    {
        label: "Brochures",
        name: "brochures",
        type: "dropdown",
        dropdownType: "BROCHURES_TYPE",
        multiple: true
    }
];


const AddDealQuery = ({ dealId, setDisplay }) => {

    const [formData, setFormData] = useState(initialData);

    const [dropdowns, setDropdowns] = useState({
        BROCHURES_TYPE: {
            values: []
        },
        DEAL_RECIPIENTS: {
            values: []
        }
    });

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    useEffect(() => {
        getDropdownValues(null, formName, dealId).then(
            response => {
                if (response) {
                    setDropdowns(response.dropdownKeyDetailsMap)
                }
            }
        )
    }, [flag])

    const [loading, setLoading] = useState(false);

    const handleSubmit = () => {
        setLoading(true);
        addDealQuery(dealId, formData).then(
            response => {
                console.log("handlesubmit", response);
                if (response) {
                    // addInteractionToView(response.data);
                }
                setFormData(initialData);
                setDisplay(false);
                setLoading(false);
            }
        )
    }

    return (
        <Form
            title="SEND Deal-Query"
            fields={formFields}
            formData={formData}
            dropdowns={dropdowns}
            setFormData={setFormData}
            onSubmit={handleSubmit}
            loading={loading}
            reloadDropdown={reloadDropdown}
        />
    );

}

export default AddDealQuery;