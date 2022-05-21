import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
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

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div>
      <h1>Products</h1>
      {loading && <p>Loading...</p>}
      {error && <p>Error :(</p>}
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
          <Link className="text-decoration-none link-dark underline-on-hover" to={`/products/${product.id}`}><h4>{product.name}</h4></Link>
            <p>{product.description}</p>
          </div>
          <div className="col-md-2 product-price">
            <span>${product.price}</span>
          </div>
          <div className="col-md-2">
            <Link to={`/products`} className="btn btn-primary">
              Add to cart
            </Link>
          </div>
        </div>
      ))}
    </div>
  );
};
