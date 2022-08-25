import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { postDropdownValue } from "../../../services/dropdownService";

const formFields = [
    {
        name: "value",
        label: "Dropdown Value",
        type: "text"
    },
    {
        name: "order",
        label: "Value Order",
        type: "number"
    }
]

const AddDropdownValue = ({ dropdownKey, addToView }) => {

    const [formData, setFormData] = useState({
        key: dropdownKey,
        value: "",
        order: ""
    });

    useEffect(() => {
        setFormData({
            key: dropdownKey,
            value: "",
            order: ""
        });
    }, [dropdownKey])

    const [valueSaveProgress, setValueSaveProgress] = useState(false);

    function handleSubmit() {
        setValueSaveProgress(true);
        postDropdownValue(formData).then(
            response => {
                console.log(response);
                addToView(response);
                setValueSaveProgress(false);
            }
        );
    }

    return (
        !!dropdownKey &&
        <Form
            title="ADD Dropdown Value"
            fields={formFields}
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleSubmit}
            loading={valueSaveProgress}
        />
    );

}

export default AddDropdownValue;