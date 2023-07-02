import { React, useEffect, useState } from 'react';
import axios from "axios";
import {Line} from "react-chartjs-2";

const WeightLineChart = () => {

const PROFILE_WEIGHT_NAME_API_BASE_URL =
    'http://localhost:8080/api/user/reports/weight/weight-lastSeven';

    const [weightDatas, setWeightDatas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchWeightData();
        setLoading(false);
    }, []);

    const fetchWeightData = async () => {
        try {
            const response = await axios.get(
                PROFILE_WEIGHT_NAME_API_BASE_URL,
                {
                    withCredentials: true,
                }
            );
            console.log(response.data);
            setWeightDatas(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    if (weightDatas.length === 0) {
        return <p className='w-full h-80 bg-zinc-100 rounded-sm align-middle'>No weight data available.</p>;
    }

    const dataSet = {
        labels: weightDatas.map((weight) => new Date(weight.weightMeasuredAt).toLocaleDateString()),
        datasets: [
            {
                data: weightDatas.map((weight) => weight.weight),
                key: 'unique-key', },
        ],
    };

    const options = {
        plugins: {
            title: {
                display: true,
                text: 'Your weight in a period',
                font: {
                    size: 16,
                    weight: 'bold',
                },
            },
            legend: {
                display: false,
            },
            datalabels: {
                backgroundColor: function (context) {
                    return context.dataset.backgroundColo;
                },
                color: 'black',
                fontsize: 2,
                anchor: "end",
                align: "bottom",
                offset: 10,
                display: function (context) {
                    const value = context.dataset.data;
                    return value;
                }
            },
        },
        elements: {
            line: {
                tension: 0,
                borderWidth: 4,
                borderColor: 'rgba(47,97,68,1)',
                fill: false,
                backgroundColor: 'rgba(0,0,0,1)',
            },
            point: {
                radius: 5,
                hitRadius: 15,
                pointBackgroundColor: 'rgba(47,97,68,1)',
            },
        },
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'day',
                },
                grid: {
                    display: true, // Hide the grid lines on the x-axis
                },
                ticks: {
                    display: true,
                    autoSkip: false,
                    callback: (value, index, values) => {
                        return new Date(value).toLocaleDateString();
                    },
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
        },
    };

    return (
              <div className='w-[500px] h-80 bg-zinc-100 rounded-sm'>
                <Line data={dataSet} options={options} />
            </div>
    )}
export  default WeightLineChart;