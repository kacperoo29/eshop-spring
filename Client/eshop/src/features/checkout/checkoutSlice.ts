import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { AddProductToCartDto, CartDto } from "../../api";
import { RootState } from "../../app/store";
import { checkoutApi } from "../apiInstances";

export interface CheckoutState {
  cart: CartDto | null;
  status: "idle" | "loading" | "error";
  error: string | null;
}

export const initialState: CheckoutState = {
  cart: null,
  status: "idle",
  error: null,
};

export const getCart = createAsyncThunk("checkout/getCart", async () => {
  const response = await checkoutApi.getCart();

  return response;
});

export const addToCart = createAsyncThunk(
  "checkout/addToCart",
  async (payload: AddProductToCartDto) => {
    const response = await checkoutApi.addProductToCart({
      addProductToCartDto: payload,
    });

    return response;
  }
);

export const checkoutSlice = createSlice({
  name: "checkout",
  initialState: initialState,
  reducers: {},
  extraReducers: {
    // Get cart
    [getCart.fulfilled.type]: (state, action) => {
      state.cart = action.payload;
      state.status = "idle";
    },
    [getCart.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [getCart.pending.type]: (state) => {
      state.status = "loading";
    },
    // Add to cart
    [addToCart.fulfilled.type]: (state, action) => {
      state.cart = action.payload;
      state.status = "idle";
    },
    [addToCart.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [addToCart.pending.type]: (state) => {
      state.status = "loading";
    },
  },
});

export const selectCart = (state: RootState) => state.checkout.cart;
export const selectCheckoutStatus = (state: RootState) => state.checkout.status;
export const selectError = (state: RootState) => state.checkout.error;

export default checkoutSlice.reducer;
