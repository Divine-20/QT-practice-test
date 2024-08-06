import { Button } from "@mantine/core";
import { notifications } from "@mantine/notifications";
import { Helmet } from "react-helmet";
import { Link, useNavigate } from "react-router-dom";
import AuthInput from "../components/AuthInput";
import AuthLayout from "../components/layouts/AuthLayout";
import useAuth from "../hooks/useAuth";
import Logo from "../components/Logo";

export default function Register() {
  const { register, registering } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget as HTMLFormElement;
    const firstName = form.firstName.value;
    const lastName = form.lastName.value;
    const userName = form.userName.value;
    const nationalId = form.nationalId.value;
    const phoneNumber = form.phoneNumber.value;
    const dateOfBirth = form.dateOfBirth;
    const gender = form.gender.value;
    const email = form.email.value;
    const password = form.password.value;
    const repeatPassword = form.repeatPassword.value;
    if (password !== repeatPassword) {
      notifications.show({
        title: "Error",
        message: "Passwords do not match",
        color: "red",
      });
      return;
    }
    register(
      firstName,
      lastName,
      userName,
      nationalId,
      dateOfBirth,
      phoneNumber,
      gender,
      email,
      password
    );
  };

  return (
    <>
      <Helmet>
        <title>Create account</title>
      </Helmet>
      <AuthLayout>
        <div className="mx-auto w-full max-w-sm lg:w-96">
          <div>
            <Logo />
            <h2 className="mt-8 text-2xl font-bold leading-9 tracking-tight text-gray-900">
              Sign in to your account
            </h2>
            <p className="mt-2 text-sm leading-6 text-gray-500">
              Already have an account{" "}
              <Link
                to="/login"
                className="font-semibold text-[#354545] hover:text-[#35454590]"
              >
                Login
              </Link>
            </p>
          </div>

          <div className="mt-10">
            <div>
              <form onSubmit={handleSubmit} className="space-y-6">
                <div className="flex gap-4">
                  <AuthInput
                    label="First name"
                    required
                    type="text"
                    name="firstName"
                    min={2}
                    max={50}
                  />
                  <AuthInput
                    label="Last name"
                    required
                    type="text"
                    name="lastName"
                    min={2}
                    max={50}
                  />
                </div>
                <div className="flex gap-4">
                  <AuthInput
                    label="Username"
                    required
                    type="text"
                    name="userName"
                    min={2}
                    max={50}
                  />

                  <AuthInput
                    label="Email Address"
                    required
                    autoComplete="email"
                    type="email"
                    name="email"
                  />
                </div>
                <div className="flex gap-4">
                  <AuthInput
                    label="National Id"
                    required
                    type="text"
                    name="nationalId"
                    min={2}
                    max={50}
                  />
                  <AuthInput
                    label="Phone Number"
                    required
                    type="text"
                    name="phoneNumber"
                    min={2}
                    max={50}
                  />
                </div>
                <div className="flex gap-4">
                  <AuthInput
                    label="Date of Birth"
                    required
                    type="date"
                    name="dateOfBirth"
                    className="w-[9.7vw]"
                  />

                  <AuthInput
                    label="Gender"
                    required
                    type="text"
                    name="gender"
                    min={2}
                    max={50}
                  />
                </div>
                <AuthInput
                  label="Password"
                  required
                  autoComplete="current-password"
                  type="password"
                  name="password"
                  min={4}
                  max={50}
                />
                <AuthInput
                  label="Repeat Password"
                  required
                  autoComplete="current-password"
                  type="password"
                  name="repeatPassword"
                  min={4}
                  max={50}
                />

                <div>
                  <Button
                    type="submit"
                    className="w-full bg-[#354545] h-12 hover:bg-[#35454580]"
                    loading={registering}
                  >
                    Create account
                  </Button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </AuthLayout>
    </>
  );
}
