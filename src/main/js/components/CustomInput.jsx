import React from "react";
import BoolSelectInput from "./input/BoolSelectInput";
import DateInput from "./input/DateInput";
import FileInput from "./input/FileInput";
import NumberInput from "./input/NumberInput";
import SelectInput from "./input/SelectInput";
import SelectInput2 from "./input/SelectInput2";
import TextAreaInput from "./input/TextAreaInput";
import TextInput from "./input/TextInput";
import TextPassword from "./input/TextPassword";

const CustomInput = ({ field, value, handleChange, handleChange2, dropdowns }) => {
    switch (field.type) {
        case "text":
            return (
                <TextInput label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "password":
            return (
                <TextPassword label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "date":
            return (
                <DateInput label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "dropdown2":
            return (
                <SelectInput label={field.label} name={field.name} onChange={handleChange}
                    optionsList={dropdowns[field.dropdownType].values}
                    value={value} />
            );
        case "dropdown":
            return (
                <SelectInput2 label={field.label} name={field.name} onChange={handleChange2}
                    optionsList={dropdowns[field.dropdownType].values}
                    value={value} multiple={field.multiple} />
            );
        case "boolean":
            return (
                <BoolSelectInput label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "textArea":
            return (
                <TextAreaInput label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "number":
            return (
                <NumberInput label={field.label} name={field.name} onChange={handleChange} value={value} />
            );
        case "file":
            return (
                <FileInput label={field.label} name={field.name} onChange={handleChange2} accept={field.accept} />
            );
    }
}

export default CustomInput;