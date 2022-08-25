import React, { useEffect, useState } from "react";
import { postDeal } from "../../../services/dealService";
import { getDropdownValues } from "../../../services/dropdownService";
import ViewDetails from "../../../components/ViewDetails";
import Form from "../../../components/Form";
import ActionButton from "../../../components/button/ActionButton";

const formName = "PARTY_DEAL";

const formFields = [
    {
        label: "Party Name",
        name: "partyName",
        type: "dropdown",
        dropdownType: "PARTY"
    },
    {
        label: "Deal Name",
        name: "dealName",
        type: "text"
    }
]

const viewFields = [
    {
        label: "Party Name",
        name: "partyName",
    },
    {
        label: "Deal Name",
        name: "dealName",
    }
]

const initialData = {
    partyName: "",
    dealName: ""
};

const DealSection1 = ({ setDealId, setDealDetails, data, edit, reloadDealButton }) => {

    const [formData, setFormData] = useState(initialData);

    const [dropdowns, setDropdowns] = useState({
        PARTY: {
            values: []
        }
    });

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    useEffect(() => {
        getDropdownValues(null, formName, null).then(
            response => {
                if (response) {
                    console.log(response.dropdownKeyDetailsMap);
                    setDropdowns(response.dropdownKeyDetailsMap)
                }
            }
        )
    }, [flag]);

    let [loading, setLoading] = useState(false);

    let [editMode, setEditMode] = useState(edit);

    function submitAddDealForm() {
        setLoading(true);
        postDeal(formData).then(response => {
            console.log(response);
            if (response) {
                setDealDetails(prevState => ({
                    ...prevState,
                    cardDetails: response.data
                }))
            }
            setLoading(false);
            setDealId(response.data.dealId);
            setEditMode(false);
        })
    }

    const actions = <div className="flex">
        {reloadDealButton}
    </div>

    return (

        editMode ?
            <Form
                title="Deal"
                fields={formFields}
                formData={formData}
                setFormData={setFormData}
                dropdowns={dropdowns}
                onSubmit={submitAddDealForm}
                loading={loading}
                reloadDropdown={reloadDropdown}
            />
            :
            <ViewDetails viewFields={viewFields} title="Deal" data={data} actions={actions} />
    );
}

export default DealSection1;