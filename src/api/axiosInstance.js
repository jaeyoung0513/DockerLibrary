import axios, { responseEncoding } from "axios";
import store, { saveJwtToken } from "../redux/store";

// Axios 인스턴스를 생성하여 API 클라이언트를 정의합니다.
const apiClient = axios.create({
  baseURL: "http://www.jaeyoung.shop", // 요청의 기본 URL을 설정합니다.
  headers: {
    "Content-Type": "application/json", // 기본 콘텐츠 타입을 JSON으로 설정합니다.
  },
});

// 요청 인터셉터를 설정합니다.
apiClient.interceptors.request.use(
  (config) => {
    // 요청 데이터가 URLSearchParams일 경우 콘텐츠 타입을 변경합니다.
    if (config.data instanceof URLSearchParams) {
      config.headers["Content-Type"] = "application/x-www-form-urlencoded";
    }
    // 스토어에서 JWT 토큰을 가져와 요청 헤더에 추가합니다.
    const jwtToken = store.getState().userInfo.jwtToken;
    config.headers["authorization"] = jwtToken;
    return config;
  },
  (error) => {
    // 요청 오류가 있는 경우 거부합니다.
    return Promise.reject(error);
  }
);

// 응답 인터셉터를 설정합니다.
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const reissueResponse = await axios.post(
          "http://www.jaeyoung.shop/api/reissue", // 명확한 URL 설정
          {},
          { withCredentials: true } // 쿠키 포함
        );
        const newToken = reissueResponse.headers["authorization"];

        // Redux 상태에 새로운 토큰 저장
        store.dispatch(saveJwtToken(newToken));

        // 요청 헤더에 새로운 토큰 추가 후 재요청
        originalRequest.headers["Authorization"] = newToken;
        return apiClient(originalRequest);
      } catch (reissueError) {
        return Promise.reject(reissueError);
      }
    }

    return Promise.reject(error);
  }
);

// apiClient 인스턴스를 기본으로 내보냅니다.
export default apiClient;
