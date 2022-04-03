import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/food-delivery/";

class OrderService {
    placeOrder() {

        return fetch(API_URL + "place_order", {
            method: 'POST',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader())
        })
    }

    getCurstomersOrders() {
        var url = new URL(API_URL + "get_my_order_history")

        return fetch(url, {
            method: 'GET',
            headers: authHeader(),
        })
    }
}
export default new OrderService();