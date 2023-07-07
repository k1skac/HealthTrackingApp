import {React, useEffect, useState} from 'react';
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
import {Alert} from "@/app/components/Alert";
import {alertService, AlertType} from "@/app/service/AlertService";
import MainService from "@/app/service/MainService";

const MainPage = () => {

    const [loading, setLoading] = useState(true);
    const [allNull, setAllNull] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const weightData = await MainService.fetchWeightData();
            const blpData = await MainService.fetchBlpData();
            const mealData = await MainService.fetchMealData();

            if (!weightData.length && !blpData.length && mealData=== undefined) {
                setAllNull(true);
            } else {
                setAllNull(false);
            }
            setLoading(false);
        };

        fetchData();
    }, [loading]);


    return (
        <div className="bg-htadarkgrey h-screen overflow-auto">
            <Navbar />
            <div className="relative">
                <div className="absolute top-0 right-0 mt-2 mr-2">
                    <Logo />
                </div>
            </div >
            <div>
               <Alert filterType={AlertType.Info}/>
            </div>
            {allNull ? (
                <div className="flex justify-center items-center h-screen">
                    <h1 className="text-4xl font-bold text-center">
            <span className="inline-block animate-spin-slow text-yellow-300">
              W
            </span>
                        <span className="inline-block animate-spin-fast text-blue-400">
              e
            </span>
                        <span className="inline-block animate-spin-slow text-green-500">
              l
            </span>
                        <span className="inline-block animate-spin-fast text-pink-600">
              c
            </span>
                        <span className="inline-block animate-spin-slow text-purple-700">
              o
            </span>
                        <span className="inline-block animate-spin-fast text-red-800">
              m
            </span>
                        <span className="inline-block animate-spin-slow text-indigo-900">
              e
            </span>
                        <span className="inline-block animate-spin-fast text-yellow-300">
              !
            </span>
                        <p>
                         Let's get started!
                        </p>
                    </h1>
                </div>
            ) : (
                <div className="mt-48">
                    <div className="flex justify-evenly">
                        <div className="shadow-md shadow-black">
                            <BloodPressureBar />
                        </div>
                        <div className="shadow-md shadow-black">
                            <WeightLineChart />
                        </div>
                        <div className="shadow-md shadow-black">
                            <MealCalorieDoughnutChart />
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default MainPage;