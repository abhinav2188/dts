import React, { useEffect, useState } from "react";
import { getDeal } from "../../../services/dealService";
import DealContacts from "../deal-contacts/DealContacts";
import DealSection2 from "./DealSection2";
import DealSection1 from "./DealSection1";
import DealSection3 from "./DealSection3";
import DealSection4 from "./DealSection4";
import DealOwners from "./DealOwners";
import DealConsultants from "../deal-consultants/DealConsultants";
import DealInteractions from "../deal-interactions/DealInteractions";
import DealQuery from "../dealQuery/DealQuery";
import DealAttachments from "../deal-attachment/DealAttachments";
import ActionButton from "../../../components/button/ActionButton";


const initialData = {
    cardDetails: {
        createTimestamp: "",
        updateTimestamp: "",
        dealId: "",
        dealName: "",
        partyName: "",
        partyId: "",
        dealStage: "",
        openingDate: "",
        isActive: ""
    },
    productDetails: {
        productType: "",
        subCategoryProduct: "",
        unitOfQuantity: "",
        orderSizeFactor: "",
        typeOfWork: "",
        roadDetails: ""
    },
    commonDetails: {
        siteLocation: "",
        cateredByVertical: "",
        paymentType: "",
        openingDate: "",
        expectedCloseDate: "",
        actualCloseDate: "",
        expectedNumberOfDays: "",
        expectedDeliveryAddress: "",
        lastPurchaseDetails: "",
        competitorsInfo: "",
        remarks: ""
    },
    additionalDetails: {
        dealStage: "",
        isActive: "",
        dealValueInCr: "",
        paymentTerms: "",
        paymentFactor: "",
        ownerFocus: "",
        dealProbability: "",
        expectedTurnover: "",
        proximityFromBase: ""
    },
    authorizationDetails: {
        owner: "",
        coOwners: [
        ]
    }
};

const UpdateDeal = ({ dealId }) => {

    const [dealDetails, setDealDetails] = useState(initialData);

    let [flag, setFlag] = useState(true);

    const ReloadDealButton = <ActionButton type="reload" onClick={() => setFlag(f => !f)} />


    useEffect(() => {
        console.log(flag);
        if (!dealId) return;
        getDeal(dealId).then(
            response => {
                if (response) {
                    setDealDetails(response.data);
                }
            }
        );

    }, [dealId, flag])

    return (
        dealId ?
            <div className="flex flex-col gap-8 py-8">
                <DealSection1 setDealDetails={setDealDetails} data={dealDetails.cardDetails} reloadDealButton={ReloadDealButton} />
                <DealQuery dealId={dealId} />
                <DealSection2 dealId={dealId} setDealDetails={setDealDetails} data={dealDetails.productDetails} />
                <DealSection3 dealId={dealId} setDealDetails={setDealDetails} data={dealDetails.commonDetails} />
                <DealSection4 dealId={dealId} setDealDetails={setDealDetails} data={dealDetails.additionalDetails} />
                <DealOwners dealId={dealId} setDealDetails={setDealDetails} data={dealDetails.authorizationDetails} />
                <DealContacts dealId={dealId} />
                <DealConsultants dealId={dealId} />
                <DealInteractions dealId={dealId} add />
                <DealAttachments dealId={dealId} />
            </div> :
            <div className="flex flex-col gap-8 py-8">
                <p>Select a deal from view section</p>
            </div>
    );
}

export default UpdateDeal;