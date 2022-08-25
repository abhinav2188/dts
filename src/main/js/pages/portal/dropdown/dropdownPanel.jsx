import React, { useEffect, useState } from "react";
import SelectInput2 from "../../../components/input/SelectInput2";
import Table from "../../../components/Table";
import { getDropdownKeys, getDropdownValues } from "../../../services/dropdownService";
import AddDropdownValue from "./AddDropdownValue";
import DeleteDropdownValue from "./DeleteDropdownValue";

const viewFields = [
    {
        name: "id",
        label: "Id"
    },
    {
        name: "value",
        label: "Dropdown Value"
    },
    {
        name: "valueOrder",
        label: "Order"
    }
]

const DropdownsPanel = (props) => {

    const [dropdownKeys, setDropdownKeys] = useState([]);

    useEffect(() => {
        // get all dropdown keys at render
        getDropdownKeys().then(
            response => {
                if (!!response.keys) {
                    const options = response.keys.map(k => ({
                        value: k.dropdownKey,
                    }))
                    setDropdownKeys(options);
                }
            }
        )
    }, [])

    const [dropdownData, setDropdownData] = useState({
        key: "",
        values: []
    });

    useEffect(() => {
        // get dropdown values when dropdown key changes
        if (dropdownData.key)
            getDropdownValues(dropdownData.key, null, null)
                .then(response => {
                    const dropdownValues = response.dropdownKeyDetailsMap[dropdownData.key].values;
                    setDropdownData(prevState => ({
                        ...prevState,
                        values: dropdownValues
                    }));
                })
    }, [dropdownData.key])

    function addToView(dropdownValue) {
        setDropdownData(prevState => ({
            ...prevState,
            values: [
                ...prevState.values,
                dropdownValue
            ]
        }));
    }

    function removeFromView(dropdownId) {
        setDropdownData(prevState => ({
            ...prevState,
            values: prevState.values.filter(value => value.id != dropdownId)
        }))

    }

    const handleChange = (name, value) => {
        console.log("handleChange(" + name + "," + value + ")");
        setDropdownData(prevState => ({
            ...prevState,
            [name]: value
        }))
    }

    const entryActions = (dropdownValue) => <div className="flex">
        <DeleteDropdownValue dropdownId={dropdownValue.id} removeFromView={removeFromView} />
    </div>

    return (
        <div className="flex flex-col w-full gap-8 py-8">
            <SelectInput2 name="key" value={dropdownData.key} onChange={handleChange} optionsList={dropdownKeys} label="Dropdown" />
            <Table
                viewFields={viewFields}
                entriesList={dropdownData.values}
                title="Dropdown Values"
                entryActions={entryActions}
            />
            <AddDropdownValue addToView={addToView} dropdownKey={dropdownData.key} />
        </div>
    );
}


// const 

// const DropdownValue = (props) => {
//     const [valueDeleteProgress, setValueDeleteProgress] = useState(false);
//     function deleteValue() {
//         setValueDeleteProgress(true);
//         deleteDropdownValue(props.id).then(
//             response => {
//                 console.log(response);
//                 setValueDeleteProgress(false);
//                 props.setDropdownData(prevState => ({
//                     ...prevState,
//                     values: prevState.values.filter(value => value.id != props.id)
//                 }))
//             }
//         )
//     }
//     return (
//         <tr key={props.id}>
//             <td>{props.value}</td>
//             <td>{props.valueOrder}</td>
//             <td>
//                 <ActionButton loading={valueDeleteProgress} onClick={deleteValue}>Delete</ActionButton>
//             </td>
//         </tr>

//     )
// }
// <select name="key" value={dropdownData.key} onChange={handleChange}>
//     <option disabled selected value=""> -- select an option -- </option>
//     {
//         dropdownKeys.map((d, i) =>
//             <option key={i} value={d.dropdownKey}>{d.dropdownKey + " --form: " + d.formType}</option>
//         )
//     }
// </select>

export default DropdownsPanel;