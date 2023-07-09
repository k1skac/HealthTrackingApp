import axios from "axios";

const PROFILE_NEW_GOAL_API_BASE_URL = 'http://localhost:8080/api/user/goal/';

class GoalAdderService {
    saveGoal(goalDTO) {
        return axios.post(PROFILE_NEW_GOAL_API_BASE_URL, goalDTO,{ withCredentials: true });
    }

}
export default new GoalAdderService();