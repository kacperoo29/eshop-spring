import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import { getOrders, selectOrders } from "./checkoutSlice";

export const Orders = () => {
  const dispatch = useAppDispatch();
  const orders = useAppSelector(selectOrders);

  useEffect(() => {
    dispatch(getOrders());
  }, [dispatch]);

  return (
    <div>
      <h2>Order history</h2>
      <ul>
        {orders?.map((order) => (
          <div className="row mt-4 border rounded p-2" key={order.id}>
            <h4>Order id: {order.id}</h4>
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">Product name</th>
                  <th scope="col">Quantity</th>
                  <th scope="col">Price per unit</th>
                  <th scope="col">Total price</th>
                </tr>
              </thead>
              <tbody>
                {order.products?.map((item) => (
                  <tr key={item.product?.id}>
                    <td>
                      <Link
                        to={`/products/${item.product?.id}`}
                        className="text-decoration-none link-dark"
                      >
                        {item.product?.name}
                      </Link>
                    </td>
                    <td>{item.quantity}</td>
                    <td>${item.product?.price}</td>
                    <td>
                      $
                      {(
                        (item.product?.price ?? 0) * (item.quantity ?? 0)
                      ).toFixed(2)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <h6>
              Total order price: $
              {order.products
                ?.reduce(
                  (acc, item) =>
                    acc + (item.product?.price ?? 0) * (item.quantity ?? 0),
                  0
                )
                .toFixed(2)}
            </h6>
          </div>
        ))}
      </ul>
    </div>
  );
};
