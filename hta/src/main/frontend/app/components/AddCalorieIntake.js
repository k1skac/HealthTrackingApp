import React, { Fragment, useState } from 'react';
import CalorieIntakeService from '@/app/service/CalorieIntakeService';
import { Dialog, Transition } from "@headlessui/react";

const AddCalorieIntake = () => {
  const [isOpen, setIsOpen] = useState(false);
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
        Add Calorie Intake
      </button>
      <Transition appear show={isOpen} as={Fragment}>
        <div className='m-auto w-full max-w-screen-md overflow-hidden text-left align-middle transition-all transform shadow-xl rounded-md'>
          <Dialog
            as="div"
            className='fixed inset-24 max-w-screen-lg mx-auto'
            onClose={closeModal}
          >
            <div className='text-center'>
              <Transition.Child
                as={Fragment}
                enter='ease-out duration-300'
                enterFrom='opacity-0 scale-95'
                enterTo='opacity-100 scale-100'
                leave='ease-in duration-200'
                leaveFrom='opacity-100 scale-100'
                leaveTo='opacity-0 scale-95'
              >
                <div>
                  <Dialog.Title
                    as='div'
                    className='mb-1 bg-htadarkteal rounded-md py-5 shadow-slate-900 shadow-md'
                  >
                    <h3 className='text-lg font-bold text-white m-auto'>
                      Add Prepared Food or Ingredient
                    </h3>
                  </Dialog.Title>
  
                  <div className="overflow-y-auto h-96 bg-htamediumteal rounded-md shadow-slate-900 shadow-md">
                    <div className=" m-auto max-w-md inline-flex justify-center">
                      <div>
                        <div className="flex flex-row">
                          <div className="w-1/4 mr-4 mt-2 border-gray-300" />
                          <h1 className="text-lg font-semibold text-white">Ingredient</h1>
                          <div className="w-1/4 ml-4 mt-2 border-gray-300" />
                        </div>
  
                        <div className="grid grid-cols-2 gap-4">
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Food Name:</label>
                            <input
                              type="text"
                              name="name"
                              placeholder="Food Name"
                              value={foodStuff.name}
                              onChange={handleFoodStuffChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Calories per 100g:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="caloriePer100g"
                              placeholder="...cal"
                              value={foodStuff.caloriePer100g}
                              onChange={handleFoodStuffChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Fat per 100g:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="fatPer100g"
                              placeholder="...g"
                              value={foodStuff.fatPer100g}
                              onChange={handleFoodStuffChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Carbohydrate per 100g:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="carbohydratePer100g"
                              placeholder="...g"
                              value={foodStuff.carbohydratePer100g}
                              onChange={handleFoodStuffChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Protein per 100g:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="proteinPer100g"
                              placeholder="...g"
                              value={foodStuff.proteinPer100g}
                              onChange={handleFoodStuffChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                        </div>
  
                        <div className="mt-8 flex justify-end">
                          <button
                            type="button"
                            onClick={resetFoodStuff}
                            className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'
                          >
                            Reset Ingredient
                          </button>
                          <button
                            type="submit"
                            onClick={saveFoodStuff}
                            className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'
                          >
                            Save Ingredient
                          </button>
                        </div>
                      </div>
                    </div>
  
                    <div className="h-14 my-4 pt-4"></div>
  
                    <div className=" m-auto max-w-md text-black inline-flex justify-center">
                      <div>
                        <div className="flex flex-row">
                          <div className="w-1/4 mr-4 mt-2 border-gray-300" />
                          <h2 className="text-gray-800 text-lg font-semibold text-white">Prepared Food</h2>
                          <div className="w-1/4 ml-4 mt-2 border-gray-300" />
                        </div>
  
                        <div className="grid grid-cols-2 gap-4">
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Food Name:</label>
                            <input
                              type="text"
                              name="name"
                              placeholder="Food Name"
                              value={readyFood.name}
                              onChange={handleReadyFoodChange}
                              className="h-10 border px-2 py-1 text-black"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Calories per Portion:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="caloriesPerPortion"
                              placeholder="...cal"
                              value={readyFood.caloriesPerPortion}
                              onChange={handleReadyFoodChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Fat per Portion:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="fatPerPortion"
                              placeholder="...g"
                              value={readyFood.fatPerPortion}
                              onChange={handleReadyFoodChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Carbohydrate per Portion:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="carbohydratePerPortion"
                              placeholder="...g"
                              value={readyFood.carbohydratePerPortion}
                              onChange={handleReadyFoodChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                          <div className="flex flex-col">
                            <label className="text-gray-800 text-sm font-normal">Protein per Portion:</label>
                            <input
                              type="number"
                              step="0.01"
                              name="proteinPerPortion"
                              placeholder="...g"
                              value={readyFood.proteinPerPortion}
                              onChange={handleReadyFoodChange}
                              className="h-10 border px-2 py-1"
                            />
                          </div>
                        </div>
  
                        <div className="mt-8 flex justify-end">
                          <button
                            type="button"
                            onClick={resetReadyFood}
                            className='mx-2 rounded text-white font-semibold bg-orange-500 hover:bg-orange-700 py-2 px-6 shadow-slate-900 shadow-sm'
                          >
                            Reset Prepared Food
                          </button>
                          <button
                            type="submit"
                            onClick={saveReadyFood}
                            className='mx-2 rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6 shadow-slate-900 shadow-sm'
                          >
                            Save Prepared Food
                          </button>
                        </div>
                      </div>
                    </div>
                    <div>
                    <div className="mt-2" />
                    <button
                      onClick={closeModal}
                      className='mx-2 rounded text-white font-semibold bg-red-500 hover:bg-red-700 py-2 px-6 shadow-slate-900 shadow-sm'
                    >
                      Close
                    </button>
                    <div className="mt-2"/>
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

export default AddCalorieIntake;