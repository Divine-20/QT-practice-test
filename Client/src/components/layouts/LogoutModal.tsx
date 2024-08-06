import { IoIosLogOut } from "react-icons/io";
import React from "react";
import { Button } from "@mantine/core";
import useAuth from "../../hooks/useAuth";

function LogoutModal() {
  const { logout } = useAuth();
  return (
    <Button
      className="flex text-red-500 bg-red-100  hover:bg-red-200"
      onClick={logout}
    >
      <IoIosLogOut />
      <p className="pl-2">Logout</p>
    </Button>
  );
}

export default LogoutModal;
