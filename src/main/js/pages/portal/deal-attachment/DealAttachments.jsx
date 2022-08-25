
import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import { getAllDealAttachments } from "../../../services/attachmentService";
import AddAttachment from "./AddAttachment";
import DeleteAttachment from "./DeleteAttachment";

const DealAttachments = ({ dealId }) => {

    const [data, setData] = useState({
        totalCount: 0,
        attachments: []
    });

    useEffect(() => {
        getAllDealAttachments(dealId).then(response => {
            if (response) {
                setData(response.data);
            }
        })
    }, [dealId])

    function addAttachmentToView(attachment) {
        setData(prevState => ({
            ...prevState,
            attachments: [
                attachment,
                ...prevState.attachments
            ]
        }));
    }

    function removeAttachmentFromView(attachmentId) {
        setData(prevState => ({
            ...prevState,
            attachments: prevState.attachments.filter(att => att.id != attachmentId)
        }));
    }

    const [viewAddForm, setViewAddForm] = useState(false);

    return (
        <div className="flex flex-col gap-8 border rounded-xl px-2">
            <div className="flex flex-col">
                <div className="flex justify-between border-b items-center py-1">
                    <h3>Deal Attachments</h3>
                    <ActionButton type="add" onClick={() => setViewAddForm(true)} />
                </div>
                <div className="flex flex-wrap gap-2 py-4">
                    {
                        data.attachments.map(attachment =>
                            <div className="border rounded-lg flex items-center">
                                <span className="px-2">
                                    <a className="text-sm text-sky-600" href={attachment.path} target="_blank">{attachment.documentName}</a>
                                </span>
                                <DeleteAttachment attachment={attachment} dealId={dealId}
                                    removeAttachmentFromView={removeAttachmentFromView} />
                            </div>
                        )
                    }
                </div>
            </div>
            {
                viewAddForm &&
                <AddAttachment dealId={dealId} addAttachmentToView={addAttachmentToView}
                    setDisplay={setViewAddForm} />
            }
        </div>
    );

}

export default DealAttachments;
