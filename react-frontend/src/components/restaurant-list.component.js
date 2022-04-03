import React, {Component, Fragment} from "react";
import FoodBrowsingService from "../services/food_browsing.service"
import RestaurantItem from "./restaurant-item.component";


export default class RestaurantList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            restaurants: []
        };
    }

    componentDidMount() {
        FoodBrowsingService.getRestaurants('', '').then(response => {
            if (response.ok) {
                response.json().then(response => {
                    this.setState({
                        loading: false,
                        restaurants: response
                    });
                })
            } else {
                this.setState({
                    loading: true,
                    restaurants: []
                });
                console.log("Error loading Restaurants' List")
            }
        })
    }

    render() {

        const {loading, restaurants} = this.state;

        const restaurantsList = restaurants.map(restaurant => {
            return <RestaurantItem key={restaurant.name} restaurant={restaurant}/>
        });


        return (
            <div className="col-md-12">
                <div className="card large-card-container">
                    <img
                        src={require('../assets/restaurant_list.png')}
                        alt="food-img"
                        className="img-card scale-down"
                    />
                    <h1>Restaurants</h1>

                    <hr/>

                    {loading && (
                        <Fragment>
                            <p>Loading...</p>
                        </Fragment>
                    )}

                    {!loading && (
                        restaurantsList
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