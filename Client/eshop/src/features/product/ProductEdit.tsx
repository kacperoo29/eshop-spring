import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ProductDto } from "../../api";
import { useAppDispatch } from "../../app/hooks";
import { editProduct, fetchProduct } from "./productSlice";

export const ProductEdit = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams();

  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [price, setPrice] = React.useState(0);
  const [imageUrl, setImageUrl] = React.useState("");

  useEffect(() => {
    dispatch(fetchProduct(id!)).then((r) => {
      if (r.meta.requestStatus !== "fulfilled")
        return;
      setName((r.payload as ProductDto).name!);
      setDescription((r.payload as ProductDto).description!);
      setPrice((r.payload as ProductDto).price!);
      setImageUrl((r.payload as ProductDto).imageUrl!);
    });
  }, [dispatch, id]);

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    dispatch(
      editProduct({
        productId: id!,
        payload: { name, description, price, imageUrl },
      })
    ).then((r) => {
      navigate(`/products/${id}`);
    });
  };

  return (
    <div>
      <h1>Edit Product</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            name="name"
            id="name"
            className="form-control"
            required
            value={name}
            onChange={(event) => setName(event.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description</label>
          <input
            type="text"
            name="description"
            id="description"
            className="form-control"
            required
            value={description}
            onChange={(event) => setDescription(event.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="price">Price</label>
          <input
            type="number"
            name="price"
            id="price"
            className="form-control"
            step=".01"
            min="0"
            required
            value={price}
            onChange={(e) => setPrice(e.target.valueAsNumber)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="imageUrl">Image URL</label>
          <input
            type="text"
            name="imageUrl"
            id="imageUrl"
            className="form-control"
            required
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Edit Product
        </button>
      </form>
    </div>
  );
};
