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
			<button
				onClick={openModal}
				className='rounded bg-htadarkteal hover:bg-htadarktealhover text-white w-48 py-3 shadow-cyan-950 shadow-md font-semi'>
				Logout
			</button>

			<Transition appear show={isOpen} as={Fragment}>
				<div className='m-auto w-full max-w-screen-md text-left align-middle transition-all transform shadow-xl rounded-md' >
					<Dialog
						as="div"
						className='fixed inset-24 max-w-screen-md h-max mx-auto'
						onClose={closeModal}>
						<div className='min-h-screen text-center'>
							<Transition.Child
								as={Fragment}
								enter='ease-out duration-300'
								enterFrom='opacity-0 scale-95'
								enterTo='opacity-100 scale-100'
								leave='ease-in duration-200'
								leaveFrom='opacity-100 csale-100'
								leaveTo='opacity-0 scale-95'>
								<div className="overflow-auto">
									<Dialog.Title
										as='div'
										className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'>
										<h3
											className='text-lg font-bold text-white m-auto'>
											Do you want to logout?
										</h3>
									</Dialog.Title>
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
							</Transition.Child>
						</div>
					</Dialog>
				</div>
			</Transition>
		</div>
	)
}

export default LogoutForm