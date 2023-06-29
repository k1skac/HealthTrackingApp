import React, { useState } from 'react';
import CalorieIntakeService from '@/app/service/CalorieIntakeService';

const AddCalorieIntake = () => {
  const [foodStuff, setFoodStuff] = useState({
    name: '',
    caloriePer100g: '',
    fatPer100g: '',
    carbohydratePer100g: '',
    proteinPer100g: '',
  });

  const [readyFood, setReadyFood] = useState({
    name: '',
    caloriesPerPortion: '',
    fatPerPortion: '',
    carbohydratePerPortion: '',
    proteinPerPortion: '',
  });

  const handleFoodStuffChange = (e) => {
    const { name, value } = e.target;
    setFoodStuff((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleReadyFoodChange = (e) => {
    const { name, value } = e.target;
    setReadyFood((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const saveFoodStuff = (e) => {
    e.preventDefault();
    CalorieIntakeService.saveFoodStuff(foodStuff)
      .then((response) => {
        console.log(response.data);
        setFoodStuff({
          name: '',
          caloriePer100g: '',
          fatPer100g: '',
          carbohydratePer100g: '',
          proteinPer100g: '',
        });
      })
      .catch((error) => {
        console.log('Food Stuff intake cannot be added!');
        console.log(error.response);
      });
  };

  const saveReadyFood = (e) => {
    e.preventDefault();
    CalorieIntakeService.saveReadyFood(readyFood)
      .then((response) => {
        console.log(response.data);
        setReadyFood({
          name: '',
          caloriesPerPortion: '',
          fatPerPortion: '',
          carbohydratePerPortion: '',
          proteinPerPortion: '',
        });
      })
      .catch((error) => {
        console.log('Ready Food intake cannot be added!');
        console.log(error.response);
      });
  };

  const resetFoodStuff = (e) => {
    e.preventDefault();
    setFoodStuff({
      name: '',
      caloriePer100g: '',
      fatPer100g: '',
      carbohydratePer100g: '',
      proteinPer100g: '',
    });
  };

  const resetReadyFood = (e) => {
    e.preventDefault();
    setReadyFood({
      name: '',
      caloriesPerPortion: '',
      fatPerPortion: '',
      carbohydratePerPortion: '',
      proteinPerPortion: '',
    });
  };

  

  return (<>
  			<div>
				<a href="http://localhost:3000/testfile">Testing dynamic content</a>
			</div>
    <div className="m-auto w-full max-w-screen-md p-6 my-12 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-md">
      <div>
        <h1 className="rounded w-full my-8 text-center bg-sky-800 text-white py-2 px-6 font-bold capitalize">Add Calorie Intake</h1>
      </div>
      <form className="flex flex-col gap-4">
        <div className="flex flex-row">
          <hr className="w-1/4 mr-4 mt-2 border-gray-300" />
          <h2 className="text-gray-800 text-lg font-semibold">Food Stuff</h2>
          <hr className="w-1/4 ml-4 mt-2 border-gray-300" />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Food Name:</label>
          <input
            type="text"
            name="name"
            placeholder="Food Name"
            value={foodStuff.name}
            onChange={(e) => handleFoodStuffChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Calories per 100g:</label>
          <input
            type="number"
            step="0.01"
            name="caloriePer100g"
            placeholder="...cal"
            value={foodStuff.caloriePer100g}
            onChange={(e) => handleFoodStuffChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Fat per 100g:</label>
          <input
            type="number"
            step="0.01"
            name="fatPer100g"
            placeholder="...g"
            value={foodStuff.fatPer100g}
            onChange={(e) => handleFoodStuffChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Carbohydrate per 100g:</label>
          <input
            type="number"
            step="0.01"
            name="carbohydratePer100g"
            placeholder="...g"
            value={foodStuff.carbohydratePer100g}
            onChange={(e) => handleFoodStuffChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Protein per 100g:</label>
          <input
            type="number"
            step="0.01"
            name="proteinPer100g"
            placeholder="...g"
            value={foodStuff.proteinPer100g}
            onChange={(e) => handleFoodStuffChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>

        <div className="mt-8 flex justify-end">
          <button
            type="button"
            onClick={(e) => resetFoodStuff(e)}
            className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-4"
          >
            Reset Food Stuff
          </button>
          <button
            type="submit"
            onClick={(e) => saveFoodStuff(e)}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            Save Food Stuff
          </button>
        </div>

        <div className="flex flex-row">
          <hr className="w-1/4 mr-4 mt-2 border-gray-300" />
          <h2 className="text-gray-800 text-lg font-semibold">Ready Food</h2>
          <hr className="w-1/4 ml-4 mt-2 border-gray-300" />
        </div>
        <div className="flex flex-row">
          <label className="text-gray-800 text-sm font-normal">Food Name:</label>
          <input
            type="text"
            name="name"
            placeholder="Food Name"
            value={readyFood.name}
            onChange={(e) => handleReadyFoodChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
          />
        </div>
        <div className="flex flex-row">
        <label className="text-gray-800 text-sm font-normal">Calories per Portion:</label>
        <input
            type="number"
            step="0.01"
            name="caloriesPerPortion"
            placeholder="...cal"
            value={readyFood.caloriesPerPortion}
            onChange={(e) => handleReadyFoodChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
        />
        </div>
        <div className="flex flex-row">
        <label className="text-gray-800 text-sm font-normal">Fat per Portion:</label>
        <input
            type="number"
            step="0.01"
            name="fatPerPortion"
            placeholder="...g"
            value={readyFood.fatPerPortion}
            onChange={(e) => handleReadyFoodChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
        />
        </div>
        <div className="flex flex-row">
        <label className="text-gray-800 text-sm font-normal">Carbohydrate per Portion:</label>
        <input
            type="number"
            step="0.01"
            name="carbohydratePerPortion"
            placeholder="...g"
            value={readyFood.carbohydratePerPortion}
            onChange={(e) => handleReadyFoodChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
        />
        </div>
        <div className="flex flex-row">
        <label className="text-gray-800 text-sm font-normal">Protein per Portion:</label>
        <input
            type="number"
            step="0.01"
            name="proteinPerPortion"
            placeholder="...g"
            value={readyFood.proteinPerPortion}
            onChange={(e) => handleReadyFoodChange(e)}
            className="h-10 border px-2 py-1 flex items-center"
        />
        </div>
        <div className="mt-8 flex justify-end">
          <button
            type="button"
            onClick={(e) => resetReadyFood(e)}
            className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-4"
          >
            Reset Ready Food
          </button>
          <button
            type="submit"
            onClick={(e) => saveReadyFood(e)}
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            Save Ready Food
          </button>
        </div>
      </form>
    </div>
    </>
  );
};

export default AddCalorieIntake;