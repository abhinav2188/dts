import React from "react";
import PropTypes from "prop-types";
import { LeftArrow, RightArrow } from "../../svgs/svgIcons";

const PageButton = (props) => {
    return (
        <div className="flex items-center border rounded-full h-5">
            <button className="w-5" onClick={() => {
                props.setPageNo(prevPageNo => (prevPageNo - 1) < 0 ? prevPageNo : prevPageNo - 1);
            }}>{LeftArrow}</button>
            <p className="px-1">{props.pageNo + 1}</p>
            <button className="w-5" onClick={() => {
                props.setPageNo(prevPageNo => (prevPageNo + 1) >= props.totalPagesCount ? prevPageNo : prevPageNo + 1);
            }}>{RightArrow}</button>
        </div>
    )
}

PageButton.propTypes = {
    pageNo: PropTypes.number,
    setPageNo: PropTypes.func,
    totalPagesCount: PropTypes.number
}

export default PageButton;