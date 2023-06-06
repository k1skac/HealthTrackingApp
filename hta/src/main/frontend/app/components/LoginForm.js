'use client';
import React, { useState } from 'react'
import LoginService from '@/app/service/LoginService';
import { setCookie, hasCookie } from 'cookies-next';

const LoginForm = () => {
    const [loginDTO, setLoginDTO] = useState({
        email: '',
        password: ''
    })

    const handleChange = (e) => {
        const value = e.target.value;
        setLoginDTO({...loginDTO, [e.target.name]: value})
    }

    const loginUser = (e) => {
        e.preventDefault();
        LoginService.loginUser(loginDTO)
        .then((response) => {
            console.log(response.status);
        }).catch((error) => {
            console.log(error);
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
				<a href="http://localhost:3000/add-vital-health-signs">Add my daily data</a>
			</div>
			<div>
				<h1 className='rounded w-full my-8 text-center bg-sky-800 text-white py-2 px-6 font-bold capitalize'>Login</h1>
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
						type="text" 
						name="password" 
						placeholder="Password..."
						value={loginDTO.password}
						onChange={(e) => handleChange(e)}
						className='h-10 w-56 border mt-2 px-2 py2'
					/>
				</label> 
				</div>
				
			</div>
			<div className='mt-40 inline-flex'>
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
				</div>
		</div>
  )
}

export default LoginForm