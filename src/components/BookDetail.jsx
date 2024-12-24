import { useParams, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import styles from "../styles/BookDetail.module.css";
import { Link } from "react-router-dom";
import errorDisplay from "../api/errorDisplay";
import { removeBook } from "../redux/store";
import apiClient from "../api/axiosInstance";

export default function BookDetail() {
  const { id } = useParams();
  const selectedBook = useSelector((state) =>
    state.bookList?.bookList.find((t) => (t.bookId === Number(id) ? t : null))
  );

  const userRole = useSelector((state) => state.userInfo.role);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const jwtToken = useSelector((state) => state.userInfo.jwtToken);

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (!confirmDelete) return;

    try {
      const response = await apiClient.delete(
        `http://localhost:8080/deleteBook/${id}`
      );
      if (response.status !== 204) {
        throw new Error("삭제하는 과정에서 오류가 발생하였습니다.");
      }
      dispatch(removeBook(id));
      alert("책이 성공적으로 삭제되었습니다.");
      navigate("/");
    } catch (error) {
      errorDisplay(error);
    }
  };

  const handleRental = async (bookId) => {
    try {
      console.log("Sending rental request with token:", jwtToken); // 디버깅 로그 추가
  
      const response = await apiClient.post(
        `http://localhost:8080/rentBook`,
        { bookId },
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`, // 토큰 추가
          },
        }
      );
  
      if (response.status !== 200) {
        throw new Error("대여하는 과정에서 오류가 발생하였습니다.");
      }
      alert("책이 성공적으로 대여되었습니다.");
      navigate("/bookList");
    } catch (error) {
      console.error("Rental error:", error); // 에러 로그 추가
      errorDisplay(error);
    }
  };
  

  if (!selectedBook) {
    return <p>책 정보를 찾을 수 없습니다.</p>;
  }

  return (
    <div className={styles.container}>
      <img src={selectedBook.image} alt={selectedBook.title} />
      <p className={styles.title}>{selectedBook.title}</p>
      <p className={styles.description}>{selectedBook.description}</p>
      <br />
      <p className={styles.bookInfo}>
        작가: {selectedBook.author} &nbsp;&nbsp; 출판사:{" "}
        {selectedBook.publisher} &nbsp;&nbsp; 대여 가능한 수량:{" "}
        {selectedBook.availableCount}
      </p>
      <br />
      {userRole === "ROLE_ADMIN" && (
        <div className={styles.buttons}>
          <Link
            to={`/edit-book/${selectedBook.bookId}`}
            className={styles.editButton}
          >
            🖋️
          </Link>
          &nbsp;&nbsp;&nbsp;
          <button
            className={styles.deleteButton}
            onClick={() => handleDelete(selectedBook.bookId)}
          >
            🗑️
          </button>
        </div>
      )}
      {userRole === "ROLE_USER" && (
        <button
          className={styles.rentalButton}
          onClick={() => handleRental(selectedBook.bookId)}
        >
          책 대여하기
        </button>
      )}
    </div>
  );
}
