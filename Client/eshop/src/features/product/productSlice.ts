import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { CreateProductDto, EditProductDto, ProductDto } from "../../api";
import { RootState } from "../../app/store";
import { productApi } from "../apiInstances";

export interface ProductState {
  products: ProductDto[];
  product: ProductDto;
  status: "idle" | "loading" | "failed";
  error: string;
}

export const fetchProduct = createAsyncThunk(
  "product/fetchProduct",
  async (id: string) => {
    const response = await productApi.getById({ id });

    return response;
  }
);

export const fetchProducts = createAsyncThunk(
  "product/fetchProducts",
  async () => {
    const response = await productApi.get();

    return response;
  }
);

export const createProduct = createAsyncThunk(
  "product/createProduct",
  async (payload: CreateProductDto) => {
    const response = await productApi.create({ createProductDto: payload });

    return response;
  }
);

export const editProduct = createAsyncThunk(
  "product/editProduct",
  async (request: { productId: string; payload: EditProductDto }) => {
    const response = await productApi.edit({
      id: request.productId,
      editProductDto: request.payload,
    });

    return response;
  }
);

const initialState: ProductState = {
  products: [],
  product: {} as ProductDto,
  status: "idle",
  error: "",
};

export const productSlice = createSlice({
  name: "product",
  initialState: initialState,
  reducers: {},
  extraReducers: {
    // Fetch product
    [fetchProduct.fulfilled.type]: (
      state,
      action: PayloadAction<ProductDto>
    ) => {
      state.product = action.payload;
      state.status = "idle";
    },
    [fetchProduct.pending.type]: (state) => {
      state.status = "loading";
    },
    [fetchProduct.rejected.type]: (state, action) => {
      state.status = "failed";
      state.error = action.error.message;
    },
    // Fetch products
    [fetchProducts.fulfilled.type]: (state, action) => {
      state.products = action.payload;
      state.status = "idle";
    },
    [fetchProducts.pending.type]: (state) => {
      state.status = "loading";
    },
    [fetchProducts.rejected.type]: (state, action) => {
      state.status = "failed";
      state.error = action.payload.message;
    },
    // Create product
    [createProduct.fulfilled.type]: (
      state,
      action: PayloadAction<ProductDto>
    ) => {
      state.product = action.payload;
      state.status = "idle";
    },
    [createProduct.pending.type]: (state) => {
      state.status = "loading";
    },
    [createProduct.rejected.type]: (state, action) => {
      state.status = "failed";
      state.error = action.error.message;
    },
    // Edit product
    [editProduct.fulfilled.type]: (
      state,
      action: PayloadAction<ProductDto>
    ) => {
      state.product = action.payload;
      state.status = "idle";
    },
    [editProduct.pending.type]: (state) => {
      state.status = "loading";
    },
    [editProduct.rejected.type]: (state, action) => {
      state.status = "failed";
      state.error = action.error.message;
    },
  },
});

export const { actions } = productSlice;

export const selectProduct = (state: RootState) => state.product.product;
export const selectProducts = (state: RootState) => state.product.products;
export const selectLoading = (state: RootState) =>
  state.product.status === "loading";
export const selectError = (state: RootState) => state.product.error;

export default productSlice.reducer;
