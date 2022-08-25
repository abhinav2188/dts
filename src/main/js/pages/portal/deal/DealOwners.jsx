import React, { useState } from "react";
import { addDealOwner, removeDealOwner } from "../../../services/dealService";
import ActionButton from "../../../components/button/ActionButton";
import Form from "../../../components/Form";

const DealOwners = ({ dealId, setDealDetails, data }) => {

    const [formData, setFormData] = useState({
        email: ""
    });

    const formFields = [
        {
            name: "email",
            label: "User Email",
            type: "text"
        }
    ]

    let [addProgress, setAddProgress] = useState(false);

    function addOwner() {
        setAddProgress(true);
        addDealOwner(dealId, formData).then(response => {
            console.log(response);
            if (response) {
                setDealDetails(prevState => ({
                    ...prevState,
                    authorizationDetails: {
                        ...prevState.authorizationDetails,
                        coOwners: [
                            ...prevState.authorizationDetails.coOwners,
                            response.data
                        ]
                    }
                }))
            }
            setAddProgress(false);
        })
    }

    const RemoveOwnerButton = ({ owner }) => {
        let [removeProgress, setRemoveProgress] = useState(false);
        function removeOwner() {
            setRemoveProgress(true);
            removeDealOwner(dealId, owner).then(response => {
                console.log(response);
                if (response) {
                    setDealDetails(prevState => ({
                        ...prevState,
                        authorizationDetails: {
                            ...prevState.authorizationDetails,
                            coOwners: prevState.authorizationDetails.coOwners.filter(co => co.id != response.data.id)
                        }
                    }))
                }
                setRemoveProgress(false);
            })
        }
        return (
            <ActionButton loading={removeProgress} onClick={removeOwner} type="delete" />
        );
    }

    const [viewAdd, setViewAdd] = useState(false);


    return (
        <div className="flex flex-col gap-8 border rounded-xl px-2">
            <div className="flex flex-col">
                <div className="flex justify-between border-b items-center py-1">
                    <h3>Deal Owners</h3>
                    <ActionButton onClick={() => setViewAdd(prevState => !prevState)} type="add">Add</ActionButton>
                </div>
                <div className="flex flex-wrap gap-2 py-4">
                    {
                        data.coOwners.map(owner =>
                            <div className="border rounded-lg flex items-center">
                                <span className="px-2">{owner.email}</span>
                                <RemoveOwnerButton owner={owner} />
                            </div>
                        )
                    }
                </div>
            </div>
            {
                viewAdd &&
                <Form
                    title="UPDATE Deal Owners"
                    fields={formFields}
                    formData={formData}
                    setFormData={setFormData}
                    onSubmit={addOwner}
                    loading={addProgress} />
            }
        </div>

    );
}

export default DealOwners;