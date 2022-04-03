import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class FoodBrowsingService {
    getAdminsMenu(selectedFoodCategories) {

        // todo: filter by categories

        var url = new URL(API_URL + "get_my_menu")

        var params = {filterFoodCategoryNames: selectedFoodCategories}

        url.search = new URLSearchParams(params).toString();

        return fetch(url, {
            method: 'GET',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader())
        })
    }


    getRestaurantsMenuForCustomer(restaurantName, selectedFoodCategories) {

        var url = new URL(API_URL + "get_restaurant_menu")

        var params = {restaurantName: restaurantName, filterFoodCategoryNames: selectedFoodCategories}

        url.search = new URLSearchParams(params).toString();


        return fetch(url, {
            method: 'GET',
            headers: authHeader()
        })
    }

    getRestaurants(restaurantNameSubStr, deliveryZone) {
        var url = new URL(API_URL + "get_filtered_restaurants")

        var params = {nameSubString: restaurantNameSubStr, deliveryZoneName: deliveryZone}
        params = new URLSearchParams(params);
        let keysForDel = [];

        params.forEach((value, key) => {
            if (value === '' || value === undefined || value === null) {
                keysForDel.push(key);
            }
        });

        keysForDel.forEach(key => {
            params.delete(key);
        });

        url.search = new URLSearchParams(params).toString();

        return fetch(url, {
            method: 'GET',
            headers: authHeader(),
        })

    }

}
export default new FoodBrowsingService();