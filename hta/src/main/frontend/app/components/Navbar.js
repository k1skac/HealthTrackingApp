import { React, useState, useEffect } from 'react';
import ProfilePageService from '../service/ProfilePageService';
import ProfilePage from './ProfilePage';
import AddVitalHealthSigns from './AddVitalHealthSigns';
import Logout from './LogoutForm';
import AddCalorieIntake from './AddCalorieIntake';
import AddMeal from './AddMeal';

const Navbar = () => {
    const [userDTO, setUserDTO] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchData();
        setLoading(false);
    }, []);

    const fetchData = async () => {
        try {
            const response = await ProfilePageService.getUserData();
            setUserDTO(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    return (
        <div className="flex justify-between h-20 bg-htamediumteal shadow-md shadow-teal-950">
            <span className="px-8 flex justify-start items-center">
                <p className="text-white font-bold mx-4">Health Tracking Application</p>
                <div className='mx-2'>
                    <ProfilePage />
                </div>
                <div className='mx-2'>
                    <AddVitalHealthSigns />
                </div>
                <div className='mx-2'>
                     <AddCalorieIntake/>
                </div>
                <div className='mx-2'>
                     <AddMeal/>
                </div>
                <div className='mx-2'>
                    <Logout />
                </div>
            </span>
            <span className="mx-2 px-6 flex justify-end items-center">
                <p className="text-white font-bold">{userDTO.realName}</p>
            </span>
        </div>
    );
};

export default Navbar;