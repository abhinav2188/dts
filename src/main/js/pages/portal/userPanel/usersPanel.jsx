import React, { useEffect, useState } from "react";
import ActionButton from "../../../components/button/ActionButton";
import Table from "../../../components/Table";
import { getAllUsers } from "../../../services/userService";
import UpdateUserAuth from "./UpdateUserAuth";

const viewFields = [
    {
        label: "User Id",
        name: "id"
    },
    {
        label: "Mobile",
        name: "mobile",
    },
    {
        label: "Email",
        name: "email",
    },
    {
        label: "Roles",
        name: "roles",
    },
    {
        label: "Active",
        name: "isActive",
    }
]

const UsersPanel = (props) => {

    let [usersData, setUsersData] = useState({
        usersList: [],
        totalUsers: 0,
        totalPages: 0
    });

    let [pageNo, setPageNo] = useState(0);

    useEffect(() => {
        getAllUsers(pageNo).then(response => {
            if (!!response.data) {
                console.log(response.data);
                setUsersData(response.data);
            }
        })
    }, [pageNo]);

    let [currentUser, setCurrentUser] = useState(null);

    const entryActions = (user) => <div className="flex">
        <ActionButton type="edit" onClick={() => {
            setCurrentUser(user);
        }} />
    </div>

    function updateView(user) {
        setUsersData(prevData => ({
            ...prevData,
            usersList: [
                user,
                ...prevData.usersList.filter(u => u.id != user.id)
            ]
        }))
    }

    return (
        <div className="w-full flex flex-col gap-8 py-8">
            <Table viewFields={viewFields}
                entriesList={usersData.usersList}
                totalPages={usersData.totalPages}
                totalEntries={usersData.totalUsers}
                pageNo={pageNo}
                setPageNo={setPageNo}
                title="Users"
                entryActions={entryActions}
            />
            <UpdateUserAuth userData={currentUser} updateView={updateView} />
        </div>
    );
}

export default UsersPanel;

