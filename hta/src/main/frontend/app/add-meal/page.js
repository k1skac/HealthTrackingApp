'use client'
import React from 'react';
import AddMeal from '../components/AddMeal';

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

const Page = () => {
  return (
    <div className="grid grid-cols-5 gap-4">
      <div className="col-span-3">
        <AddMeal />
      </div>
    </div>
  );
};

export default Page;