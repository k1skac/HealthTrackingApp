import { React, useEffect, useState } from 'react';

import {
    Chart as ChartJS,
    BarElement
} from 'chart.js';

ChartJS.register(
    BarElement,
);

import {Bar} from 'react-chartjs-2';
import 'chartjs-adapter-moment';
import axios from 'axios';


const BloodPressureBar = () => {
    const PROFILE_BLOODPRESSURE_NAME_API_BASE_URL =
        'http://localhost:8080/api/user/reports/blp/bloodpressure-lastSeven';

    const [blpDatas, setBlpDatas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchBlpData();
        setLoading(false);
    }, []);

    const fetchBlpData = async () => {
        try {
            const response = await axios.get(
                PROFILE_BLOODPRESSURE_NAME_API_BASE_URL,

                {
                    withCredentials: true,
                }
            );
            setBlpDatas(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    if (blpDatas.length === 0) {
        return <p className='w-full h-80 bg-zinc-100 rounded-sm align-middle'>No blood pressure data available.</p>;
    }

    const data = {
        labels: blpDatas.map((blp) => new Date(blp.bloodPressureMeasuredAt)
            .toLocaleDateString() + " " + new Date(blp.bloodPressureMeasuredAt)
            .toLocaleTimeString()),
        datasets: [
            {
                label: "systolic",
                data: blpDatas.map((blp) => blp.systolic),
                key: 'unique-key',
                backgroundColor: "orange",
            },
            {
                label: "diastolic",
                data: blpDatas.map((blp) => blp.diastolic),
                key: 'unique-key',
                backgroundColor: "purple",
            },
        ],
    };

    const options = {
        plugins: {
            title: {
                display: true,
                text: 'Your blood pressure in a period',
                font: {
                    size: 16,
                    weight: 'bold',
                },
                datalabels: {
                    backgroundColor: function (context) {
                        return context.dataset.backgroundColo;
                    },
                    color: 'green',
                    fontsize: 4,
                    display: function (context) {
                        const value = context.dataset.data;
                        return value;
                    }
                }
            },
            legend: {
                display: true,
                position: "top",
                labels: {
                    boxWidth: 7,
                    usePointStyle: true,
                    pointStyle: "circle"
                }
            },
        },
        elements: {
            bar: {barPercentage: 0.3,
            categoryPercentage: 1,
            }
        },
        scales: {

            x: {
                    display: true,
                },
            },
            y: {
                type: 'linear',
                display: true,
                beginAtZero: true,
                grid: {
                    display: true,
                },
            },
    };


    return (
        <div>
            <div className='w-[500px] h-80 bg-white rounded-sm border-4 border-htamediumteal'>
                <Bar data={data} options={options} />
            </div>
        </div>

    );
}
export default BloodPressureBar;
