import React from "react";
import PropTypes from "prop-types";
import Spinner from "../Spinner";
import { Add, Delete, Edit, Reload } from "../../svgs/svgIcons";


const buttons = {
    add: <div className="border border-green-800 rounded h-6 w-6 text-green-800 hover:border-green-500">{Add}</div>,
    edit: <div className="border border-yellow-800 rounded h-6 w-6 text-yellow-800 hover:border-yellow-500">{Edit}</div>,
    delete: <div className="border border-red-800 rounded h-6 w-6 text-red-800 hover:border-red-500">{Delete}</div>,
    reload: <div className="border border-sky-800 rounded h-6 w-6 text-sky-800 hover:border-sky-500">{Reload}</div>,
}


const ActionButton = (props) => {
    return (
        <button disabled={props.loading} onClick={props.onClick}>
            {
                props.loading ? <Spinner /> :
                    !!props.type ?
                        buttons[props.type] : props.children
            }
        </button>
    );
}

ActionButton.propTypes = {
    loading: PropTypes.bool,
    onClick: PropTypes.func
}

export default ActionButton;