'use client'

import {React, useEffect, useState,Fragment} from 'react'
import axios from "axios";
import {Dialog, Transition} from "@headlessui/react";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import RegisterService from "@/app/service/RegisterService";


const RegisterForm = () => {
    const [registerDTO, setRegisterDTO] = useState({
        username : '',
        email: '',
        password:'',
        realName:'',
        biologicalGender:'',
        birthdate:'',
        height:0.0,
    });
    const [loading, setLoading] = useState(true);
    const [cityOptions, setCityOptions] = useState([]);
    const [isOpen, setIsOpen] = useState(false);
    const [validationErrors, setValidationErrors] = useState({});
    const [validEmail, setValidEmail] = useState(true);
    const maxDate = new Date();

    const PROFILE_CITY_NAME_API_BASE_URL = 'http://localhost:8080/api/user/get-cityname-list';
    const PROFILE_EMAIL_VALID_API_BASE_URL = 'http://localhost:8080/api/user/check-email-availability';

    useEffect(() => {
        fetchCityOptions();
        setLoading(false);
    }, []);

    const fetchCityOptions = async () => {
        try {
            const response = await axios.get(PROFILE_CITY_NAME_API_BASE_URL, { withCredentials: true });
            const options = response.data;
            setCityOptions(options);
        } catch (error) {
            console.error('Error fetching city options:', error);
        }
    };

    const checkEmailAvailability = async (email) => {
        try {
            const response = await axios.get(PROFILE_EMAIL_VALID_API_BASE_URL, {
                params: { email: email }
            });
            return response.data;
        } catch (error) {
            console.error('Error checking email availability:', error);
            return false;
        }
    };

    const handleEmailChange = async (event) => {
        const email = event.target.value;
        setRegisterDTO((prevRegisterDTO) => ({
            ...prevRegisterDTO,
            email: email,
        }));
        const isNotAvailable = await checkEmailAvailability(email);
        setValidEmail(isNotAvailable);
    };
    const registerUserProfile = (e) => {
        e.preventDefault();
    if (validateForm()) {
        RegisterService.saveRegisterProfile(registerDTO)
            .then((response) => {
                console.log(response.status);
                closeModal();
                window.location.href= "http://localhost:3000/"
            })
            .catch((error) => {
                console.log(error);
            });
    }
};

const validateForm = () => {
    const errors = {};
    let isValid = true;

    if (!registerDTO.username) {
        errors.username = 'Username is required';
        isValid = false;
    }

    if (!registerDTO.realName) {
        errors.realName = 'Real name is required';
        isValid = false;
    }

    if (!registerDTO.email) {
        errors.email = 'Email is required';
        isValid = false;
    } else if (!/\S+@\S+\.\S+/.test(registerDTO.email)) {
        errors.email = 'Email is invalid';
        isValid = false;
    } else if (validEmail) {
            errors.email = 'Email has been taken';
            isValid = false;
    }
    if (!registerDTO.password) {
        errors.password = 'Password is required';
        isValid = false;
    } else if (registerDTO.password.length < 8) {
        errors.password = 'Password must be at least 8 characters long';
        isValid = false;
    }else if (!/[a-z]/.test(registerDTO.password) || !/[A-Z]/.test(registerDTO.password) || !/\d/.test(registerDTO.password)) {
        errors.password = 'Password must contain both uppercase and lowercase characters, and at least one number';
        isValid = false;
    }

    if (!registerDTO.biologicalGender) {
        errors.biologicalGender = 'Gender is required';
        isValid = false;
    }

    if (registerDTO.birthdate=== new Date()) {
        errors.biologicalGender = 'Gender is required';
        isValid = false;
    }

    if (!registerDTO.cityName) {
        errors.cityName = 'City name is required';
        isValid = false;
    }

    if (registerDTO.height===0.0) {
        errors.height = 'Height is required';
        isValid = false;
    }

    setValidationErrors(errors);
    return isValid;
};

    function closeModal() {
        setIsOpen(false);
    }

    function openModal() {
        setIsOpen(true);
    }

    const handleChange = (e) => {
        e.preventDefault();
        const value = e.target.value;
        setRegisterDTO({...registerDTO, [e.target.name]: value});
    }

    const handleChangeDate = (date) => {
        setRegisterDTO((prevRegisterDTO) => ({
            ...prevRegisterDTO,
            birthDate: date ? date.toISOString().split('T')[0] : '',
        }));
    };



    const resetForm = () => {
        setRegisterDTO({
            username: '',
            email: '',
            password: '',
            realName: '',
            biologicalGender: '',
            birthdate: '',
            height: 0.0,
        });
        setValidationErrors({});
        setValidEmail(true);
    };


    return (
        <div>
            <button
                onClick={openModal}
                className='inline-block text-sm font-normal'>
                Register
            </button>

            <Transition appear show={isOpen} as={Fragment}>
                <div  >
                    <Dialog
                        as="div"
                        className='overflow-y-auto fixed inset-24 max-w-screen-md h-max mx-auto'
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
                                <div className="max-h-screen">
                                    <Dialog.Title
                                        as='div'
                                        className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'>
                                            <h3
                                                className='text-lg font-bold text-white m-auto'>
                                                Please add your profile data
                                            </h3>
                                    </Dialog.Title>
                                    <div className="bg-htamediumteal rounded-md shadow-slate-900 shadow-md">
                                        <div>
                                            {loading && (
                                                <div
                                                    className='text-white'>
                                                    Loading
                                                </div>
                                            )}
                                            {!loading && (
                                                <div className='m-auto max-w-md text-white'>
                                                    <div className="overflow-y-auto h-96 ">
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Username
                                                            </label>
                                                            <input
                                                                type="text"
                                                                name="username"
                                                                required
                                                                value={registerDTO.username}
                                                                onChange={(event) => handleChange(event)}
                                                                className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                            {validationErrors.username && (
                                                                <p className='text-yellow-500'>{validationErrors.username}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Real name
                                                            </label>
                                                            <input
                                                                type="text"
                                                                name="realName"
                                                                required
                                                                value={registerDTO.realName}
                                                                onChange={(event) => handleChange(event)}
                                                                className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                            {validationErrors.realName && (
                                                                <p className='text-yellow-500'>{validationErrors.realName}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Email
                                                            </label>
                                                            <input
                                                                type="email"
                                                                name="email"
                                                                required
                                                                value={registerDTO.email}
                                                                onChange={(event) => handleEmailChange(event)}
                                                                className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                            {validationErrors.email && (
                                                                <p className='text-yellow-500'>{validationErrors.email}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Password
                                                            </label>
                                                            <input
                                                                type="text"
                                                                name="password"
                                                                required
                                                                value={registerDTO.password}
                                                                onChange={(event) => handleChange(event)}
                                                                className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                            {validationErrors.password && (
                                                                <p className='text-yellow-500'>{validationErrors.password}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label className='block py-2'>Gender</label>
                                                            <select
                                                                onChange={(event) => handleChange(event)}
                                                                name="biologicalGender"
                                                                value={registerDTO.biologicalGender}
                                                                defaultValue={""}
                                                                required
                                                                className="rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm">
                                                                <option value="" disabled>Select an option</option>
                                                                <option value="Male">Male</option>
                                                                <option value="Female">Female</option>
                                                                <option value="Undefined">Undefined</option>
                                                            </select>
                                                            {validationErrors.biologicalGender && (
                                                                <p className='text-yellow-500'>{validationErrors.biologicalGender}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Birth date
                                                            </label>
                                                            <DatePicker
                                                                name="birthDate"
                                                                selected={registerDTO.birthDate ? new Date(registerDTO.birthDate) : new Date()}
                                                                onChange={handleChangeDate}
                                                                className='rounded-sm h-8 w-96 border px-2 py-2 text-black shadow-slate-900 shadow-sm'
                                                                dateFormat="yyyy.MM.dd"
                                                                maxDate={maxDate}
                                                            />
                                                            {validationErrors.birthDate && (
                                                                <p className='text-yellow-500'>{validationErrors.birthDate}</p>
                                                            )}
                                                        </div>
                                                        <div className='pt-2'>
                                                            <label
                                                                className='block py-2'>
                                                                City name
                                                            </label>
                                                            <select
                                                                onChange={(event) => handleChange(event)}
                                                                name="cityName"
                                                                value={registerDTO.cityName}
                                                                required
                                                                className="rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm">
                                                                <option className="text-black" value={registerDTO.city} placeholder='Select a city'></option>
                                                                {cityOptions.map((city) =>
                                                                    <option className="text-black"  >
                                                                        {city}
                                                                    </option>
                                                                )}
                                                            </select>
                                                            {validationErrors.cityName && (
                                                                <p className='text-yellow-500'>{validationErrors.cityName}</p>
                                                            )}
                                                        </div>
                                                        <div className='p-2'>
                                                            <label
                                                                className='block py-2'>
                                                                Height
                                                            </label>
                                                            <input
                                                                type="number"
                                                                step="0.01"
                                                                min="20"
                                                                name="height"
                                                                value={registerDTO.height}
                                                                required
                                                                onChange={(event) => handleChange(event)}
                                                                className='h-8 w-96 border px-2 py2 text-black shadow-slate-900 shadow-sm'/>
                                                            {validationErrors.height && (
                                                                <p className='text-yellow-500'>{validationErrors.height}</p>
                                                            )}
                                                        </div>
                                                    </div>

                                                    <div className='flex justify-center pt-2 pb-4'>
                                                        <div className='h-14 my-4 pt-4'>
                                                            <button
                                                                onClick={registerUserProfile}
                                                                className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                                Register
                                                            </button>
                                                        </div>
                                                        <div className='h-14 my-4 pt-4'>
                                                            <button
                                                                onClick={resetForm}
                                                                className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                                Reset
                                                            </button>
                                                        </div>
                                                        <div className='h-14 my-4 pt-4'>
                                                            <button
                                                                onClick={closeModal} //Added function
                                                                className='mx-2 rounded text-white font-semibold bg-red-500 hover:bg-red-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                                Close
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            )}
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


export default RegisterForm;