'use client'

import ProfilePageService from "@/app/service/ProfilePageService";
import {React, useEffect, useState,Fragment} from 'react'
import axios from "axios";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const PROFILE_UPDATE_API_BASE_URL = 'http://localhost:8080/api/user/update-user-profile';
const PROFILE_CITY_NAME_API_BASE_URL = 'http://localhost:8080/api/user/get-cityname-list';

const ProfilePage = () => {
    const [userDTO, setUserDTO] = useState({});
    const [loading, setLoading] = useState(true);
    const [cityOptions, setCityOptions] = useState([]);
    const [isOpen, setIsOpen] = useState(false);
    const maxDate = new Date();

    useEffect(() => {
        fetchCityOptions();
        fetchData();
        setLoading(false);
    }, []);


    const fetchData = async () => {
        try {
            const response = await axios.get(PROFILE_UPDATE_API_BASE_URL, { withCredentials: true });
            setUserDTO(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    const fetchCityOptions = async () => {
        try {
            const response = await axios.get(PROFILE_CITY_NAME_API_BASE_URL, { withCredentials: true });
            const options = response.data;
            setCityOptions(options);
        } catch (error) {
            console.error('Error fetching city options:', error);
        }
    };

    const updateUserProfile = (e) => {
        e.preventDefault();
        ProfilePageService.saveUserProfile(userDTO)
            .then((response) => {
                console.log(response.status);
                closeModal();
            }).catch((error) => {
            console.log(error);
            });
    }

    function closeModal() {
        setIsOpen(false);
        window.location.href = "/";
    }

    function openModal() {
        setIsOpen(true);
    }

    const handleChange = (e) => {
        e.preventDefault();
        const value = e.target.value;
        setUserDTO({...userDTO, [e.target.name]: value});
    }

    const handleChangeDate = (date) => {
        if (date instanceof Date && !isNaN(date)) {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const formattedDate = `${year}-${month}-${day}`;
            setUserDTO((prevUserDTO) => ({
                ...prevUserDTO,
                birthDate: formattedDate,
            }));
        } else {
            console.error('Invalid date:', date);
        }
    };

    const resetForm = async () => {
        try {
            await fetchData();
        } catch (error) {
            console.error('Error resetting form:', error);
        }
    };

    return (
                <div className='m-auto w-full max-w-screen-md text-left align-middle transition-all transform shadow-xl rounded-md' >
                    <div className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'>
                    <h3
                        className='text-lg font-bold text-white m-auto text-center'>
                        Edit your profile data
                    </h3>
                    </div>
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
                                                    <div className='pt-4'>
                                                        <label
                                                            className='block py-2'>
                                                            Username
                                                        </label>
                                                        <input
                                                            type="text"
                                                            name="userName"
                                                            readOnly="true"
                                                            value={userDTO.username}
                                                            onChange={(event) => handleChange(event)}
                                                            className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                    </div>
                                                    <div className='pt-2'>
                                                        <label
                                                            className='block py-2'>
                                                            Real name
                                                        </label>
                                                        <input
                                                            type="text"
                                                            name="realName"
                                                            value={userDTO.realName}
                                                            onChange={(event) => handleChange(event)}
                                                            className='rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm'/>
                                                    </div>
                                                    <div className='pt-2'>
                                                        <label
                                                            className='block py-2'>
                                                            Gender
                                                        </label>
                                                        <select
                                                            onChange={(event) => handleChange(event)}
                                                            name="biologicalGender"
                                                            value={userDTO.biologicalGender}
                                                            className="rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm">
                                                            <option value="Male">Male</option>
                                                            <option value="Female">Female</option>
                                                            <option value="Undefined">Undefined</option>
                                                        </select>
                                                    </div>
                                                    <div className='pt-2'>
                                                        <label
                                                            className='block py-2'>
                                                            Birth date
                                                        </label>
                                                        <DatePicker
                                                            name="birthDate"
                                                            selected={userDTO.birthDate ? new Date(userDTO.birthDate) : null}
                                                            onChange={handleChangeDate}
                                                            className='rounded-sm h-8 w-96 border px-2 py-2 text-black shadow-slate-900 shadow-sm'
                                                            dateFormat="yyyy.MM.dd"
                                                            maxDate={maxDate}
                                                        />
                                                    </div>
                                                    <div className='pt-2'>
                                                        <label
                                                            className='block py-2'>
                                                            City name
                                                        </label>
                                                        <select
                                                            onChange={(event) => handleChange(event)}
                                                            name="cityName"
                                                            value={userDTO.cityName}
                                                            className="rounded-sm h-8 w-96 text-black shadow-slate-900 shadow-sm">
                                                            <option className="text-black" placeholder='Select a city'></option>
                                                            {cityOptions.map((city) =>
                                                                <option className="text-black" key={city}  >
                                                                    {city}
                                                                </option>
                                                            )}
                                                        </select>
                                                    </div>
                                                    <div className='p-2'>
                                                        <label
                                                            className='block py-2'>
                                                            Height
                                                        </label>
                                                        <input
                                                            type="number"
                                                            step="0.01"
                                                            name="height"
                                                            value={userDTO.height}
                                                            onChange={(event) => handleChange(event)}
                                                            className='h-8 w-96 border px-2 py2 text-black shadow-slate-900 shadow-sm'/>
                                                    </div>
                                                    <div className='flex justify-center pt-2 pb-4'>
                                                        <div className='h-14 my-4 pt-4'>
                                                            <button
                                                                onClick={updateUserProfile}
                                                                className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'>
                                                                Update
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
    )
}


export default ProfilePage;