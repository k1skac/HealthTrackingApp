"use client"

import {React, useEffect, useState} from 'react'
import axios from "axios";
import MainPage from "@/app/components/MainPage";

export default function Home() {

	const PROFILE_COOKIE_CHECK_BASE_URL = 'http://localhost:8080/api/user/hello';

	const [hasCookie, setHasCookie] = useState(false);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		checkCookie();
	}, []);

	const checkCookie = async () => {
		try {
			const response = await axios.get(PROFILE_COOKIE_CHECK_BASE_URL, {withCredentials: true});
			setHasCookie(response.data);
			setLoading(false);
		} catch (error) {
			console.error('Error fetching user data:', error);
			setLoading(false);
		}
	};

	if (loading) {
		return <p>Loading...</p>;
	}

	if (!hasCookie) {
		window.location.href= "http://localhost:3000/login";
		return;
	}

	return (
		<div>
			<div>
				<MainPage/>
			</div>
		</div>
	)
}
