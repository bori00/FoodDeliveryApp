const API_URL = "http://localhost:8080/food-delivery/";

class AuthService {
    login(username, password) {

        let body = {"name": username, "password": password}

        return fetch(API_URL + "login", {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    "charset": "UTF-8"
                },
                body: JSON.stringify(body)
            }).then(response => {
                if (response.ok) {
                    localStorage.setItem("user", JSON.stringify(response));
                }
                return response;
            })
    }

    logout() {
        localStorage.removeItem("user");
    }

    async register(username, password) {
        let body = {"name": username, "password": password, "userType": "CUSTOMER"} // todo:
        // change type

        return fetch(API_URL + "register", {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                "charset": "UTF-8"
            },
            body: JSON.stringify(body)
        })
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}
export default new AuthService();