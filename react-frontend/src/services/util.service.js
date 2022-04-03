import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class UtilService {

    getAllDeliveryZones() {
        return fetch(API_URL + "util/get_all_delivery_zones", {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }
        })
    }

    getAllFoodCategories() {
        return fetch(API_URL + "util/get_all_food_categories", {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }
        })
    }

    getAllPossibleNextOrderStatuses(currentStatus) {
        var url = new URL(API_URL + "util/get_all_possible_next_order_statuses")

        var params = {currentStatus: currentStatus}
        params = new URLSearchParams(params);

        url.search = new URLSearchParams(params).toString();

        return fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }
        })
    }

    getAllPossibleOrderStatuses() {
        var url = new URL(API_URL + "util/get_all_possible_order_statuses")

        return fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }
        })
    }

}
export default new UtilService();