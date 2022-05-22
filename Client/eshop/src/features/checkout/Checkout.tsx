import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import { getCart, postOrder, selectCart } from "./checkoutSlice";

export const Checkout = () => {
  const dispatch = useAppDispatch();
  const cart = useAppSelector(selectCart);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getCart());
  }, [dispatch]);

  const handleCheckout = () => {
    dispatch(postOrder()).then((r) => {
      if (r.meta.requestStatus === "fulfilled") navigate("/orders");
    });
  };

  return (
    <>
      {!cart?.products || cart?.products?.length < 1 ? (
        <h3>Cart is empty</h3>
      ) : (
        <div className="row">
          <h1>Checkout</h1>
          <div className="col-md-8">
            <h2>Cart</h2>
            <ul>
              {cart?.products?.map((item) => (
                <li key={item.product?.id}>
                  {item.product?.name} - ${item.product?.price}x{item.quantity}
                </li>
              ))}
            </ul>
          </div>
          <div className="col-md-4">
            <h2>Total</h2>
            <p>
              $
              {cart?.products?.reduce(
                (acc, item) =>
                  acc + (item.product?.price ?? 0 * (item.quantity ?? 0)),
                0
              )}
            </p>
            <button className="btn btn-primary" onClick={handleCheckout}>
              Checkout
            </button>
          </div>
        </div>
      )}
    </>
  );
};
