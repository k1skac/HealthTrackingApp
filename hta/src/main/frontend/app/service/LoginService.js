import axios from "axios";

const LOGIN_API_BASE_URL = 'http://localhost:8080/api/user/authenticate';

class LoginService {
    loginUser(loginDTO) {
        return axios.post(LOGIN_API_BASE_URL, loginDTO, { withCredentials: true });
    }
}

export default new LoginService();