import React from "react";
import { Link } from "react-router-dom";
import { useAppSelector } from "./app/hooks";
import { selectIsLoggedIn } from "./features/user/userSlice";

export const Navbar = () => {
  const isAuthenticated = useAppSelector(selectIsLoggedIn);

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="/">
          Eshop
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
              <Link className="nav-link" to={`/`}>
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={`/products`}>
                Products
              </Link>
            </li>
          </ul>
        </div>

        <div className="navbar-nav ml-auto">
          {isAuthenticated ? (
            <>
              <Link className="nav-link" to={`/logout`}>
                Logout
              </Link>
              <Link className="nav-link" to={`/checkout`}>
                <i className="bi bi-cart4"></i>
              </Link>
            </>
          ) : (
            <>
              <Link className="nav-link" to={`/login`}>
                Login
              </Link>
              <Link className="nav-link" to={`/register`}>
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};
