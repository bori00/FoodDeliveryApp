import React, {Component, Fragment} from "react";
import MenuService from "../services/food_browsing.service"
import UtilService from "../services/util.service"
import {Link} from "react-router-dom";
import MenuItem from "./menu-item.component";


export default class Menu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            menu_items: []
        };
    }

    componentDidMount() {
        let menuPromise;
        if (this.props.match.params.restaurant === 'admins') {
            menuPromise = MenuService.getAdminsMenu()
        } else {
            menuPromise = MenuService.getRestaurantsMenuForCustomer(this.props.match.params.restaurant)
        }
        menuPromise.then(response => {
            if (response.ok) {
                response.json().then(response => {
                    this.setState({
                        loading: false,
                        menu_items: response
                    });
                })
            } else {
                this.setState({
                    loading: true,
                    menu_items: []
                });
                console.log("Error loading menu")
            }
        })
    }

    render() {

        console.log(this.state.menu_items)

        const restaurantName = this.props.match.params.restaurant;
        const isAdmin = restaurantName === "admins";

        const {loading, menu_items} = this.state;

        const itemList = menu_items.map(menu_item => {
            return <MenuItem key={menu_item.name} foodItem={menu_item} userIsAdmin={isAdmin}/>
        });


        return (
            <div className="col-md-12">
                <div className="card large-card-container">
                    <img
                        src={require('../assets/menu.png')}
                        alt="food-img"
                        className="img-card scale-down"
                    />
                    {isAdmin && (
                        <h1>My Menu</h1>
                    )}
                    {!isAdmin && (
                        <h1>{restaurantName}'s Menu</h1>
                    )}

                    <hr/>

                    {loading && (
                        <Fragment>
                            <p>Loading...</p>
                        </Fragment>
                    )}

                    {!loading && (
                        itemList
                    )}
                </div>
            </div>
        );
    }
}

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};