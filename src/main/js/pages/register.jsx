import React from "react";
import TextInput from "../components/input/TextInput";
import TextPassword from "../components/input/TextPassword";
import SubmitButton from "../components/button/SubmitButton";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../services/authService";

const Register = (props) => {

    let [formData, setFormData] = useState({
        email: "",
        password: "",
        confirmPassword: "",
        mobile: ""
    });

    let [loading, setLoading] = useState(false);

    let navigate = useNavigate();

    function handleChange(event) {
        let name = event.target.name;
        let value = event.target.value;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function submitRegisterForm(event) {
        setLoading(true);
        event.preventDefault();
        registerUser(formData.email, formData.password, formData.mobile)
            .then((isSuccess) => {
                console.log(isSuccess);
                setLoading(false);
                if (isSuccess) {
                    alert("login again with the credentials");
                    setFormData({});
                    navigate("/login", { replace: true });
                }
            });
    }
    return (
        <div className="flex flex-col p-4 border-sm w-96 border shadow-md rounded-lg my-16 self-center">
            <h3 className="border-b">Register User</h3>
            <form className="flex flex-col items-center w-full mt-8 mb-2 gap-2">
                <TextInput name="email" label="Email" value={formData.email} onChange={handleChange} />
                <TextInput name="mobile" label="Mobile" value={formData.mobile} onChange={handleChange} />
                <TextPassword name="password" label="Password" value={formData.password} onChange={handleChange} />
                <SubmitButton onClick={submitRegisterForm} loading={loading} className="mt-4" />
            </form>
        </div>
    );
}

// <TextPassword name="confirmPassword" label="Confirm Password" value={formData.confirmPassword} onChange={handleChange} /> 

export default Register;