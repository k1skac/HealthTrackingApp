'use client';
import React from 'react'
import Donut from '../components/Donut'

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    TimeScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler,
} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    TimeScale,
    PointElement,
    LineElement,
    Filler,
    Title,
    Tooltip,
    Legend
);

import 'chartjs-adapter-moment';

const page = () => {
	return (
		<div>
		<Donut />
		</div>
	)
}

export default page