import axios from "axios";

const NOTIFICATION_API_BASE_URL = 'http://localhost:8080/api/user/notifications';

class NotificationService {
    showWeightNotification() {
        return axios.get(NOTIFICATION_API_BASE_URL + '/weightNotification', { withCredentials: true });
    }

    showHeartRateNotification() {
        return axios.get(NOTIFICATION_API_BASE_URL + '/heartRateNotification', { withCredentials: true });
    }

    showBloodPressureNotification() {
        return axios.get(NOTIFICATION_API_BASE_URL + '/bloodPressureNotification', { withCredentials: true });
    }

    showMedicationNotifications() {
        return axios.get(NOTIFICATION_API_BASE_URL + '/medicationNotifications', { withCredentials: true });
    }
}

export default new NotificationService();