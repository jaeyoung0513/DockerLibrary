import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useState, useEffect } from "react";
import styles from "../styles/EditBook.module.css";
import { updateBook } from "../redux/store";
import apiClient from "../api/axiosInstance";
import errorDisplay from "../api/errorDisplay";

export default function EditBook() {
    const { id } = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const selectedBook = useSelector((state) =>
        state.bookList?.bookList.find((t) => t.bookId === Number(id))
    ) || {};

    const userRole = useSelector((state) => state.userInfo.role);

    const [state, setState] = useState({ title: "", description: "", image: "" });

    useEffect(() => {
        if (selectedBook) {
            setState({
                bookId: selectedBook.bookId || "",
                title: selectedBook.title || "",
                description: selectedBook.description || "",
                image: selectedBook.image || "",
            });
        }
    }, [selectedBook]);

    if (userRole !== "ROLE_ADMIN") {
        return (
            <div>
                <h2>관리자만 이용할 수 있습니다.</h2>
            </div>
        );
    }

    const handleSubmit = async (e) => {

        e.preventDefault();
        try {
            const response = await apiClient.put("http://localhost:8080/editBook", "state", {
                timeout: 5000,
            });
            dispatch(updateBook(response.data));
        } catch (error) {
            errorDisplay(error);
        }
        navigate(`/bookList/${id}`);
    };

    return (
        <div className={styles.container}>
            <h2>Edit Book</h2>
            {selectedBook.imgsrc && <img src={state.imgsrc} alt={state.title} />}
            <form onSubmit={handleSubmit}>
               제목 <input
                    type="text"
                    name="title"
                    placeholder="Title"
                    value={state.title}
                    onChange={(e) =>
                        setState((prevState) => ({...prevState, title: e.target.value}))
                    }
                />
                책 설명 <input
                    type="text"
                    name="description"
                    placeholder="Description"
                    value={state.description}
                    onChange={(e) =>
                        setState((prevState) => ({...prevState, description: e.target.value}))
                    }
                />
                이미지 주소 <input
                    type="text"
                    name="image"
                    placeholder="이미지 주소 변경"
                    value={state.image}
                    onChange={(e) =>
                        setState((prevState) => ({...prevState, image: e.target.value}))
                    }
                />
                <button type="submit">저장</button>
            </form>
        </div>
    );
}