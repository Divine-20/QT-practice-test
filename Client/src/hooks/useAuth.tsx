/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react-refresh/only-export-components */
import { notifications } from "@mantine/notifications";
import { createContext, useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../lib/axios.config";
import { deleteCookie, setCookie } from "../lib/utils";
import { User } from "../types";

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => void;
  loggingIn: boolean;
  register: (
    firstName: string,
    lastName: string,
    userName: string,
    nationalId: string,
    phoneNumber: string,
    dateOfBirth: string,
    gender: string,
    email: string,
    password: string
  ) => void;
  registering: boolean;
  logout: () => void;
  loggingOut: boolean;
  initialLoading: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [loggingIn, setLoggingIn] = useState(false);
  const [registering, setRegistering] = useState(false);
  const [loggingOut, setLoggingOut] = useState(false);
  const [initialLoading, setInitialLoading] = useState(true);
  const [user, setUser] = useState<User | null>(null);

  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (user) {
      setInitialLoading(false);
      return;
    }
    const fetchUser = async () => {
      try {
        const { data } = await axios.get("/auth/profile");
        setUser(data.user);
      } catch (error) {
        setUser(null);
        if (location.pathname.includes("/posts" || "/comments")) {
          navigate("/login");
        }
      } finally {
        setInitialLoading(false);
      }
    };
    fetchUser();
  }, [location.pathname, user]);

  const login = async (email: string, password: string) => {
    setLoggingIn(true);
    try {
      const { data } = await axios.post("/auth/login", {
        email,
        password,
      });
      setUser(data.data.user);
      console.log(data.data.user);

      setCookie("token", data.data.accessToken, 7);
      notifications.show({
        title: "Success",
        message: "Logged in successfully",
        color: "green",
      });
      navigate("/posts");
    } catch (error) {
      notifications.show({
        title: "Error",
        message: "Invalid email or password",
        color: "red",
      });
    } finally {
      setLoggingIn(false);
    }
  };

  const register = async (
    firstName: string,
    lastName: string,
    userName: string,
    nationalId: string,
    phoneNumber: string,
    dateOfBirth: string,
    gender: string,
    email: string,
    password: string
  ) => {
    setRegistering(true);
    try {
      const { data } = await axios.post("/auth/register", {
        firstName,
        lastName,
        userName,
        nationalId,
        phoneNumber,
        dateOfBirth,
        gender,
        email,
        password,
      });
      notifications.show({
        title: "Success",
        message: "Account created successfully",
        color: "green",
      });
      setUser(data.data.user);
      setCookie("token", data.data.accessToken, 7);
      navigate("/posts");
    } catch (error) {
      notifications.show({
        title: "Error",
        message: "An error occurred",
        color: "red",
      });
    } finally {
      setRegistering(false);
    }
  };

  const logout = async () => {
    setLoggingOut(true);
    try {
      deleteCookie("token");
      setUser(null);
      notifications.show({
        title: "Success",
        message: "Logged out successfully",
        color: "green",
      });
      navigate("/login");
    } catch (error) {
      notifications.show({
        title: "Error",
        message: "An error occurred",
        color: "red",
      });
    } finally {
      setLoggingOut(false);
    }
  };
  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        loggingIn,
        register,
        registering,
        logout,
        loggingOut,
        initialLoading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
