import axios from "axios";


class MainService {
    PROFILE_WEIGHT_NAME_API_BASE_URL = 'http://localhost:8080/api/user/reports/weight/weight-lastSeven';
    PROFILE_BLOODPRESSURE_NAME_API_BASE_URL = 'http://localhost:8080/api/user/reports/blp/bloodpressure-lastSeven';
    PROFILE_MEALCALORIE_NAME_API_BASE_URL = 'http://localhost:8080/api/user/nutrition/nutrition-last';

    weightData = [];
    blpData = [];
    mealData = [];

    fetchWeightData = async () => {
        if (this.weightData.length > 0) {
            return this.weightData;
        }

        try {
            const response = await axios.get(this.PROFILE_WEIGHT_NAME_API_BASE_URL, {
                withCredentials: true,
            });

            this.weightData = response.data;
            return this.weightData;
        } catch (error) {
            console.error("Error fetching weight data:", error);
        }
    };

    fetchBlpData = async () => {
        if (this.blpData.length > 0) {
            return this.blpData;
        }

        try {
            const response = await axios.get(
                this.PROFILE_BLOODPRESSURE_NAME_API_BASE_URL,
                {
                    withCredentials: true,
                }
            );

            this.blpData = response.data;
            return this.blpData;
        } catch (error) {
            console.error("Error fetching blood pressure data:", error);
        }
    };

    fetchMealData = async () => {
        if (this.mealData.length > 0) {
            return this.mealData;
        }

        try {
            const response = await axios.get(
                this.PROFILE_MEALCALORIE_NAME_API_BASE_URL,
                {
                    withCredentials: true,
                }
            );

            this.mealData = response.data;
            return this.mealData;
        } catch (error) {
            console.error("Error fetching meal data:", error);
        }
    };
}
export default new MainService();