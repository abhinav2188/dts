import React, { useState, useEffect, createContext } from "react";
import { useSessionStorage } from "./sessionStorageHook";
import UserContext from "./UserContext";

const UserProvider = (props) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        console.log("checking user logged in status");
        let userJson = window.sessionStorage.getItem("userDetails");
        if (userJson == null) setUser(null);
        else {
            const foundUser = JSON.parse(userJson);
            setUser(foundUser);
        }
    }, []);

    return (
        <UserContext.Provider value={{ user: user, setUser: setUser }}>{props.children}</UserContext.Provider>
    )
}

export default UserProvider;