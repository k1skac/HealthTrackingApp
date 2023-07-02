import { React, useEffect, useState } from 'react';
import ChartDataLabels from 'chartjs-plugin-datalabels';

import {
    Chart as ChartJS,
    ArcElement, Chart,
} from 'chart.js';

ChartJS.register(
    ArcElement,
);

import { Doughnut} from 'react-chartjs-2';
import moment from 'moment';
import { format } from 'date-fns';
import 'chartjs-adapter-moment';
import axios from 'axios';

const MealCalorieDoughnutChart = () => {
    const PROFILE_MEALCALORIE_NAME_API_BASE_URL = 'http://localhost:8080/api/user/nutrition/nutrition-last';

    const [mealDatas, setMealDatas] = useState([]);
    const [loading, setLoading] = useState(true);
    Chart.register(ChartDataLabels);

    useEffect(() => {
        fetchMealData();
        setLoading(false);
    }, []);

    const fetchMealData = async () => {
        try {
            const response = await axios.get(
                PROFILE_MEALCALORIE_NAME_API_BASE_URL,

                {
                    withCredentials: true,
                }
            );
            setMealDatas(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };
    const dataCenter = [mealDatas.mealSumCalorie, mealDatas.mealSumCarbohydrate, mealDatas.mealSumFat, mealDatas.mealSumProtein];

    if (mealDatas.length === 0) {
        return <div className='w-full h-80 bg-zinc-100 rounded-sm align-middle' >No meal data available.</div>;
    }

    const data = {
        //plugins: [ChartDataLabels],

        labels: ["Carbohydrate", "Fat", "Protein"],
        datasets: [
            {
                data: [
                    Math.round(dataCenter[1] * 100) / 100,
                    Math.round(dataCenter[2] * 100) / 100,
                    Math.round(dataCenter[3] * 100) / 100,
                ],
                key: 'unique-key',
                backgroundColor: ["orange", "purple", "green"]
            },
            {
                data1: [Math.round(dataCenter[0] * 100) / 100], // Corrected syntax for rounding the value
                key: 'unique-key',
            }
        ],
        datalabels: {
            data:[dataCenter[1], dataCenter[2], dataCenter[3]],
        },
    };

    const options = {
        plugins: {
            datalabels: {
                backgroundColor: function (context) {
                    return context.dataset.backgroundColo;
                },
                color: 'black',
                fontsize: 4,
                display: function(context) {
                    const value = context.dataset.data;
                    return value;
                },

                labels: {
                    value: {
                        color: "#FFCE56"
                    },
                    font: {
                        weight: 'bold'
                    },
                    padding: 6,
                    formatter: Math.round
                },
            },
            title: {
                display: true,
                text: 'Your last meal content',
                font: {
                    size: 16,
                    weight: 'bold',
                },
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
            dataset: {
                display: true,
                color: 'black',
                font: {
                    size: 16,
                    weight: "bold",
                }
            },
        },
        elements: {
            arc: {weight: 1,
                borderWidth: 3,
            }
        },
        cutout: 20,
    };

    const textCenter = {
        id: 'textCenter',
        beforeDatasetsDraw: function (chart, args, pluginOptions) {
            const {ctx, data} = chart;
            ctx.save();
            ctx.font = 'bolder 20px sans-serif';
            ctx.fillStyle = 'black';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(data.datasets[1].data1, chart.getDatasetMeta(0).data[0].x, chart.getDatasetMeta(0).data[0].y);        },
    };

    return (

        <div>
            <div className='w-full h-80 bg-zinc-100 rounded-sm'>
                <Doughnut data={data} options={options} plugins={[textCenter]}/>
            </div>
        </div>

    );
}
export default MealCalorieDoughnutChart;