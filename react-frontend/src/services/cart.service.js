import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class CartService {
    addItemToCart(foodName, quantity, restaurantName) {

        let body = {"foodName": foodName, "quantity": quantity, "restaurantName": restaurantName}

        return fetch(API_URL + "add_item_to_cart", {
            method: 'POST',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader()),
            body: JSON.stringify(body)
        })
    }

    getItemCartQuantity(foodName, restaurantName) {
        var url = new URL(API_URL + "get_cart_item_quantity")

        var params = {"itemName": foodName, "restaurantName": restaurantName}
        params = new URLSearchParams(params);

        url.search = new URLSearchParams(params).toString();

        return fetch(url, {
            method: 'GET',
            headers: authHeader(),
        })
    }

    getCustomersCartContent() {
        var url = new URL(API_URL + "get_my_cart")

        return fetch(url, {
            method: 'GET',
            headers: authHeader(),
        })
    }
}
export default new CartService();