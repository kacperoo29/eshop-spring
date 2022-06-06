import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { AddProductToCartDto, CartDto, ChangeQuantityDto } from "../../api";
import { RootState } from "../../app/store";
import { checkoutApi } from "../apiInstances";

export interface CheckoutState {
  cart: CartDto | null;
  status: "idle" | "loading" | "error";
  error: string | null;
  orders: CartDto[] | null;
}

export const initialState: CheckoutState = {
  cart: null,
  status: "idle",
  error: null,
  orders: null,
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

export const getOrders = createAsyncThunk("checkout/getOrders", async () => {
  const response = await checkoutApi.getUserOrders();

  return response;
});

export const postOrder = createAsyncThunk(
  "checkout/postOrder",
  async () => {
    const response = await checkoutApi.postOrder();
    
    return response;
  }
);

export const changeQuantity = createAsyncThunk(
  "checkout/changeQuantity",
  async (payload: ChangeQuantityDto) => {
    const response = await checkoutApi.changeQuantity({ changeQuantityDto: payload });

    return response;
  }
);

export const removeProduct = createAsyncThunk(
  "checkout/removeProduct",
  async (payload: string) => {
    const response = await checkoutApi.removeProductFromCart({ productId: payload });

    return response;
  }
)

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
    // Get orders
    [getOrders.fulfilled.type]: (state, action) => {
      state.orders = action.payload;
      state.status = "idle";
    },
    [getOrders.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [getOrders.pending.type]: (state) => {
      state.status = "loading";
    },
    // Post order
    [postOrder.fulfilled.type]: (state, action) => {
      state.status = "idle";
    },
    [postOrder.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [postOrder.pending.type]: (state) => {
      state.status = "loading";
    },
    // Change quantity
    [changeQuantity.fulfilled.type]: (state, action) => {
      state.status = "idle";
    },
    [changeQuantity.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [changeQuantity.pending.type]: (state) => {
      state.status = "loading";
    },
    // Remove product
    [removeProduct.fulfilled.type]: (state, action) => {
      state.cart = action.payload;
      state.status = "idle";
    },
    [removeProduct.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    [removeProduct.pending.type]: (state) => {
      state.status = "loading";
    }
  },
});

export const selectCart = (state: RootState) => state.checkout.cart;
export const selectCheckoutStatus = (state: RootState) => state.checkout.status;
export const selectCheckoutError = (state: RootState) => state.checkout.error;
export const selectOrders = (state: RootState) => state.checkout.orders;

export default checkoutSlice.reducer;
