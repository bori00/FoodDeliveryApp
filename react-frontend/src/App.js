import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { Route, Switch } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import AuthService from "./services/auth.service";
import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import RestaurantSetup from "./components/restaurant-setup.component";
import AddFood from "./components/add-food.component";
import Menu from "./components/menu.component"
import RestaurantList from "./components/restaurant-list.component";
import Cart from "./components/cart.component"
import CustomerOrderHistory from "./components/customer-order-history";
import OrderManagement from "./components/manage-orders.component";

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
        this.state = {
            showRestaurantOwnerAdminBoard: false,
            showNewAdminBoard: false,
            showCustomerBoard: false,
            currentUser: undefined,
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();
        if (user) {
            this.setState({
                currentUser: user,
                showRestaurantOwnerAdminBoard: user.role === "RESTAURANT_ADMIN" && user.hasRestaurant,
                showNewAdminBoard: user.role === "RESTAURANT_ADMIN" && !user.hasRestaurant,
                showCustomerBoard: user.role === "CUSTOMER"
            });
        }
    }

    logOut() {
        AuthService.logout();
    }

    render() {
        const { currentUser, showRestaurantOwnerAdminBoard, showNewAdminBoard, showCustomerBoard} = this.state;
        return (
            <div>
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                    <Link to={"/home"} className="navbar-brand">
                        Food Delivery
                    </Link>
                    <div className="navbar-nav mr-auto">
                        {showRestaurantOwnerAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/menu/admins"} className="nav-link">
                                     Menu
                                </Link>
                            </li>
                        )}
                        {showRestaurantOwnerAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/order-management"} className="nav-link">
                                    Orders
                                </Link>
                            </li>
                        )}
                        {showRestaurantOwnerAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/add-food"} className="nav-link">
                                    Add Menu Item
                                </Link>
                            </li>
                        )}
                        {showNewAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/setup-restaurant"} className="nav-link">
                                    Setup Restaurant
                                </Link>
                            </li>
                        )}
                        {showCustomerBoard && (
                            <li className="nav-item">
                                <Link to={"/restaurants"} className="nav-link">
                                    Restaurants
                                </Link>
                            </li>
                        )}
                        {showCustomerBoard && (
                            <li className="nav-item">
                                <Link to={"/cart"} className="nav-link">
                                    Cart
                                </Link>
                            </li>
                        )}
                        {showCustomerBoard && (
                            <li className="nav-item">
                                <Link to={"/customer-order-history"} className="nav-link">
                                    Order History
                                </Link>
                            </li>
                        )}
                    </div>
                    {currentUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <a href="/login" className="nav-link" onClick={this.logOut}>
                                    LogOut
                                </a>
                            </li>
                        </div>
                    ) : (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/login"} className="nav-link">
                                    Login
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/register"} className="nav-link">
                                    Sign Up
                                </Link>
                            </li>
                        </div>
                    )}
                </nav>
                <div className="container mt-3">
                        <Switch>
                            <Route exact path="/home" component={Home} />
                            <Route exact path="/login"  component={Login} />
                            <Route exact path="/register"  component={Register} />
                            <Route path="/setup-restaurant"  component={RestaurantSetup} />
                            <Route path="/add-food"  component={AddFood} />
                            <Route exact path="/menu/:restaurant"  component={Menu} />
                            <Route exact path="/restaurants"  component={RestaurantList} />
                            <Route exact path="/cart"  component={Cart} />
                            <Route exact path="/customer-order-history"  component={CustomerOrderHistory} />
                            <Route exact path="/order-management"  component={OrderManagement} />
                        </Switch>
                </div>
            </div>
        );
    }
}
export default withRouter(App);