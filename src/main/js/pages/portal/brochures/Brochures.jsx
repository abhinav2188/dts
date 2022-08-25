
import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import Table from "../../../components/Table";
import { getAllBrochures } from "../../../services/brochureService";
import AddBrochure from "./AddBrochure";

const viewFields = [
    {
        label: "Brochure Name",
        name: "brochureName"
    },
    {
        label: "File URL",
        name: "filePath",
    },
    {
        label: "Active",
        name: "isActive"
    },
]

const Brochures = () => {

    const [data, setData] = useState({
        totalCount: 0,
        totalPageCount: 0,
        brochures: []
    });

    const [pageNo, setPageNo] = useState(0);

    const [flag, setFlag] = useState(true);

    useEffect(() => {
        getAllBrochures(pageNo).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [pageNo, flag])

    function addBrochureToView(brochure) {
        setData(prevState => ({
            ...prevState,
            brochures: [
                brochure,
                ...prevState.brochures
            ]
        }));
    }

    const [viewAddForm, setViewAddForm] = useState(false);

    const tableActions = <div className="flex justify-center items-center gap-1">
        <ActionButton type="add" onClick={() => setViewAddForm(true)} />
        <ActionButton type="reload" onClick={() => setFlag(f => !f)} />
    </div>


    return (
        <div className="flex flex-col w-full gap-8 mt-8">
            <Table
                viewFields={viewFields}
                pageNo={pageNo}
                setPageNo={setPageNo}
                totalEntries={data.totalCount}
                totalPages={data.totalPageCount}
                entriesList={data.brochures}
                title="Brochures"
                tableActions={tableActions}
            />
            {viewAddForm && <AddBrochure addBrochureToView={addBrochureToView} display={viewAddForm} setDisplay={setViewAddForm} />}
        </div>
    );


}

export default Brochures;
