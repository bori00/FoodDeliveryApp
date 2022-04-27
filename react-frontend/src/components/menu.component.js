import React, {Component, Fragment} from "react";
import MenuService from "../services/food_browsing.service"
import UtilService from "../services/util.service"
import {Link} from "react-router-dom";
import MenuItem from "./menu-item.component";
import Select from "react-select";


export default class Menu extends Component {
    constructor(props) {
        super(props);
        this.onChangeFoodCategory = this.onChangeFoodCategory.bind(this);
        this.state = {
            loading: true,
            menu_items: [],
            possibleFoodCategories: [],
            selectedFoodCategories: []
        };
    }

    componentDidMount() {
        // allow filtering by category
        UtilService.getAllFoodCategories()
            .then(response => {
                if (response.ok) {
                    response.json().then(response => {
                        this.setState({
                            possibleFoodCategories: response.map(category => this.getDictOfFoodCategory(category))
                        });
                    })
                } else {
                    this.setState({
                        possibleFoodCategories: []
                    });
                    console.log("Error loading possible food categories")
                }
            })

        // get menu items
        this.loadMenu()
    }

    loadMenu() {
        console.log("Load menu categories: ", this.state.selectedFoodCategories)
        this.setState({
            loading: true,
            menu_items: []
        });

        let menuPromise;
        if (this.props.match.params.restaurant === 'admins') {
            menuPromise = MenuService.getAdminsMenu(this.state.selectedFoodCategories)
        } else {
            menuPromise = MenuService.getRestaurantsMenuForCustomer(this.props.match.params.restaurant, this.state.selectedFoodCategories)
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
                console.log("Error loading menu");
            }
        })
    }

    downloadMenu(e) {
        e.preventDefault();
        MenuService.getAdminsMenuInPDF()
    }

    getDictOfFoodCategory(foodName) {
        return { value: foodName, label: foodName }
    }

    onChangeFoodCategory(e) {
        if (e != null) {
            this.setState({
                selectedFoodCategories: e.map(category => category.value)
            }, () => {
                this.loadMenu()
            })
        } else {
            this.setState({
                selectedFoodCategories: []
            }, () => {
                this.loadMenu()
            })
        }
    }

    render() {

        console.log(this.state.menu_items)

        const restaurantName = this.props.match.params.restaurant;
        const isAdmin = restaurantName === "admins";

        const {loading, menu_items} = this.state;

        const itemList = menu_items.map(menu_item => {
            return <MenuItem key={menu_item.name} foodItem={menu_item} userIsAdmin={isAdmin} restaurantName={restaurantName}/>
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
                        <Fragment>
                            <h1>My Menu</h1>

                            <br/>

                            <button onClick={e => this.downloadMenu(e)}
                                className="btn btn-secondary btn-block">
                                Download in PDF
                            </button>
                        </Fragment>
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
                        <Fragment>
                            <h4>Filter by Categories:</h4>
                            <Select options={this.state.possibleFoodCategories}
                                    isMulti
                                    isClearable
                                    name="foodCategory"
                                    value={this.state.selectedFoodCategories.map(category => this.getDictOfFoodCategory(category))}
                                    onChange={this.onChangeFoodCategory}
                            />
                            <hr/>
                            {itemList}
                        </Fragment>
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