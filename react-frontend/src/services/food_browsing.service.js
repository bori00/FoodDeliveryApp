import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class MenuService {
    getAdminsMenu() {

        // let body = {"name": name, "address": address, "availableDeliveryZones":
        // availableDeliveryZones}

        // console.log("body:", body)

        return fetch(API_URL + "get_my_menu", {
            method: 'GET',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader())
        })
    }


    getRestaurantsMenuForCustomer(restaurantName) {

        let body = {"restaurantName": restaurantName}


        return fetch(API_URL + "/get_restaurant_menu", {
            method: 'GET',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader()),
            body: JSON.stringify(body)
        })
    }

}
export default new MenuService();