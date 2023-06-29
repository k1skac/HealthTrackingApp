import axios from 'axios';

const NUTRITION_API_BASE_URL = 'http://localhost:8080/api/user/nutrition';

class CalorieIntakeService {
  saveFoodStuff(calorieIntake) {
    return axios.post(`${NUTRITION_API_BASE_URL}/add-foodStuff`, calorieIntake, { withCredentials: true });
  }

  saveReadyFood(calorieIntake) {
    return axios.post(`${NUTRITION_API_BASE_URL}/add-readyFood`, calorieIntake, { withCredentials: true });
  }

  saveMeal(addMealDTO) {
    return axios.post(`${NUTRITION_API_BASE_URL}/add-meal`, addMealDTO, { withCredentials: true });
  }
  getFoodTypesAsString() {
    return axios.get(`${NUTRITION_API_BASE_URL}/readyFoodTypes`, { withCredentials: true });
  }

  getFoodStuffTypesAsString() {
    return axios.get(`${NUTRITION_API_BASE_URL}/food-stuff-types`, { withCredentials: true });
  }

  sendDynamicData(dynamicData) {
    return axios.post(`${NUTRITION_API_BASE_URL}/post-food-data`, dynamicData, { withCredentials: true });
  }
  getDynamicData() {
    return axios.get(`${NUTRITION_API_BASE_URL}/get-food-data`, { withCredentials: true });
  }

}

export default new CalorieIntakeService();