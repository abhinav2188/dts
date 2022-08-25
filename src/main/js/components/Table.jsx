import React from "react";
import PageButton from "./button/PageButton";

const Table = ({ viewFields, entriesList, totalEntries, totalPages, pageNo, setPageNo, entryActions, title, tableActions }) => {
    return (
        <div className="flex flex-col border overflow-auto rounded-xl">
            <div className="flex justify-between items-center px-2 py-1">
                <div className="flex items-center gap-2">
                    <h3>{title}</h3>
                    {!!totalEntries && <p className="rounded-xl px-1 text-sm font-bold bg-sky-800 text-white    ">{totalEntries}</p>}
                    {!!totalPages &&
                        <PageButton pageNo={pageNo} setPageNo={setPageNo} totalPagesCount={totalPages} />
                    }
                </div>
                {tableActions}
            </div>
            {
                entriesList.length > 0 &&
                <table className="rounded-xl border-box table">
                    <thead>
                        <tr className="bg-gray-500 text-white font-bold">
                            {
                                viewFields.map((viewField) =>
                                    <td className="py-2 px-2" key={viewField.label}>{viewField.label}</td>
                                )
                            }
                            <td className="p-2">Actions</td>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            entriesList.map(
                                (entry, i) =>
                                    <tr key={i} className="border-t">
                                        {
                                            viewFields.map(viewField =>
                                                <td className="px-2 ">{String(entry[viewField.name])}</td>)
                                        }
                                        <td className="px-2">
                                            {entryActions &&
                                                entryActions(entry)}
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            }
        </div>
    );
}

export default Table;