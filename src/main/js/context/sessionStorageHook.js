import { useEffect, useState } from "react";

function getStorageValue(key, defaultValue) {
    const saved = window.sessionStorage.getItem(key);
    try {
        return JSON.parse(saved);
    }
    catch (error) {
        return defaultValue;
    }
}

export const useSessionStorage = (key, defaultValue) => {
    const [value, setValue] = useState(() => {
        return getStorageValue(key, defaultValue);
    });
    useEffect(() => {
        console.log("setting sessionStorage item " + key);
        window.sessionStorage.setItem(key, JSON.stringify(value));
    }, [key, value]);
    return [value, setValue];
}