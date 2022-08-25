import React, { useEffect, useState } from "react";
import Table from "../../../components/Table";
import { getAllContacts } from "../../../services/contactService";

const viewFields = [
    {
        label: "Contact Id",
        name: "id"
    },
    {
        label: "Full Name",
        name: "name",
    },
    {
        label: "Email-Pri",
        name: "email1",
    },
    {
        label: "Email-Sec",
        name: "email2",
    },
    {
        label: "Mobile-Pri",
        name: "mobile1",
    },
    {
        label: "Mobile-Sec",
        name: "mobile2",
    },
    {
        label: "Designation",
        name: "designation",
    },
    {
        label: "Address",
        name: "address",
    },
    {
        label: "Deal ID",
        name: "dealId"
    },
    {
        label: "Deal Name",
        name: "dealName"
    }
]

const Contacts = () => {

    const [data, setData] = useState({
        totalCount: 0,
        totalPageCount: 0,
        contacts: []
    });

    const [pageNo, setPageNo] = useState(0);

    useEffect(() => {
        getAllContacts(pageNo).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [pageNo])

    return (
        <div className="flex flex-col gap-8">
            <Table
                viewFields={viewFields}
                pageNo={pageNo}
                setPageNo={setPageNo}
                totalEntries={data.totalCount}
                totalPages={data.totalPageCount}
                entriesList={data.contacts}
                title="Contacts"
            />
        </div>
    );

}

export default Contacts;
