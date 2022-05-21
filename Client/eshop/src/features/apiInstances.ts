import { CheckoutApi, Configuration, ProductsApi, UsersApi } from "../api";

let accessToken = "";

export const productApi = new ProductsApi(new Configuration({accessToken: () => accessToken}));
export const userApi = new UsersApi(new Configuration({ accessToken: () => accessToken }));
export const checkoutApi = new CheckoutApi(new Configuration({ accessToken: () => accessToken }));
