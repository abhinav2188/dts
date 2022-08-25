import React from "react";

const ViewDetails = ({ data, viewFields, actions, title }) => {

    return (
        <div className="border rounded-xl flex flex-col overflow-hidden">
            <div className="flex justify-between items-center px-2 py-1">
                <h3 className="capitalize">{title}</h3>
                <div className="flex">{actions}</div>
            </div>
            {
                viewFields.map(viewField =>
                    <div className="flex gap-2 border-t py-1 px-2 items-center">
                        <span className="text-sm uppercase text-sky-600 font-bold">{viewField.label}</span>
                        <span>{String(data[viewField.name])}</span>
                    </div>
                )
            }
        </div>
    );

}

export default ViewDetails;