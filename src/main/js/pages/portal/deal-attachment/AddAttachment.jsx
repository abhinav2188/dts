import React, { useState } from "react";
import Form from "../../../components/Form";
import { addDealAttachment } from "../../../services/attachmentService";

const formFields = [
    {
        label: "File",
        name: "file",
        type: "file",
        accept: "image/*"
    }
]

const initialData = {
    file: ""
};

const AddAttachment = ({ dealId, addAttachmentToView, setDisplay }) => {

    const [formData, setFormData] = useState(initialData);

    const [loading, setLoading] = useState(false);

    const handleSubmit = (event) => {
        // event.preventDefault();
        const formDataa = new FormData();
        formDataa.append("file", formData.file);
        setLoading(true);
        addDealAttachment(dealId, formDataa).then(
            response => {
                console.log("handlesubmit", response);
                if (response) {
                    addAttachmentToView(response.data);
                }
                setFormData(initialData);
                setDisplay(false);
                setLoading(false);
            }
        )
    }

    return (

        <Form
            title="ADD Deal Attachment"
            fields={formFields}
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleSubmit}
            loading={loading}
            multipart
        />

    );

}

export default AddAttachment;
