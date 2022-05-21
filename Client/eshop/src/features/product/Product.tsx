import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import { fetchProduct, selectProduct } from "./productSlice";

export const Product = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const product = useAppSelector(selectProduct);

  useEffect(() => {
    console.log(id)
    dispatch(fetchProduct(id!));
  }, [dispatch, id]);

  return (
    <div>
      <div className="row">
        <div className="col-md-6">
          <img src={product.imageUrl} alt={product.name} />
        </div>
        <div className="col-md-6">
          <h1>{product.name}</h1>
          <p>{product.description}</p>
          <p>{product.price}</p>
        </div>
      </div>
    </div>
  );
};
