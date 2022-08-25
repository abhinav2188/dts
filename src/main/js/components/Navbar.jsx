import React, { useContext, useEffect } from "react";
import logo from "../logo.png";
import { NavLink, useNavigate } from "react-router-dom";
import UserContext from "../context/UserContext";
import { Logout, User } from "../svgs/svgIcons";

const Navbar = () => {
    const userContext = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        console.log("logged change");
    }, [userContext.user])

    const logoutUser = () => {
        userContext.setUser(null);
        window.sessionStorage.clear();
        navigate("/login", { replace: true });
    }

    return (
        <nav className="shadow-sm w-full flex items-center gap-4 justify-between px-4">
            <NavLink to="/">
                <img src={logo} alt="logo" className="h-16" />
            </NavLink>
            {
                userContext.user === null ?
                    <div className="flex items-center gap-4">
                        <NavLink to="/login" className={({ isActive }) => ["px-2 py-1 rounded border border-sky-500", isActive ? "bg-sky-500 text-white" : ""].join(" ")}>Login</NavLink>
                        <NavLink to="/register" className={({ isActive }) => ["px-2 py-1 rounded border border-sky-500", isActive ? "bg-sky-500 text-white" : ""].join(" ")}>Register</NavLink>
                    </div> :
                    <div className="flex items-center gap-4">
                        <button className="flex items-center gap-1 border rounded-full px-1">
                            <span className="w-5 h-5">{User}</span>
                            <span>{userContext.user.email}</span>
                        </button>
                        <button onClick={logoutUser} className="w-4 h-4">{Logout}</button>
                    </div>
            }
        </nav>
    );
}

export default Navbar;