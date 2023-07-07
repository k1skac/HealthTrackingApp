import React, { Fragment, useState, useEffect } from 'react';
import CalorieIntakeService from '@/app/service/CalorieIntakeService';
import Donut from '../components/Donut';
import {Dialog, Transition} from "@headlessui/react";

const AddMeal = () => {
  const [mealTime, setMealTime] = useState('');
  const [foodstuffs, setFoodstuffs] = useState([{ foodstuff: '', quantity: '' }]);
  const [readyFoods, setReadyFoods] = useState([{ readyFood: '', quantity: '' }]);
  const [readyFoodOptions, setReadyFoodOptions] = useState([]);
  const [foodStuffOptions, setFoodStuffOptions] = useState([]);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    fetchReadyFoodOptions();
  }, []);

  useEffect(() => {
    fetchFoodStuffOptions();
  }, []);

  useEffect(() => {
    const interval = setInterval(handleSaveDynamicMeal, 1000);
    return () => clearInterval(interval);
  }, [foodstuffs, readyFoods]);

  const handleSaveDynamicMeal = () => {
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
  
    const dynamicMealData = {
      mealTime: mealTime || '',
      foodstuffsList,
      readyFoodsList,
    };
  
    CalorieIntakeService.saveDynamicMeal(dynamicMealData)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

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
  
    const dynamicMealData = {
      mealTime: mealTime || '',
      foodstuffsList,
      readyFoodsList,
    };
  
    CalorieIntakeService.saveDynamicMeal(dynamicMealData)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const reset = (e) => {
    e.preventDefault();
    setFoodstuffs([{
      foodstuff: '',
      quantity: ''
    }]);
    setReadyFoods([{
      readyFood: '',
      quantity: ''
    }]);
    setMealTime('');
  };

  function closeModal() {
    setIsOpen(false);
  }

  function openModal() {
    setIsOpen(true);
  }

  return (
    <div>
      <button
        onClick={openModal}
        className='rounded bg-htadarkteal hover:bg-htadarktealhover text-white w-48 py-3 shadow-cyan-950 shadow-md font-semi'
      >
        Add Meal
      </button>
      <Transition appear show={isOpen} as={Fragment}>
        <div className='m-auto w-full max-w-screen-md overflow-hidden text-left align-middle transition-all transform shadow-xl rounded-md'>
          <Dialog
            as="div"
            className='fixed inset-24 max-w-screen-lg mx-auto'
            onClose={closeModal}
          >
            <div className='min-h-screen text-center'>
              <Transition.Child
                as={Fragment}
                enter='ease-out duration-300'
                enterFrom='opacity-0 scale-95'
                enterTo='opacity-100 scale-100'
                leave='ease-in duration-200'
                leaveFrom='opacity-100 csale-100'
                leaveTo='opacity-0 scale-95'
              >
                <div className="overflow-auto">
                  <Dialog.Title
                    as='div'
                    className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'
                  >
                    <h3 className='text-lg font-bold text-white m-auto'>
                      Add Meal
                    </h3>
                  </Dialog.Title>
                  <div className="grid grid-cols-10 gap-4 overflow-y-auto h-96 bg-htamediumteal rounded-md shadow-slate-900 shadow-md">
                    <div className="p-4 col-span-7">
                      <div className="mb-4">
                        <h2 className="text-lg font-semibold">Add Meal</h2>
                        <label className="block mt-2">Meal Time:</label>
                        <input
                          type="datetime-local"
                          value={mealTime}
                          onChange={(e) => setMealTime(e.target.value)}
                          className="border rounded-md px-2 py-1"
                        />
  
                        <label className="block mt-4">Ingredients:</label>
                        <div className="space-y-2">
                          {foodstuffs.map((foodstuff, index) => (
                            <div key={index} className="flex items-center">
                              <select
                                name="foodstuff"
                                value={foodstuff.foodstuff}
                                onChange={(e) => handleFoodstuffChange(index, e)}
                                className="border rounded-md px-2 py-1 w-80"
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
                                placeholder="Gram"
                                className="border rounded-md px-1 py-1 w-20 ml-2" // Adjusted input size and added ml-2 class for spacing
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
                            Add Ingredient
                          </button>
                        </div>
  
                        <label className="block mt-4">Prepared Meal</label>
                        <div className="space-y-2">
                          {readyFoods.map((readyFood, index) => (
                            <div key={index} className="flex items-center">
                              <select
                                name="readyFood"
                                value={readyFood.readyFood}
                                onChange={(e) => handleReadyFoodChange(index, e)}
                                className="border rounded-md px-2 py-1 select-ready-food w-80"
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
                                className="border rounded-md px-1 py-1 w-20 ml-2" // Adjusted input size and added ml-2 class for spacing
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
                            Add Prepared Food
                          </button>
                        </div>
  
                        <div className='mt-2'></div>
                        <div className='flex justify-center pt-2 pb-20'>
                          <div className='h-14 my-4 pt-4'>
                            <button
                              type="button"
                              onClick={handleSaveMeal}
                              className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'
                            >
                              Save Meal
                            </button>
                          </div>
                          <div className='h-14 my-4 pt-4'>
                            <button
                              type="reset"
                              onClick={reset}
                              className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'
                            >
                              Clear
                            </button>
                          </div>
                          <div className='h-14 my-4 pt-4'>
                            <button
                              onClick={closeModal} //Added function
                              className='mx-2 rounded text-white font-semibold bg-red-500 hover:bg-red-700 py-2 px-6 shadow-slate-900 shadow-sm'
                            >
                              Close
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="col-span-3">
                      <Donut />
                    </div>
                  </div>
                </div>
              </Transition.Child>
            </div>
          </Dialog>
        </div>
      </Transition>
    </div>
  );
};

export default AddMeal;