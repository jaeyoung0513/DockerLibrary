import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { saveJwtToken, setRole, setLoginFlag } from "../redux/store";
import { useNavigate, useLocation } from "react-router-dom";
import apiClient from "../api/axiosInstance";

export default function OAuth2RedirectHandler() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    async function fetchToken() {
      try {
        const params = new URLSearchParams(location.search);
        const accessToken = params.get("accessToken");
        const role = params.get("role");

        if (!accessToken || !role) {
          throw new Error("인증 데이터가 누락되었습니다. 다시 시도하세요.");
        }

        dispatch(saveJwtToken(accessToken));
        dispatch(setRole(role));
        dispatch(setLoginFlag());
        console.log("OAuth2 로그인 성공:", { accessToken, role });
        navigate("/");
      } catch (error) {
        console.error("OAuth2 인증 실패:", error.message);
        alert("로그인에 실패했습니다. 다시 시도해주세요.");
        navigate("/login");
      }
    }

    fetchToken();
  }, [dispatch, navigate, location]);

  return <h1>OAuth2 인증 중...</h1>;
}
