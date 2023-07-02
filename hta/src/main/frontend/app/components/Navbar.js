import { React, useState, useEffect } from 'react';
import ProfilePageService from '../service/ProfilePageService';
import ProfilePage from './ProfilePage';
import AddVitalHealthSigns from './AddVitalHealthSigns';
import Logout from './LogoutForm';

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
                <div className='mx-4'>
                    <ProfilePage />
                </div>
                <div className='mx-4'>
                    <AddVitalHealthSigns />
                </div>
                <div className='mx-4'>
                    <Logout />
                </div>
            </span>
            <span className="mx-4 px-8 flex justify-end items-center">
                <p className="text-white font-bold">{userDTO.realName}</p>
            </span>
        </div>
    );
};

export default Navbar;