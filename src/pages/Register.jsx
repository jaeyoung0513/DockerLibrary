import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from "../api/axiosInstance";
import errorDisplay from "../api/errorDisplay";
import axios from "axios";

export default function Register() {

    const [loginId, setLoginId] = useState("");
    const [isValidUser, setIsValidUser] = useState(false); // 유효한 사용자 여부

    const validateUserId = async () => {
        try {
            const response = await axios.get(`/api/validate-loginId/${loginId}`, {
                withCredentials: true,
            });
            if (!response.data) {
                setIsValidUser(true);
                setError("");
            } else {
                setError("이미 존재하는 사용자 아이디입니다.");
                setIsValidUser(false);
            }
        } catch (err) {
            errorDisplay(err);
            setError("아이디 검증 중 오류가 발생했습니다.");
            setIsValidUser(false);
        }
    };

    const handleSubmit = async () => {
        try {
            const payload = {
                loginId,
                password: userDetails.password,
                userName: userDetails.userName,
                mobile: userDetails.mobile,
            };
            await axios.post("/api/join", payload, {
                withCredentials: true,
            });
            alert("가입 정보가 성공적으로 추가되었습니다.");
            navigate("/login");
        } catch (err) {
            errorDisplay(err);
            setError("가입 정보를 추가하는 중 오류가 발생했습니다.");
        }
    };


    const [userDetails, setUserDetails] = useState({
        password: "",
        userName: "",
        mobile: "",
    });
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserDetails((prevDetails) => ({
            ...prevDetails,
            [name]: value,
        }));
    };

    return (
        <div>
            <hr/>
            <h2>가입 정보 추가</h2>
            <hr/>
            <label htmlFor="loginId">
                고객 아이디:{" "}
                <input
                    type="text"
                    id="loginId"
                    name="loginId"
                    placeholder="id"
                    value={loginId}
                    onChange={(e) => setLoginId(e.target.value)}
                />{" "}
                <button type="button" onClick={validateUserId}>
                    중복 검사
                </button>
                {isValidUser && (<p style={{color: "grey"}}> 추가 가능한 아이디 입니다.</p>)}
            </label>
            {error && <p style={{color: "red"}}>{error}</p>}
            {isValidUser && (
                <div>
                    <label>
                        비밀번호:{" "}
                        <input
                            type="text"
                            name="password" // 사용자 비밀번호
                            placeholder="Password"
                            value={userDetails.password}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br/>
                    <label>
                        사용자 이름:{" "}
                        <input
                            type="text"
                            name="userName" // 사용자 이름 필드
                            placeholder="사용자 이름"
                            value={userDetails.userName}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br/>
                    <label>
                        전화번호:{" "}
                        <input
                            type="text"
                            name="mobile" // 사용자 주소 필드
                            placeholder="전화번호"
                            value={userDetails.mobile}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br/>
                    <button type="button" onClick={handleSubmit}>
                        입력 완료
                    </button>
                </div>
            )}
        </div>
    )
}