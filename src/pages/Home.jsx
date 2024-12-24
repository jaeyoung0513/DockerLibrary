import { useSelector } from "react-redux";

export default function Home() {
  const loginFlag = useSelector((state) => state.userInfo.loginFlag);
  const userRole = useSelector((state) => state.userInfo.role);

  return (
    <div>
      <br />
      <h2>
        {loginFlag &&
          (userRole === "ROLE_ADMIN"
            ? "관리자 로그인 되었습니다. "
            : "로그인 되었습니다. ")}
        &nbsp;&nbsp; 도서관 홈입니다.
      </h2>
    </div>
  );
}
