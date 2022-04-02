import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class RestaurantManagementService {
    setupRestaurant(name, address, availableDeliveryZones) {

        let body = {"name": name, "address": address, "availableDeliveryZones": availableDeliveryZones}

        console.log("body:", body)

        return fetch(API_URL + "add_restaurant", {
            method: 'POST',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader()),
            body: JSON.stringify(body)
        })
    }

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

}
export default new RestaurantManagementService();