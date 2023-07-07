'use client';
import React, { useState } from 'react'
import LoginService from '@/app/service/LoginService';
import {alertService} from "@/app/service/AlertService";
import {Alert} from "@/app/components/Alert";
import Register from "./RegisterForm";
import { RiEyeLine, RiEyeOffLine } from 'react-icons/ri';

const LoginForm = () => {
    const [loginDTO, setLoginDTO] = useState({
        email: '',
        password: ''
    })
	const [showPassword, setShowPassword] = useState(false);

	const togglePasswordVisibility = () => {
		setShowPassword(!showPassword);
	  };

	const [errorMessage, setErrorMessage] = useState(false);

    const handleChange = (e) => {
        const value = e.target.value;
        setLoginDTO({...loginDTO, [e.target.name]: value})
    }

    const loginUser = (e) => {
        e.preventDefault();
        LoginService.loginUser(loginDTO)
        .then((response) => {
			alertService.success(response.data);
			if (response.status===200) {
				window.location.href= "http://localhost:3000/";
			} else {
				setErrorMessage(true);
			}
        }).catch((error) => {
            console.log(error);
			alertService.error(error.response.data.errors);
        });
    }
    
    const reset = (e) => {
		e.preventDefault();
		setLoginDTO({
			email: '',
			password: ''
		});
    }

    return (
      	<div className='m-auto w-full max-w-screen-md p-6 my-12 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-md'>
			<div>
				<h1 className='rounded w-full my-8 text-center bg-htadarkteal text-white py-2 px-6 font-bold'>Login</h1>
			</div>
			<div>
				<Alert/>
			</div>
			<div className='flex'>
				<div className='m-auto py-2 h-14'>
					<label className='inline-block text-gray-800 text-sm font-normal'>Email:
						<br/>
						<input
							type="text"
							name="email"
							placeholder="Email..."
							value={loginDTO.email}
							onChange={(e) => handleChange(e)}
							className='h-10 w-56 border mt-2 px-2 py2'
						/>
					</label>
						<br/>
					<label className='inline-block text-gray-800 text-sm font-normal'>Password:
						<br/>
						<input
							type={showPassword ? 'text' : 'password'}
							name="password"
							placeholder="Password..."
							value={loginDTO.password}
							onChange={(e) => handleChange(e)}
							className='h-10 w-56 border mt-2 px-2 py2'
						/>
						<button
							type="button"
							onClick={togglePasswordVisibility}
							className="inline-block focus:outline-none ml-2"
						>
							{showPassword ? (
							<RiEyeOffLine size={15} />
							) : (
							<RiEyeLine size={15} />
							)}
						</button>
					</label>
				</div>
				{errorMessage}

			</div>
			<div className='mt-40 flex justify-center'>
				<button 
					type="submit" 
					onClick={loginUser}
					className='rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6'>
					Login
				</button>
				<button
					type="reset"
					onClick={reset}
					className='rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 ml-6'>
					Clear
				</button>
				<div className='rounded text-white font-semibold bg-sky-600 hover:bg-sky-900 py-2 px-6 ml-6'>
					<Register />
				</div>
			</div>
		</div>
  )
}

export default LoginForm