import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setLogout } from "../redux/store";
import axios from "axios";
import styles from "../styles/Header.module.css";

export default function Header() {
  const loginFlag = useSelector((state) => state.userInfo.loginFlag);

  const userRole = useSelector((state) => state.userInfo.role);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogout = async (e) => {
    try {
      const response = await axios.post(
        "/api/logout",
        {},
        { withCredentials: true }
      );
      console.log(response.data);
      await dispatch(setLogout());
      localStorage.removeItem("jwtToken"); // JWT를 로컬 스토리지에서 삭제
      navigate("/");
    } catch (error) {
      console.log(error);
      console.log("로그아웃 오류");
    }
  };
  return (
    <header className={styles.header}>
      <h1 className={styles.title}>Jaeyoung's Library</h1>
      <nav className={styles.nav}>
        <Link to="/">홈</Link>&nbsp;
        <Link to="/bookList">도서찾기</Link>&nbsp;
        {userRole === "ROLE_ADMIN" && <Link to="/admin">관리자</Link>}&nbsp;
        {loginFlag && (
          <button onClick={handleLogout} className={styles.logout}>
            로그아웃
          </button>
        )}
        {!loginFlag && <Link to="/login">로그인</Link>}&nbsp;
        {!loginFlag && <Link to="/register">회원가입</Link>}
      </nav>
    </header>
  );
}
