import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { Route, Switch } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import AuthService from "./services/auth.service";
import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import BoardCustomer from "./components/board-customer.component";
import BoardAdmin from "./components/board-admin.component";
import RestaurantSetup from "./components/restaurant-setup.component";

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
                                <Link to={"/admin"} className="nav-link">
                                     Menu
                                </Link>
                            </li>
                        )}
                        {showRestaurantOwnerAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/admin"} className="nav-link">
                                    Orders
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
                                <Link to={"/customer"} className="nav-link">
                                    Customer Board
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
                            <Route path="/customer"  component={BoardCustomer} />
                            <Route path="/admin"  component={BoardAdmin} />
                            <Route path="/setup-restaurant"  component={RestaurantSetup} />
                        </Switch>
                </div>
            </div>
        );
    }
}
export default withRouter(App);