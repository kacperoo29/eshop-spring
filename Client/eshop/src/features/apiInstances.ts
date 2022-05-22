import { CheckoutApi, Configuration, ProductsApi, UsersApi } from "../api";
import Cookies from "universal-cookie";

const cookies = new Cookies();

var accessToken: string | undefined = cookies.get("accessToken");
export const setAccessToken = (token: string | undefined) => {
  cookies.set("accessToken", token ?? "", { path: "/" });
  accessToken = token;
}

export const cleanAccessToken = () => {
  cookies.remove("accessToken", { path: "/" });
  accessToken = undefined;
}

export const productApi = new ProductsApi(
  new Configuration({ accessToken: () => accessToken ?? "" })
);
export const userApi = new UsersApi(
  new Configuration({ accessToken: () => accessToken ?? "" })
);
export const checkoutApi = new CheckoutApi(
  new Configuration({ accessToken: () => accessToken ?? "" })
);
