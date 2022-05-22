import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import { addToCart } from "../checkout/checkoutSlice";
import {
  getUserDetails,
  selectIsLoggedIn,
  selectUser,
} from "../user/userSlice";
import {
  fetchProducts,
  selectError,
  selectLoading,
  selectProducts,
} from "./productSlice";

export const ProductList = () => {
  const dispatch = useAppDispatch();
  const products = useAppSelector(selectProducts);
  const loading = useAppSelector(selectLoading);
  const error = useAppSelector(selectError);
  const user = useAppSelector(selectUser);
  const isAuthenticated = useAppSelector(selectIsLoggedIn);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(fetchProducts());
    if (isAuthenticated) dispatch(getUserDetails());
  }, [dispatch, isAuthenticated]);

  return (
    <div>
      <h1>Products</h1>
      {loading && <p>Loading...</p>}
      {error && <p>Error :(</p>}
      {isAuthenticated && user?.role?.toLowerCase() === "admin" && (
        <button className="btn btn-primary" onClick={() => navigate(`/products/add`)}>Add new product</button>
      )}
      {products.map((product) => (
        <div key={product.id} className="row product">
          <Link to={`/products/${product.id}`} className="col-md-2">
            <img
              src={product.imageUrl}
              alt={product.name}
              className="img-fluid"
            />
          </Link>
          <div className="col-md-6 product-detail">
            <Link
              className="text-decoration-none link-dark underline-on-hover"
              to={`/products/${product.id}`}
            >
              <h4>{product.name}</h4>
            </Link>
            <p>{product.description}</p>
          </div>
          <div className="col-md-2 product-price">
            <span>${product.price}</span>
          </div>
          <div className="col-md-2">
            <button
              className="btn btn-primary"
              onClick={() =>
                dispatch(addToCart({ productId: product.id, quantity: 1 }))
              }
            >
              Add to cart
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};
