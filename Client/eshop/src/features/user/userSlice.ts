import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  AuthDto,
  AuthenticateRequest,
  AuthRequestDto,
  CreateUserDto,
  CreateUserRequest,
  UserDto,
} from "../../api";
import { RootState } from "../../app/store";
import { setAccessToken, userApi } from "../apiInstances";
import Cookies from "universal-cookie";

const cookies = new Cookies();

export interface UserState {
  user: UserDto | null;
  status: "idle" | "loading" | "error";
  authResult: AuthDto | null;
  error: string | null;
}

const initialState: UserState = {
  user: null,
  status: "idle",
  authResult: {
    token: cookies.get("accessToken"),
    isSucessfull: cookies.get("accessToken") !== undefined,
  },
  error: null,
};

export const authenticate = createAsyncThunk(
  "user/authenticate",
  async (payload: AuthRequestDto) => {
    const response = await userApi.authenticate({ authRequestDto: payload });

    return response;
  }
);

export const createUser = createAsyncThunk(
  "user/createUser",
  async (payload: CreateUserDto) => {
    const response = await userApi.createUser({ createUserDto: payload });

    return response;
  }
);

export const userSlice = createSlice({
  name: "user",
  initialState: initialState,
  reducers: {
    logout: (state: UserState) => {
      state.authResult = {
        token: undefined,
        isSucessfull: undefined,
        errorMessage: undefined,
      };
      setAccessToken("");
    },
  },
  extraReducers: {
    // Authenticate
    [authenticate.fulfilled.type]: (state, action: PayloadAction<AuthDto>) => {
      state.authResult = action.payload;
      state.status = "idle";
      setAccessToken(action.payload.token);
    },
    [authenticate.pending.type]: (state) => {
      state.status = "loading";
    },
    [authenticate.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
    // Create user
    [createUser.fulfilled.type]: (state, action: PayloadAction<UserDto>) => {
      state.user = action.payload;
      state.status = "idle";
    },
    [createUser.pending.type]: (state) => {
      state.status = "loading";
    },
    [createUser.rejected.type]: (state, action) => {
      state.status = "error";
      state.error = action.error.message;
    },
  },
});

export const selectUser = (state: RootState) => state.user.user;
export const selectToken = (state: RootState) => state.user.authResult?.token;
export const selectIsLoggedIn = (state: RootState) =>
  state.user.authResult?.token !== null && state.user.authResult?.isSucessfull;
export const selectIsSuccess = (state: RootState) =>
  state.user.authResult?.isSucessfull;
export const selectAuthError = (state: RootState) =>
  state.user.authResult?.errorMessage;
export const selectUserError = (state: RootState) => state.user.error;

export const { logout } = userSlice.actions;

export default userSlice.reducer;
