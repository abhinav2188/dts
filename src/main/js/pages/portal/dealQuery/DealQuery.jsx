import React, { useState } from "react";
import SubmitButton from "../../../components/button/SubmitButton";
import AddDealQuery from "./AddDealQuery";

const DealQuery = ({ dealId }) => {

    const [viewAddForm, setViewAddForm] = useState(false);

    return (
        viewAddForm ?
            <AddDealQuery setDisplay={setViewAddForm} dealId={dealId} /> :
            <SubmitButton type="add" onClick={() => setViewAddForm(true)} >Send Query</SubmitButton>
    );

}

export default DealQuery;