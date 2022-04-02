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

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
        this.state = {
            showAdminBoard: false,
            currentUser: undefined,
        };
    }
    componentDidMount() {
        const user = AuthService.getCurrentUser();
        if (user) {
            this.setState({
                currentUser: user,
                showAdminBoard: user.role === "RESTAURANT_ADMIN",
                showCustomerBoard: user.rolw === "CUSTOMER"
            });
        }
    }
    logOut() {
        AuthService.logout();
    }
    render() {
        const { currentUser, showAdminBoard, showCustomerBoard} = this.state;
        return (
            <div>
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                    <Link to={"/home"} className="navbar-brand">
                        Food Delivery
                    </Link>
                    <div className="navbar-nav mr-auto">
                        {showAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/admin"} className="nav-link">
                                    Admin2 Board
                                </Link>
                            </li>
                        )}
                        {showCustomerBoard && (
                            <li className="nav-item">
                                <Link to={"/customer"} className="nav-link">
                                    Customer2 Board
                                </Link>
                            </li>
                        )}
                    </div>
                    {currentUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/profile"} className="nav-link">
                                    {currentUser.username}
                                </Link>
                            </li>
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
                        </Switch>
                </div>
            </div>
        );
    }
}
export default withRouter(App);