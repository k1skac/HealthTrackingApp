import React, { useState, useEffect } from 'react';
import CalorieIntakeService from '@/app/service/CalorieIntakeService';

const AddMeal = () => {
  const [mealTime, setMealTime] = useState('');
  const [foodstuffs, setFoodstuffs] = useState([{ foodstuff: '', quantity: '' }]);
  const [readyFoods, setReadyFoods] = useState([{ readyFood: '', quantity: '' }]);
  const [readyFoodOptions, setReadyFoodOptions] = useState([]);
  const [foodStuffOptions, setFoodStuffOptions] = useState([]);

  useEffect(() => {
    fetchReadyFoodOptions();
  }, []);

  useEffect(() => {
    fetchFoodStuffOptions();
  }, []);

  const fetchReadyFoodOptions = () => {
    CalorieIntakeService.getFoodTypesAsString()
      .then(response => {
        setReadyFoodOptions(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const fetchFoodStuffOptions = () => {
    CalorieIntakeService.getFoodStuffTypesAsString()
      .then(response => {
        setFoodStuffOptions(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const handleFoodstuffChange = (index, e) => {
    const { name, value } = e.target;
    const updatedFoodstuffs = [...foodstuffs];
    updatedFoodstuffs[index] = { ...updatedFoodstuffs[index], [name]: value };
    setFoodstuffs(updatedFoodstuffs);
  };

  const handleReadyFoodChange = (index, e) => {
    const { name, value } = e.target;
    const updatedReadyFoods = [...readyFoods];
    updatedReadyFoods[index] = { ...updatedReadyFoods[index], [name]: value };
    setReadyFoods(updatedReadyFoods);
  };

  const handleAddFoodstuff = () => {
    setFoodstuffs([...foodstuffs, { foodstuff: '', quantity: '' }]);
  };

  const handleRemoveFoodstuff = (index) => {
    const updatedFoodstuffs = [...foodstuffs];
    updatedFoodstuffs.splice(index, 1);
    setFoodstuffs(updatedFoodstuffs);
  };

  const handleAddReadyFood = () => {
    setReadyFoods([...readyFoods, { readyFood: '', quantity: '' }]);
  };

  const handleRemoveReadyFood = (index) => {
    const updatedReadyFoods = [...readyFoods];
    updatedReadyFoods.splice(index, 1);
    setReadyFoods(updatedReadyFoods);
  };

  const handleSaveMeal = () => {
    const foodstuffsList = {};
    const readyFoodsList = {};

    foodstuffs.forEach((item) => {
      if (item.foodstuff && item.quantity) {
        foodstuffsList[item.foodstuff] = item.quantity !== '' ? parseFloat(item.quantity) : 0;
      }
    });

    readyFoods.forEach((item) => {
      if (item.readyFood && item.quantity) {
        readyFoodsList[item.readyFood] = item.quantity !== '' ? parseFloat(item.quantity) : 0;
      }
    });

    const mealData = {
      mealTime: mealTime || '',
      foodstuffsList,
      readyFoodsList,
    };

    CalorieIntakeService.saveMeal(mealData)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  return (
    <div className="p-4">
      <div className="mb-4">
        <h2 className="text-lg font-semibold">Add Meal</h2>
        <label className="block mt-2">Meal Time:</label>
        <input
          type="datetime-local"
          value={mealTime}
          onChange={(e) => setMealTime(e.target.value)}
          className="border rounded-md px-2 py-1"
        />

        <label className="block mt-4">Foodstuffs:</label>
        <div className="space-y-2">
          {foodstuffs.map((foodstuff, index) => (
            <div key={index} className="flex items-center">
              <select
                name="foodstuff"
                value={foodstuff.foodstuff}
                onChange={(e) => handleFoodstuffChange(index, e)}
                className="border rounded-md px-2 py-1"
              >
                <option value="">Select Ingredient</option>
                {foodStuffOptions.map(option => (
                  <option value={option} key={option}>{option}</option>
                ))}
              </select>
              <input
                type="text"
                name="quantity"
                value={foodstuff.quantity}
                onChange={(e) => handleFoodstuffChange(index, e)}
                placeholder="Quantity"
                className="border rounded-md px-2 py-1"
              />
              {index > 0 && (
                <button
                  type="button"
                  onClick={() => handleRemoveFoodstuff(index)}
                  className="text-red-500 hover:text-red-600 ml-2"
                >
                  X
                </button>
              )}
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddFoodstuff}
            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md"
          >
            Add Foodstuff
          </button>
        </div>

        <label className="block mt-4">Ready Foods:</label>
        <div className="space-y-2">
          {readyFoods.map((readyFood, index) => (
            <div key={index} className="flex items-center">
              <select
                name="readyFood"
                value={readyFood.readyFood}
                onChange={(e) => handleReadyFoodChange(index, e)}
                className="border rounded-md px-2 py-1 select-ready-food"
              >
                <option value="">Select Ready Food</option>
                {readyFoodOptions.map(option => (
                  <option value={option} key={option}>{option}</option>
                ))}
              </select>
              <input
                type="text"
                name="quantity"
                value={readyFood.quantity}
                onChange={(e) => handleReadyFoodChange(index, e)}
                placeholder="Quantity"
                className="border rounded-md px-2 py-1"
              />
              {index > 0 && (
                <button
                  type="button"
                  onClick={() => handleRemoveReadyFood(index)}
                  className="text-red-500 hover:text-red-600 ml-2"
                >
                  X
                </button>
              )}
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddReadyFood}
            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md"
          >
            Add Ready Food
          </button>
        </div>

        <button
          type="button"
          onClick={handleSaveMeal}
          className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 mt-4 rounded-md"
        >
          Save Meal
        </button>
      </div>
    </div>
  );
};

export default AddMeal;