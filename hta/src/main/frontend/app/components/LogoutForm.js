'use client';
import React, {Fragment, useState} from 'react'
import {Dialog, Transition} from "@headlessui/react";
import axios from "axios";


const LogoutForm = () => {

	const [isOpen, setIsOpen] = useState(false);

	const LOGOUT_API_BASE_URL = 'http://localhost:8080/api/user/logout';

	function closeModal() {
		setIsOpen(false);
	}

	function openModal() {
		setIsOpen(true);
	}

	const deleteUser = (e) => {
		e.preventDefault();
		return axios.get(LOGOUT_API_BASE_URL, { withCredentials: true })
			.then((response) => {
				console.log(response.status);
				if (response.status === 200) {
					window.location.href = "http://localhost:3000/";
				} else {
					setErrorMessage(true);
				}
			}).catch((error) => {
			console.log(error);
		});
	}

	const reset = () => {
		window.location.href = "http://localhost:3000/";
		};

	return (
		<div>
				<div className='m-auto w-full max-w-screen-md text-left align-middle transition-all transform shadow-xl rounded-md' >
										 <div className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'>
										<h3
											className='text-lg font-bold text-white m-auto text-center'>
											Do you want to logout?
										</h3>
										 </div>
									<div className="bg-htamediumteal rounded-md shadow-slate-900 shadow-md">
										<div>
												<div>
													<div className='flex justify-center pt-2 pb-4'>
														<div className='h-14 my-4 pt-4'>
															<button
																onClick={deleteUser}
																className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'>
																Logout
															</button>
														</div>
														<div className='h-14 my-4 pt-4'>
															<button
																onClick={reset}
																className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'>
																Stay logged in
															</button>
														</div>
													</div>
												</div>
										</div>
									</div>
								</div>
		</div>
	)
}

export default LogoutForm