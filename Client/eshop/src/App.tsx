import React, { useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Product } from "./features/product/Product";
import { ProductList } from "./features/product/ProductList";
import { Home } from "./Home";
import { Navbar } from "./Navbar";
import { Login } from "./features/user/Login";
import { Register } from "./features/user/Register";
import { Checkout } from "./features/checkout/Checkout";
import { ProductAdd } from "./features/product/ProductAdd";
import { Logout } from "./features/user/Logout";
import { Orders } from "./features/checkout/Orders";

import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { ProductEdit } from "./features/product/ProductEdit";
import { useAppSelector } from "./app/hooks";
import { selectProductLoading } from "./features/product/productSlice";
import { selectCheckoutStatus } from "./features/checkout/checkoutSlice";
import { selectUserStatus } from "./features/user/userSlice";

function App() {
  const checkoutLoading = useAppSelector(selectCheckoutStatus) === "loading";
  const productsLoading = useAppSelector(selectProductLoading);
  const userLoading = useAppSelector(selectUserStatus) === "loading";

  let loading = checkoutLoading || productsLoading || userLoading;

  // useEffect(() => {
  //   loading = checkoutLoading || productsLoading || userLoading;
  // }, [checkoutLoading, productsLoading, userLoading])

  // if (loading) return <div className="loader"></div>;

  return (
    <div className="App">
      <BrowserRouter>
        <Navbar />
        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/products" element={<ProductList />} />
            <Route path="/products/:id" element={<Product />} />
            <Route path="/products/add" element={<ProductAdd />} />
            <Route path="/products/edit/:id" element={<ProductEdit />} />
            <Route path="/login" element={<Login />} />
            <Route path="/logout" element={<Logout />} />
            <Route path="/register" element={<Register />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/orders" element={<Orders />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
