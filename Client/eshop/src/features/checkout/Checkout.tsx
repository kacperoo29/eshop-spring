import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { CartDto, ChangeQuantityDtoFromJSONTyped } from "../../api";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import {
  changeQuantity,
  getCart,
  postOrder,
  removeProduct,
  selectCart,
  selectCheckoutError,
  selectCheckoutStatus,
} from "./checkoutSlice";

export const Checkout = () => {
  const dispatch = useAppDispatch();
  const cart = useAppSelector(selectCart);
  const navigate = useNavigate();
  const error = useAppSelector(selectCheckoutError);
  const [quantity, setQuantity] = useState<{ [id: string]: number }>();
  const status = useAppSelector(selectCheckoutStatus);

  useEffect(() => {
    dispatch(getCart()).then((r) => {
      if (r.meta.requestStatus === "fulfilled") {
        (r.payload as CartDto).products?.forEach((p) => {
          setQuantity((q) => ({ ...q, [p.product?.id!]: p.quantity! }));
        });
      }
    });
  }, [dispatch]);

  const handleCheckout = () => {
    dispatch(postOrder()).then((r) => {
      if (r.meta.requestStatus === "fulfilled") navigate("/orders");
      else alert(error);
    });
  };

  const handleQuantityChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    productId: string
  ) => {
    setQuantity((q) => ({ ...q, [productId]: e.target.valueAsNumber }));
    dispatch(
      changeQuantity({ productId: productId, quantity: e.target.valueAsNumber })
    ).then((r) => {
      if (r.meta.requestStatus === "rejected") alert(error);
    });
  };

  const handleRemove = (productId: string) => {
    dispatch(removeProduct(productId)).then((r) => {
      if (r.meta.requestStatus === "rejected") alert(error);
    });
  };

  if (status === "loading") return <div>Loading...</div>;

  return (
    <>
      {!cart?.products || cart?.products?.length < 1 ? (
        <h3>Cart is empty</h3>
      ) : (
        <div className="row">
          <h1>Checkout</h1>
          <div className="col-md-8">
            <h2>Cart</h2>
            <table className="table">
              <thead>
                <tr>
                  <th>Product</th>
                  <th>Quantity</th>
                  <th>Price</th>
                  <th>Remove</th>
                </tr>
              </thead>
              <tbody>
                {cart?.products.map((product) => (
                  <tr key={product.product?.id}>
                    <td>{product.product?.name}</td>
                    <td>
                      <input
                        className="form-control"
                        type="number"
                        min="1"
                        step={1}
                        value={quantity ? quantity[product.product?.id!] : 1}
                        onChange={(e) =>
                          handleQuantityChange(e, product.product?.id!)
                        }
                      />
                    </td>
                    <td>${product.product?.price}</td>
                    <td>
                      <button
                        className="btn btn-danger"
                        onClick={() => handleRemove(product.product?.id!)}
                      >
                        Remove
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className="col-md-4">
            <h2>Total</h2>
            <p>
              $
              {cart?.products
                ?.reduce(
                  (acc, item) =>
                    acc +
                    (item.product?.price ?? 0) *
                      (quantity ? quantity[item.product?.id!] : 1),
                  0
                )
                .toFixed(2)}
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
