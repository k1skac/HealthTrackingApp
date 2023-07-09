import { React, useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import axios from 'axios';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { Chart as ChartJS, ArcElement } from 'chart.js';

ChartJS.register(ArcElement);

const MealCalorieDoughnutChart = () => {
  const [mealData, setMealData] = useState({});
  const [loading, setLoading] = useState(true);
  ChartJS.register(ChartDataLabels);

  useEffect(() => {
    const interval = setInterval(fetchMealData, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  const fetchMealData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/user/nutrition/nutrition-dynamic-load', {
        withCredentials: true,
      });
      setMealData(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const dataCenter = [mealData.mealSumCalorie, mealData.mealSumCarbohydrate, mealData.mealSumFat, mealData.mealSumProtein];

  const data = {
    labels: ['Carbohydrate', 'Fat', 'Protein'],
    datasets: [
      {
        data: [
          Math.round(dataCenter[1] * 100) / 100,
          Math.round(dataCenter[2] * 100) / 100,
          Math.round(dataCenter[3] * 100) / 100,
        ],
        backgroundColor: ['orange', 'purple', 'green'],
      },
    ],
    datalabels: {
      color: 'red',
      display: true,
      formatter: (value, ctx) => {
        return `${value.toFixed(1)} g`;
      },
    },
  };

  const options = {
    plugins: {
      title: {
        display: true,
        text: 'Meal content',
        font: {
          size: 16,
          weight: 'bold',
          color: 'black',
        },
        color: 'black',
      },
      legend: {
        display: true,
        position: 'top',
        labels: {
          boxWidth: 7,
          usePointStyle: true,
          pointStyle: 'circle',
          color: 'black',
        },
      },
      datalabels: {
        formatter: (value, ctx) => {
          return `${value.toFixed(1)} g`;
        },
      },
    },
    elements: {
      arc: {
        borderWidth: 3,
        backgroundColor: 'white',
      },
    },
    cutout: 60,
  };

  return (
    <div>
      {loading ? (
        <p>Loading...</p>
      ) : (
          <div className="h-96 bg-htamediumteal rounded-sm chart-container">
          <Doughnut data={data} options={{ ...options, plugins: { ...options.plugins, datalabels: { ...options.plugins.datalabels, color: 'black', font: { weight: 'bold', size: 14 } } } }} />
          </div>
      )}
    </div>
  );
};

export default MealCalorieDoughnutChart;