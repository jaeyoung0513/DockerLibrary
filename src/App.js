import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import Home from "./pages/Home";
import BookList from "./pages/BookList";
import Register from "./pages/Register";
import AdminPage from "./pages/AdminPage";
import Login from "./pages/Login";
import BookDetail from "./components/BookDetail";
import EditBook from "./components/EditBook";
import OAuth2RedirectHandler from "./components/OAuth2RedirectHandler";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Home />} />
          <Route path="bookList" element={<BookList />} />
          <Route path="bookList/:id" element={<BookDetail />} />
          <Route path="edit-book/:id" element={<EditBook />} />
          <Route path="register" element={<Register />} />
          <Route path="admin" element={<AdminPage />} />
          <Route path="login" element={<Login />} />
          <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
