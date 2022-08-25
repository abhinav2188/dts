import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import Table from "../../../components/Table";
import { deleteDealConsultant, getAllDealConsultants } from "../../../services/consultantService";
import AddDealConsultant from "./AddDealConsultant";

const viewFields = [
    {
        label: "Consultant Id",
        name: "id"
    },
    {
        label: "Full Name",
        name: "name",
    },
    {
        label: "Email",
        name: "email",
    },
    {
        label: "Mobile",
        name: "mobile",
    },
    {
        label: "Designation",
        name: "designation",
    }
]

const DealConsultants = ({ dealId, add }) => {

    const [data, setData] = useState({
        totalCount: 0,
        totalPageCount: 0,
        consultants: []
    });
    const [pageNo, setPageNo] = useState(0);

    useEffect(() => {
        getAllDealConsultants(dealId, pageNo).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [pageNo])

    function addConsultantToView(consultant) {
        setData(prevState => ({
            ...prevState,
            consultants: [
                consultant,
                ...prevState.consultants
            ]
        }));
    }


    const [viewAddForm, setViewAddForm] = useState(add);

    const tableActions = <div className="flex">
        <ActionButton type="add" onClick={() => setViewAddForm(true)} />
    </div>

    const DeleteConsultantButton = ({ consultantId }) => {
        const [loading, setLoading] = useState(false);

        function deleteConsultant(consultantId) {
            setLoading(true);
            deleteDealConsultant(consultantId).then(
                response => {
                    if (response) {
                        setData(prevData => ({
                            ...prevData,
                            consultants: prevData.consultants.filter(consultant => consultant.id != consultantId)
                        }))
                    }
                    setLoading(false);
                }
            )
        }

        return (
            <ActionButton type="delete" loading={loading} onClick={() => deleteConsultant(consultantId)}>Delete</ActionButton>
        );
    }


    const entryActions = (consultant) =>
        <div className="flex">
            <DeleteConsultantButton consultantId={consultant.id} />
        </div>

    return (
        <div className="flex flex-col gap-8">
            <Table
                viewFields={viewFields}
                pageNo={pageNo}
                setPageNo={setPageNo}
                totalEntries={data.totalCount}
                totalPages={data.totalPageCount}
                entriesList={data.consultants}
                title="Deal Consultants"
                tableActions={tableActions}
                entryActions={entryActions}
            />
            {viewAddForm && <AddDealConsultant dealId={dealId} addConsultantToView={addConsultantToView} display={viewAddForm} setDisplay={setViewAddForm} />}
        </div>
    );


}

export default DealConsultants;
