import React from "react";
import { NavLink, Outlet } from "react-router-dom";
import ExportExcel from "./portal/ExportExcel";

const Portal = (props) => {
    return (
        <div className="flex flex-col w-full">
            <div className="flex gap-2 items-center shadow-sm justify-between px-4 py-1">
                <div className="flex gap-3 items-center">
                    <NavLink to="users" className={({ isActive }) => [" px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Users</NavLink>
                    <NavLink to="dropdowns" className={({ isActive }) => [" px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Dropdowns</NavLink>
                    <NavLink to="party" className={({ isActive }) => [" px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Party</NavLink>
                    <NavLink to="deals" className={({ isActive }) => ["  px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Deals</NavLink>
                    <NavLink to="brochures" className={({ isActive }) => [" px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Brochures</NavLink>
                    <NavLink to="contacts" className={({ isActive }) => [" px-1", isActive ? "border-b-2 border-sky-600 text-sky-600 font-bold" : ""].join(" ")} >Contacts</NavLink>
                </div>
                <div className="flex">
                    <ExportExcel />
                </div>
            </div>
            <div className="flex px-4">
                <Outlet />
            </div>
        </div>
    );
}

export default Portal;