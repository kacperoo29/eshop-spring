import React from "react";
import { useAppDispatch } from "../../app/hooks";

import { createUser } from "./userSlice";
import "./Login.css";

export const Register = () => {
  const dispatch = useAppDispatch();
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [username, setUsername] = React.useState("");

  const handleRegister = () => {
    dispatch(
      createUser({ email: email, username: username, password: password })
    ).then((r) => {
      if (r.meta.requestStatus === "rejected") {
        alert("Something went wrong");
      } else {
        alert("Successfully registered");
      }
    });
  };

  return (
    <div className="card card-container">
      <h1>Register</h1>
      <div className="form-signin">
        <span id="reauth-email" className="reauth-email"></span>
        <input
          className="from-control"
          type="text"
          placeholder="Username"
          required
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          className="from-control"
          type="text"
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
          onClick={handleRegister}
        >
          Register
        </button>
      </div>
    </div>
  );
};
