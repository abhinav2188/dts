import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import PageButton from "../../../components/button/PageButton";
import Form from "../../../components/Form";
import Table from "../../../components/Table";
import { getAllDeals } from "../../../services/dealService";
import { getDropdownValues } from "../../../services/dropdownService";

const formName = "DEAL_SEARCH";

const viewFields = [
    {
        label: "Id",
        name: "dealId"
    },
    {
        label: "Deal Name",
        name: "dealName"
    },
    {
        label: "Party Name",
        name: "partyName"
    },
    {
        label: "Deal Stage",
        name: "dealStage"
    },
    {
        label: "Opening Date",
        name: "openingDate"
    },
    {
        label: "Active",
        name: "isActive"
    },
]

const searchFormFields = [
    {
        name: "dealName",
        label: "Deal Name",
        type: "dropdown",
        dropdownType: "DEAL_NAME"
    },
    {
        name: "partyName",
        label: "Party Name",
        type: "dropdown",
        dropdownType: "PARTY_NAME"
    },
    {
        name: "coOwnerEmail",
        label: "User Email",
        type: "dropdown",
        dropdownType: "USER_EMAIL"
    }
];

const ViewDeals = ({ pageNo, setPageNo, data, setData, setSection, setCurrentDealId }) => {

    const [searchParams, setSearchParams] = useState({
        dealName: "",
        partyName: "",
        coOwnerEmail: ""
    });

    const [flag, setFlag] = useState(true);

    const reloadDropdown = () => setFlag(f => !f);

    const [dropdowns, setDropdowns] = useState({
        USER_EMAIL: {
            values: []
        },
        PARTY_NAME: {
            values: []
        },
        DEAL_NAME: {
            values: []
        }
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

    function onSubmit() {
        setLoading(true);
        getAllDeals(pageNo, searchParams).then(response => {
            if (response) {
                setData(response.data);
            }
            setLoading(false);
        })
    }

    const entryActions = (deal) => {
        return (
            <div className="flex">
                <ActionButton onClick={() => {
                    setCurrentDealId(deal.dealId);
                    setSection("updateDeal");
                }} type="edit" />
            </div>
        );
    }

    return (
        <div className="flex flex-col w-full py-8 gap-8">
            <Form
                className="flex-row"
                title="Search Deal"
                fields={searchFormFields}
                formData={searchParams}
                setFormData={setSearchParams}
                onSubmit={onSubmit}
                loading={loading}
                reloadDropdown={reloadDropdown}
                dropdowns={dropdowns}
            />
            <Table
                viewFields={viewFields}
                pageNo={pageNo}
                setPageNo={setPageNo}
                totalEntries={data.totalCount}
                totalPages={data.totalPages}
                entriesList={data.deals}
                title="Deals"
                entryActions={entryActions}
            />
        </div>
    );
}

export default ViewDeals;