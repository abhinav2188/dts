import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { putDealSection4 } from "../../../services/dealService";
import { getDropdownValues } from "../../../services/dropdownService";
import { handleFormDataChange } from "../../../utils/FormUtils";
import ViewDetails from "../../../components/ViewDetails";
import ActionButton from "../../../components/button/ActionButton";

const formFields = [
    {
        label: "Deal Stage",
        name: "dealStage",
        type: "dropdown",
        dropdownType: "CURRENT_DEAL_STAGE"
    },
    {
        label: "Active",
        name: "isActive",
        type: "boolean",
    },
    {
        label: "Deal Value in Cr",
        name: "dealValueInCr",
        type: "number",
    },
    {
        label: "Payment Terms",
        name: "paymentTerms",
        type: "dropdown",
        dropdownType: "PAYMENT_TERMS"
    },
    {
        label: "Payment Factor",
        name: "paymentFactor",
        type: "number"
    },
    {
        label: "Owner Focus",
        name: "ownerFocus",
        type: "number"
    },
    {
        label: "Deal Probability",
        name: "dealProbability",
        type: "number"
    },
    {
        label: "Expected Turnover",
        name: "expectedTurnover",
        type: "number"
    },
    {
        label: "Proximity From Base",
        name: "proximityFromBase",
        type: "text"
    },
]

const viewFields = [
    {
        label: "Deal Stage",
        name: "dealStage",
    },
    {
        label: "Active",
        name: "isActive",
    },
    {
        label: "Deal Value in Cr",
        name: "dealValueInCr",
    },
    {
        label: "Payment Terms",
        name: "paymentTerms",
    },
    {
        label: "Payment Factor",
        name: "paymentFactor",
    },
    {
        label: "Owner Focus",
        name: "ownerFocus",
    },
    {
        label: "Deal Probability",
        name: "dealProbability",
    },
    {
        label: "Expected Turnover",
        name: "expectedTurnover",
    },
    {
        label: "Proximity From Base",
        name: "proximityFromBase",
    },
]

const formName = "DEAL_ADDITIONAL";

const DealSection4 = ({ dealId, setDealDetails, data, edit }) => {

    const [formData, setFormData] = useState({
        dealStage: "",
        isActive: "",
        dealValueInCr: "",
        paymentTerms: "",
        paymentFactor: "",
        ownerFocus: "",
        dealProbability: "",
        expectedTurnover: "",
        proximityFromBase: ""
    });

    useEffect(() => {
        if (!!data) {
            setFormData(data);
        }
    }, [data])

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    const [dropdowns, setDropdowns] = useState({
        CURRENT_DEAL_STAGE: {
            values: []
        },
        PAYMENT_TERMS: {
            values: []
        }
    });

    useEffect(() => {
        getDropdownValues(null, formName, null).then(
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
        putDealSection4(dealId, formData).then(
            response => {
                console.log(response);
                setLoading(false);
                setDealDetails(prevState => ({
                    ...prevState,
                    additionalDetails: formData
                }));
                setEditMode(false);
            }
        )
    }

    let [editMode, setEditMode] = useState(edit);

    const actions = <div className="flex">
        <ActionButton type="edit" onClick={() => setEditMode(true)} />
    </div>

    return (
        editMode ?
            <Form
                title="UPDATE Additional Details"
                fields={formFields}
                formData={formData}
                dropdowns={dropdowns}
                setFormData={setFormData}
                onSubmit={handleSubmit}
                loading={loading}
                reloadDropdown={reloadDropdown}
            />
            :
            <ViewDetails viewFields={viewFields} data={data} actions={actions} title="additional details" />
    );

}

export default DealSection4;