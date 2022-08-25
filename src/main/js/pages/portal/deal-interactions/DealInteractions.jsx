import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import Table from "../../../components/Table";
import { deleteDealInteraction, getAllDealInteractions } from "../../../services/dealInteractionsService";
import AddDealInteraction from "./AddDealInteraction";

const viewFields = [
    {
        label: "Created At",
        name: "createTimestamp",
    },
    {
        label: "Deal Stage",
        name: "dealStage",
    },
    {
        label: "Meeting Date",
        name: "meetingDate",
    },
    {
        label: "Meeting Location",
        name: "meetingLocation",
    },
    {
        label: "Contacts",
        name: "contacts",
    },
    {
        label: "Consultants",
        name: "consultants",
    },
    {
        label: "Handlers",
        name: "handlers",
    },
    {
        label: "Meeting Details",
        name: "meetingDetails",
    }
];

const DealInteractions = ({ dealId, add }) => {

    const [data, setData] = useState({
        totalCount: 0,
        totalPageCount: 0,
        interactions: []
    });
    const [pageNo, setPageNo] = useState(0);

    useEffect(() => {
        getAllDealInteractions(dealId, pageNo).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [pageNo])

    function addInteractionToView(interaction) {
        setData(prevState => ({
            ...prevState,
            interactions: [
                interaction,
                ...prevState.interactions
            ]
        }));
    }


    const [viewAddForm, setViewAddForm] = useState(add);

    const tableActions = <div className="flex">
        <ActionButton type="add" onClick={() => setViewAddForm(true)} />
    </div>

    const DeleteInteractionButton = ({ interactionId }) => {
        const [loading, setLoading] = useState(false);

        function deleteInteraction(interactionId) {
            setLoading(true);
            deleteDealInteraction(interactionId).then(
                response => {
                    if (response) {
                        setData(prevData => ({
                            ...prevData,
                            interactions: prevData.interactions.filter(interaction => interaction.id != interactionId)
                        }))
                    }
                    setLoading(false);
                }
            )
        }

        return (
            <ActionButton type="delete" loading={loading} onClick={() => deleteInteraction(interactionId)} />
        );
    }


    const entryActions = (interaction) => <div className="flex" >
        <DeleteInteractionButton interactionId={interaction.id} />
    </div>

    return (
        <div className="flex flex-col gap-8">
            <Table
                viewFields={viewFields}
                pageNo={pageNo}
                setPageNo={setPageNo}
                totalEntries={data.totalCount}
                totalPages={data.totalPageCount}
                entriesList={data.interactions}
                title="Deal Interactions"
                tableActions={tableActions}
                entryActions={entryActions}
            />
            {viewAddForm && <AddDealInteraction dealId={dealId} addInteractionToView={addInteractionToView} display={viewAddForm} setDisplay={setViewAddForm} />}
        </div>
    );


}

export default DealInteractions;
