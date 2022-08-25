import React, { useState } from "react";
import PropTypes from "prop-types";

const FileInput = (props) => {

    const [fileData, setFileData] = useState({
        file: "",
        imagePreviewUrl: ""
    })

    // function handleChange(e) {
    //     let f = e.target.files[0];
    //     props.onChange(props.name, f);
    // }

    function handleChange(e) {
        let reader = new FileReader();
        let file = e.target.files[0];
        reader.onloadend = () => {
            // setFileData({
            //     file: file,
            //     imagePreviewUrl: reader.result
            // });
            props.onChange(props.name, file);
        }
        reader.readAsDataURL(file);
    }

    return (
        <label htmlFor={props.name} className="flex flex-col w-full">
            <span className="block text-sm font-medium text-slate-700">{props.label}</span>
            <input className="mt-1 block w-full px-2 py-1 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400
            focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500
            disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none
            invalid:border-red-500 invalid:text-red-600
            focus:invalid:border-red-500 focus:invalid:ring-red-500" accept={props.accept}
                type="file" id={props.name} name={props.name} onChange={handleChange} />
        </label>
    );
}

FileInput.propTypes = {
    name: PropTypes.string,
    label: PropTypes.string,
    value: PropTypes.string,
    onChange: PropTypes.func,
    accept: PropTypes.string
};

export default FileInput;