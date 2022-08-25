import React, { useContext, useEffect } from "react";
import { Outlet, Link, Navigate, useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import UserContext from "../context/UserContext";
import { validateToken } from "../services/tokenService";

const Home = () => {
    const userContext = useContext(UserContext);
    const navigate = useNavigate();
    useEffect(() => {
        if (userContext.user) {
            navigate("/portal", { replace: true })
            // validateToken().then(
            //     response => {
            //         console.log("home token status:", response);
            //         if (response) {
            //             navigate("/portal", { replace: true })
            //         }
            //         else {
            //             navigate("/login", { replace: true });
            //         }
            //     }
            // )
        }
        else {
            navigate("/login", { replace: true });
        }
    }, [userContext.user]);

    return (
        <div className="w-screen h-screen flex flex-col">
            <Navbar />
            <Outlet />
            <footer className="flex items-center justify-center mt-16">
                copyright ART
            </footer>
        </div>
    );

}

export default Home;

            // {
            //     userContext.user ?
            //         <Navigate to="portal" replace={true} /> :
            //         <Navigate to="login" replace={true} />
            // }
