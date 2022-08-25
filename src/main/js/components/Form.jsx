import React from "react";
import { handleFormDataChange } from "../utils/FormUtils";
import ActionButton from "./button/ActionButton";
import SubmitButton from "./button/SubmitButton";
import CustomInput from "./CustomInput";

const Form = ({ className, fields, setFormData, onSubmit, formData, loading, dropdowns, multipart, title, reloadDropdown }) => {

    const handleChange = (event) => {
        handleFormDataChange(event, setFormData);
    }

    const handleChange2 = (name, value) => {
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }))
    }

    return (
        <div className="flex flex-col w-full border rounded-xl p-4">
            <div className="flex items-center gap-2 mb-3 border-b pb-1">
                <h3>{title}</h3>
                <ActionButton type="reload" onClick={reloadDropdown} />
            </div>
            <form encType={multipart && "multipart/form-data"} className={`flex flex-col gap-2 ${className}`}>
                {
                    fields.map(field =>
                        <CustomInput field={field} value={formData[field.name]} handleChange={handleChange} handleChange2={handleChange2} dropdowns={dropdowns} />
                    )
                }
                <SubmitButton className="mt-4 self-center" onClick={(event) => {
                    event.preventDefault();
                    onSubmit();
                }} loading={loading} />
            </form>
        </div>
    );

}

export default Form;