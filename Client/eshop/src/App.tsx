import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Product } from "./features/product/Product";
import { ProductList } from "./features/product/ProductList";
import { Home } from "./Home";

import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Navbar } from "./Navbar";
import { Login } from "./features/user/Login";
import { Register } from "./features/user/Register";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Navbar />
        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/products" element={<ProductList />} />
            <Route path="/products/:id" element={<Product />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
