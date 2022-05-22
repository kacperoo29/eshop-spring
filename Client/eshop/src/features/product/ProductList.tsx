import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ProductDto } from "../../api";
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

  const [quantity, setQuantity] = React.useState<{ [id: string]: number }>();

  useEffect(() => {
    dispatch(fetchProducts()).then((r) => {
      if (r.meta.requestStatus === "fulfilled") {
        (r.payload as ProductDto[]).forEach((p) => {
          setQuantity({ ...quantity, [p.id!]: 1 });
        });
      }
    });
    if (isAuthenticated) dispatch(getUserDetails());
  }, [dispatch, isAuthenticated]);

  return (
    <div>
      <h1>Products</h1>
      {loading && <p>Loading...</p>}
      {error && <p>Error :(</p>}
      {isAuthenticated && user?.role?.toLowerCase() === "admin" && (
        <button
          className="btn btn-primary"
          onClick={() => navigate(`/products/add`)}
        >
          Add new product
        </button>
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
          <div className="col-md-5 product-detail">
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
          <div className="col-md-3">
            {isAuthenticated ? (
              <>
                <div className="form-inline row">
                  <div className="form-group col-lg-6 mt-4">
                    <button
                      className="btn btn-primary"
                      onClick={() =>
                        dispatch(
                          addToCart({
                            productId: product.id,
                            quantity: quantity ? quantity[product.id!] : 1,
                          })
                        )
                      }
                    >
                      Add to cart
                    </button>
                  </div>
                  <div className="form-group col-lg-4">
                    <label htmlFor="quantity">Count</label>
                    <input
                      className="form-control"
                      type="number"
                      min="1"
                      max="99"
                      name="quantity"
                      value={quantity ? quantity[product.id!] : 1}
                      onChange={(e) =>
                        setQuantity({ [product.id!]: e.target.valueAsNumber })
                      }
                    />
                  </div>
                </div>
                <div>
                  {user?.role?.toLowerCase() === "admin" && (
                    <Link
                      className="btn btn-secondary"
                      to={`/products/edit/${product.id}`}
                    >
                      Edit
                    </Link>
                  )}
                </div>
              </>
            ) : (
              <Link className="btn btn-primary" to={`/login`}>
                Login to add to cart
              </Link>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};
