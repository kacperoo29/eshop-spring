import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../app/hooks";
import { logout } from "./userSlice";

export const Logout = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    dispatch(logout());
    navigate("/");
  }, [dispatch, navigate]);

  return (
    <div>
      <h1>Logging out...</h1>
    </div>
  );
};
