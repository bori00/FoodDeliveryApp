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
                    return response.json().then(responseJson => {
                        localStorage.setItem("user", JSON.stringify(responseJson));
                        return response;
                    })
                } else {
                    return response;
                }
            })
    }

    logout() {
        localStorage.removeItem("user");
    }

    async register(username, password, isAdmin) {

        const body = {"name": username, "password": password, "userType": isAdmin ? "ADMIN" : "CUSTOMER"};

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