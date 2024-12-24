import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import styles from "../styles/BookList.module.css";

export default function BookList() {
  const bookList = useSelector((state) => state.bookList?.bookList || []);
  const [selectedCategory, setSelectedCategory] = useState("All");
  const [searchTerm, setSearchTerm] = useState("");

  const categories = ["All", ...new Set(bookList.map((book) => book.category))];

  const filteredBooks = bookList.filter((book) => {
    const matchesCategory =
      selectedCategory === "All" || book.category === selectedCategory;

    const matchesSearchTerm =
      book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.publisher?.toLowerCase().includes(searchTerm.toLowerCase()) || // Optional chaining for `publisher`
      book.category.toLowerCase().includes(searchTerm.toLowerCase());

    return matchesCategory && matchesSearchTerm;
  });

  return (
    <div>
      <div className={styles.searchBar}></div>
      <nav className={styles.categoryNav}>
        {categories.map((category) => (
          <button
            key={category}
            onClick={() => setSelectedCategory(category)}
            className={
              selectedCategory === category ? styles.active : styles.notActive
            }
          >
            {category}
          </button>
        ))}
        <input
          type="text"
          placeholder="검색"
          className={styles.searchBar}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </nav>

      <div className={styles.bookList}>
        {filteredBooks.map((book, index) => (
          <div key={`${book.bookId}-${index}`} className={styles.book}>
            <Link to={`/bookList/${book.bookId}`}>
              <img src={book.image} alt={book.title} height={200} width={150} />
            </Link>
            <h2>{book.title}</h2>
            <p>{book.author}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
