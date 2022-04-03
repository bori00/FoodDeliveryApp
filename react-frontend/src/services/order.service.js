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

    getRestaurantsOrders(selectedOrderStatuses) {
        var url = new URL(API_URL + "see_restaurants_sorted_filtered_orders")

        var params = {statuses: selectedOrderStatuses}
        params = new URLSearchParams(params);

        url.search = new URLSearchParams(params).toString();

        return fetch(url, {
            method: 'GET',
            headers: authHeader(),
        })
    }

    changeOrderStatus(orderId, newstatus) {
        let body = {"orderId": orderId, "newStatus": newstatus}

        return fetch(API_URL + "change_order_status", {
            method: 'POST',
            headers: Object.assign({}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            }, authHeader()),
            body: JSON.stringify(body)
        })
    }
}
export default new OrderService();