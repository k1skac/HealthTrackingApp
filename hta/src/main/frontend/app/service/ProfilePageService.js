import axios from "axios";

const PROFILE_UPDATE_API_BASE_URL = 'http://localhost:8080/api/user/update-user-profile';

class ProfilePageService {
    getUserData() {
        return axios.get(PROFILE_UPDATE_API_BASE_URL, { withCredentials: true });
    }

    saveUserProfile(userDTO) {
        return axios.put(PROFILE_UPDATE_API_BASE_URL, userDTO, { withCredentials: true });
    }
}

export default new ProfilePageService();