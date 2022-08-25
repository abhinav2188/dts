import React, { useState } from "react";

const Sections = ({ sections, section, setSection }) => {

    function isSectionActive(name) {
        return section == name;
    }

    return (
        <div className="w-full flex flex-col">
            <nav className="w-full flex items-center gap-1 pt-4">
                {
                    sections.map((sec, i) =>
                        <button className={`border border-sky-200 rounded-xl px-2 ${isSectionActive(sec.name) ? "bg-sky-200" : ""}`} onClick={() => setSection(sec.name)} key={i}>{sec.name}</button>
                    )
                }
            </nav>
            <div>
                {
                    sections.filter(section => isSectionActive(section.name)).map(section =>
                        section.component)
                }
            </div>
        </div>

    );
}

export default Sections;