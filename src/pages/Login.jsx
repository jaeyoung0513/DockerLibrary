import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { setLoginFlag, saveJwtToken, setRole } from "../redux/store";
import style from "../styles/Login.module.css";
import apiClient from "../api/axiosInstance";
import errorDisplay from "../api/errorDisplay";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleOAuthLogin = (provider) => {
    window.location.href = `http://www.jaeyoung.shop/oauth2/authorization/${provider}`;
  };

  // 사용법

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await apiClient.post(
        "/api/login",
        new URLSearchParams({ username, password }),
        { withCredentials: true }
      );

      const token = response.headers["authorization"];
      const role = response.data;
      await dispatch(setLoginFlag());
      console.log("로그인이 성공했습니다. loginFlag가 설정되었습니다.");
      await dispatch(saveJwtToken(token));
      await dispatch(setRole(role));
      console.log("회원 등급", role);
      console.log("jwt토큰:", token);
      navigate("/");
    } catch (error) {
      errorDisplay(error);
      console.error(error);
    }
  };
  const handleJoin = async (e) => {
    e.preventDefault();
    navigate("/api/register");
  };
  const naverImgsrc =
    "https://i.namu.wiki/i/p_1IEyQ8rYenO9YgAFp_LHIAW46kn6DXT0VKmZ_jKNijvYth9DieYZuJX_E_H_4GkCER_sVKhMqSyQYoW94JKA.svg";

  return (
    <div className={style.container}>
      <form>
        <input
          type="text"
          placeholder="아이디"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <div className={style.logoImg}>
          &nbsp;&nbsp;
          <img
            src={naverImgsrc}
            alt="네이버로고"
            onClick={() => handleOAuthLogin("naver")}
          />
        </div>
        <button type="button" name="login" onClick={handleLogin}>
          로그인
        </button>
        <button type="button" name="join" onClick={handleJoin}>
          회원가입
        </button>
      </form>
    </div>
  );
}
