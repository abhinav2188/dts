import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { addBrochure } from "../../../services/brochureService";

const formFields = [
    {
        label: "File",
        name: "file",
        type: "file"
    },
    {
        label: "Brochure Name",
        name: "brochureName",
        type: "text"
    }
]

const initialData = {
    file: "",
    brochureName: "",
};

const AddBrochure = ({ addBrochureToView, setDisplay }) => {

    const [formData, setFormData] = useState(initialData);

    const [loading, setLoading] = useState(false);

    const handleSubmit = (event) => {
        // event.preventDefault();
        const formDataa = new FormData();
        formDataa.append("file", formData.file);
        formDataa.append("brochureName", formData.brochureName);
        setLoading(true);
        addBrochure(formDataa).then(
            response => {
                console.log("handlesubmit", response);
                if (response) {
                    addBrochureToView(response.data);
                }
                setFormData(initialData);
                setDisplay(false);
                setLoading(false);
            }
        )
    }

    return (

        <Form
            title="ADD Brochure"
            fields={formFields}
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleSubmit}
            loading={loading}
            multipart
        />

    );

}

export default AddBrochure;
