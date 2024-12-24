import Header from "./Header";
import { Outlet } from "react-router-dom";
import styles from "../styles/MainLayout.module.css";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import axios from "axios";
import {addBook} from "../redux/store";
import errorDisplay from "../api/errorDisplay";

export default function MainLayout() {
    const dispatch=useDispatch();

    useEffect(() => {
        axios
            .get("/api/bookList", {
                timeout: 5000,
                withCredentials: true,
            })
            .then((response) => {
                response.data.map((t) => dispatch(addBook(t)));
            })
            .catch((error) => {
                errorDisplay(error)
            });
    }, [dispatch]);

    return (<>
        <Header />
        <div className={styles.child}>
            <Outlet />
        </div>
    </>);
}