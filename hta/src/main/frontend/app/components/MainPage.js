import { React } from 'react';
import Logo from './Logo';
import BloodPressureBar from "@/app/components/BloodPressureBar";
import WeightLineChart from "@/app/components/WeightLineChart";
import MealCalorieDoughnutChart from "@/app/components/MealCalorieDoughnutChart";

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
import Navbar from './Navbar';

const MainPage = () => {


    return (
        <div className="bg-htadarkgrey h-screen overflow-auto">
            <Navbar />
            <div className="relative">
                <div className="absolute top-0 right-0 mt-2 mr-2">
                    <Logo />
                </div>
            </div>
            <div className='mt-48'>
                <div className='flex justify-evenly'>
                    <div className='shadow-md shadow-black'>
                        <BloodPressureBar />
                    </div>
                    <div className='shadow-md shadow-black'>
                        <WeightLineChart />
                    </div>
                    <div className='shadow-md shadow-black'>
                        <MealCalorieDoughnutChart />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MainPage;