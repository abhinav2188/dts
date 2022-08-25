import React, { useEffect, useState } from "react";
import Select from "react-select";

const SelectInput2 = ({ optionsList, name, label, onChange, multiple, value }) => {

    const [selectedOptions, setSelectedOptions] = useState([]);
    const [options, setOptions] = useState([]);

    useEffect(() => {
        setOptions([...optionsList.map(option => ({
            value: option.value,
            label: option.value
        })), {
            value: "",
            label: "NONE"
        }]);
    }, [optionsList])

    useEffect(() => {
        console.log(value);
        if (!!value) {
            let vals = value.split(',');
            let opvals = vals.map(val => ({
                value: val,
                label: val
            }));
            console.log(vals);
            console.log(opvals);
            // let optionVals = options.filter(op => vals.includes(op.value));
            // console.log(optionVals);
            setSelectedOptions(opvals);
        }
    }, [value])

    function handleSelectChange(selectedItems) {
        console.log(selectedItems);
        setSelectedOptions(selectedItems);
        let selectedValue = "";
        if (multiple) {
            selectedValue = selectedItems.map(item => item.value).join(",");
        }
        else {
            selectedValue = selectedItems.value;
        }
        onChange(name, selectedValue);
    }

    return (
        <label htmlFor={name} className="w-full">
            <span>{label}</span>
            <Select options={options} onChange={handleSelectChange} isMulti={multiple} value={selectedOptions} />
        </label>
    );

}

export default SelectInput2;