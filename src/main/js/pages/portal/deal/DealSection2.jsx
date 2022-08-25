import React, { useEffect, useState } from "react";
import Form from "../../../components/Form";
import { putDealSection2 } from "../../../services/dealService";
import { getDropdownValues } from "../../../services/dropdownService";
import { handleFormDataChange } from "../../../utils/FormUtils";
import ViewDetails from "../../../components/ViewDetails";
import ActionButton from "../../../components/button/ActionButton";

const formName = "DEAL_PRODUCT_REQUIREMENTS";

const formFields = [
    {
        label: "Product Type",
        name: "productType",
        type: "dropdown",
        dropdownType: "PRODUCT_TYPE"
    },
    {
        label: "Sub-category Product",
        name: "subCategoryProduct",
        type: "dropdown",
        dropdownType: "SUB_CATEGORY_PRODUCT"
    },
    {
        label: "Unit of Quantity",
        name: "unitOfQuantity",
        type: "dropdown",
        dropdownType: "UNIT_OF_QUANTITY"
    },
    {
        label: "Order Size Factor",
        name: "orderSizeFactor",
        type: "number"
    },
    {
        label: "Type of Work",
        name: "typeOfWork",
        type: "dropdown",
        dropdownType: "TYPE_OF_WORK"
    },
    {
        label: "Road Details",
        name: "roadDetails",
        type: "textArea"
    },
]


const viewFields = [
    {
        label: "Product Type",
        name: "productType"
    },
    {
        label: "Sub-category Product",
        name: "subCategoryProduct"
    },
    {
        label: "Unit of Quantity",
        name: "unitOfQuantity"
    },
    {
        label: "Order Size Factor",
        name: "orderSizeFactor"
    },
    {
        label: "Type of Work",
        name: "typeOfWork"
    },
    {
        label: "Road Details",
        name: "roadDetails"
    },
]


const DealSection2 = ({ dealId, setDealDetails, data, edit }) => {
    const [formData, setFormData] = useState({
        productType: "",
        subCategoryProduct: "",
        unitOfQuantity: "",
        orderSizeFactor: "",
        typeOfWork: "",
        roadDetails: ""
    });

    useEffect(() => {
        if (!!data) {
            setFormData(data);
        }
    }, [data])

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    const [dropdowns, setDropdowns] = useState({
        PRODUCT_TYPE: {
            values: []
        },
        SUB_CATEGORY_PRODUCT: {
            values: []
        },
        UNIT_OF_QUANTITY: {
            values: []
        },
        TYPE_OF_WORK: {
            values: []
        },
    });

    useEffect(() => {
        getDropdownValues(null, formName, null).then(
            response => {
                if (response) {
                    console.log(response.dropdownKeyDetailsMap);
                    setDropdowns(response.dropdownKeyDetailsMap)
                }
            }
        )
    }, [flag])

    const [loading, setLoading] = useState(false);

    const handleSubmit = () => {
        setLoading(true);
        putDealSection2(dealId, formData).then(
            response => {
                console.log(response);
                setLoading(false);
                setDealDetails(prevState => ({
                    ...prevState,
                    productDetails: formData
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
                title="UPDATE Product Details"
                fields={formFields}
                formData={formData}
                setFormData={setFormData}
                dropdowns={dropdowns}
                onSubmit={handleSubmit}
                loading={loading}
                reloadDropdown={reloadDropdown}
            />
            :
            <ViewDetails viewFields={viewFields} data={data} actions={actions} title="product details" />
    );

}

export default DealSection2;