import { React, useState, useEffect } from 'react';
import ProfilePageService from '../service/ProfilePageService';
import ProfilePage from './ProfilePage';
import AddVitalHealthSigns from './AddVitalHealthSigns';
import Logout from './LogoutForm';
import AddCalorieIntake from './AddCalorieIntake';
import AddMeal from './AddMeal';
import {Menu} from "@headlessui/react";
import DropdownLink from "@/app/components/DropdownLink";

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
                    <AddVitalHealthSigns />
                </div>
                <div className='mx-4'>
                    <AddMeal />
                </div>
                 <div className='mx-4'>
                     <AddCalorieIntake/>
                </div>
            </span>
            <span className="mx-4 px-4 flex justify-end items-center">
                    <Menu as="div" className="relative inline-block">
                        <Menu.Button className="text-white font-bold">
                            {userDTO.realName}
                        </Menu.Button>
                        <Menu.Items className=" text-white text-center right-0 absolute w-40 origin-top-right bg-htadarkteal hover:bg-htadarktealhover shadow-lg rounded">
                            <Menu.Item>
                                <DropdownLink
                                    className="dropdown-link"
                                    href="/profile-page">
                                    Update Profile
                                </DropdownLink>
                            </Menu.Item>
                            <Menu.Item>
                                <DropdownLink
                                    className="dropdown-link"
                                    href="/logout">
                                    Logout
                                </DropdownLink>
                            </Menu.Item>
                        </Menu.Items>
                    </Menu>
            </span>
        </div>
    );
};

export default Navbar;