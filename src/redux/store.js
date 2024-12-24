import { createSlice, configureStore } from "@reduxjs/toolkit";

// UserInfo 슬라이스
const userInfoSlice = createSlice({
  name: "userInfo",
  initialState: {
    userInfoList: [],
    count: 0,
    loginFlag: false,
    role: "",
    jwtToken: "",
  },
  reducers: {
    addUserInfo: (state, action) => {
      state.userInfoList.push(action.payload);
      state.count++;
    },
    clearUserInfo: (state) => {
      state.userInfoList = [];
      state.count = 0;
    },
    setLoginFlag: (state) => {
      state.loginFlag = true;
    },
    setLogout: (state) => {
      state.loginFlag = false;
      state.role = "";
      state.jwtToken = "";
    },
    saveJwtToken: (state, action) => {
      state.jwtToken = action.payload;
    },
    setRole: (state, action) => {
      state.role = action.payload;
    },
  },
});

// Book 슬라이스
const bookSlice = createSlice({
  name: "bookList",
  initialState: {
    bookList: [],
  },
  reducers: {
    addBook: (state, action) => {
      state.bookList.push(action.payload);
    },
    removeBook: (state, action) => {
      state.bookList = state.bookList.filter(
        (book) => book.bookId !== action.payload
      );
    },
    updateBook: (state, action) => {
      const index = state.bookList.findIndex(
        (book) => book.bookId === action.payload.bookId
      );
      if (index !== -1) {
        state.bookList[index] = action.payload;
      }
    },
    clearBooks: (state) => {
      state.bookList = [];
    },
  },
});

const store = configureStore({
  reducer: {
    userInfo: userInfoSlice.reducer,
    bookList: bookSlice.reducer,
  },
});

// 액션 익스포트
export const {
  addUserInfo,
  clearUserInfo,
  setLoginFlag,
  saveJwtToken,
  setRole,
  setLogout,
} = userInfoSlice.actions;

export const { addBook, removeBook, updateBook, clearBooks } =
  bookSlice.actions;

export default store;
