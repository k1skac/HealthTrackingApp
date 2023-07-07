import axios from "axios";

const PROFILE_REGISTER_API_BASE_URL = 'http://localhost:8080/api/user/register';
const LOGOUT_API_BASE_URL = 'http://localhost:8080/api/user/logout';

class RegisterService {

    saveRegisterProfile(registerDTO) {
        return axios.post(PROFILE_REGISTER_API_BASE_URL , registerDTO, { withCredentials: true });
    }
}
export default new RegisterService();