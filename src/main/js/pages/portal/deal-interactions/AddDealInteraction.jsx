import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { addDealInteraction } from "../../../services/dealInteractionsService";
import { getDropdownValues } from "../../../services/dropdownService";

const formName = "DEAL_INTERACTIONS";

const initialData = {
    meetingDate: "",
    meetingLocation: "",
    meetingDetails: "",
    contacts: "",
    consultants: "",
    handlers: ""
};

const formFields = [
    {
        label: "Meeting Date",
        name: "meetingDate",
        type: "date"
    },
    {
        label: "Meeting Location",
        name: "meetingLocation",
        type: "text"
    },
    {
        label: "Contacts",
        name: "contacts",
        type: "dropdown",
        dropdownType: "MEETING_CONTACT",
        multiple: true
    },
    {
        label: "Consultants",
        name: "consultants",
        type: "dropdown",
        dropdownType: "MEETING_CONSULTANT",
        multiple: true
    },
    {
        label: "Handlers",
        name: "handlers",
        type: "dropdown",
        dropdownType: "MEETING_HANDLER",
        multiple: true
    },
    {
        label: "Meeting Details",
        name: "meetingDetails",
        type: "textArea"
    }
];


const AddDealInteraction = ({ dealId, addInteractionToView, setDisplay, reload }) => {

    const [formData, setFormData] = useState(initialData);

    const [dropdowns, setDropdowns] = useState({
        MEETING_CONTACT: {
            values: []
        },
        MEETING_CONSULTANT: {
            values: []
        },
        MEETING_HANDLER: {
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
        addDealInteraction(dealId, formData).then(
            response => {
                console.log("handlesubmit", response);
                if (response) {
                    addInteractionToView(response.data);
                }
                setFormData(initialData);
                setDisplay(false);
                setLoading(false);
            }
        )
    }

    return (
        <Form
            title="ADD Deal-Interaction"
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

export default AddDealInteraction;