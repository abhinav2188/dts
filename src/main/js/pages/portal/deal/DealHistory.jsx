import React, { useEffect, useState } from "react";
import Table from "../../../components/Table";
import { getDealHistory } from "../../../services/dealHistoryService";

const viewFields = [
    {
        label: "Id",
        name: "id"
    },
    {
        label: "Created At",
        name: "createdAt"
    },
    {
        label: "Created By",
        name: "userEmail"
    },
    {
        label: "Created By id",
        name: "userId"
    },
    {
        label: "Deal Id",
        name: "dealId"
    },
    {
        label: "Title",
        name: "title"
    },
    {
        label: "Details",
        name: "details"
    },
]

const DealHistory = ({ dealId, add }) => {

    const [data, setData] = useState({
        totalCount: 0,
        totalPageCount: 0,
        historyList: []
    });

    const [pageNo, setPageNo] = useState(0);

    useEffect(() => {
        getDealHistory(dealId, pageNo).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [pageNo])

    return (
        <div className="flex flex-col gap-8 py-8">
            {
                dealId ?
                    <Table
                        viewFields={viewFields}
                        pageNo={pageNo}
                        setPageNo={setPageNo}
                        totalEntries={data.totalCount}
                        totalPages={data.totalPageCount}
                        entriesList={data.historyList}
                        title="Deal History"
                    /> :
                    <p>Select a deal from view section</p>
            }
        </div>
    );
}

export default DealHistory;
