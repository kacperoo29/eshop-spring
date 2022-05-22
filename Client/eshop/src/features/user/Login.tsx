import React from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../app/hooks";
import { authenticate } from "./userSlice";

import "./Login.css";

export const Login = () => {
  const dispatch = useAppDispatch();
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const navigate = useNavigate();

  const handleLogin = () => {
    dispatch(authenticate({ email: email, password: password })).then((r) => {
      // TODO: push to success page
      if (r.meta.requestStatus === "fulfilled") navigate("/");
    });
  };

  return (
    <div className="card card-container">
      <h1>Login</h1>
      <div className="form-signin">
        <span id="reauth-email" className="reauth-email"></span>
        <input
          className="from-control"
          type="email"
          placeholder="Email"
          required
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="from-control"
          type="password"
          placeholder="Password"
          required
          onChange={(e) => setPassword(e.target.value)}
        />
        <button
          className="btn btn-primary btn-block btn-signin btn-lg"
          onClick={handleLogin}
        >
          Login
        </button>
      </div>
    </div>
  );
};
