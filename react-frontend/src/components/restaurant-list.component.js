import React, {Component, Fragment} from "react";
import FoodBrowsingService from "../services/food_browsing.service"
import RestaurantItem from "./restaurant-item.component";
import Input from "react-validation/build/input";
import Select from "react-select";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import UtilService from "../services/util.service";


export default class RestaurantList extends Component {
    constructor(props) {
        super(props);
        this.handleGetFilteredRestaurants = this.handleGetFilteredRestaurants.bind(this)
        this.onChangeFilterName = this.onChangeFilterName.bind(this);
        this.onChangeRequestedDeliveryZone = this.onChangeRequestedDeliveryZone.bind(this)
        this.state = {
            loading: true,
            restaurants: [],
            filterName: "",
            filterDeliveryZone: "",
            possibleDeliveryZones: []
        };
    }

    componentDidMount() {
        this.handleGetFilteredRestaurants()

        UtilService.getAllDeliveryZones()
            .then(response => {
                if (response.ok) {
                    response.json().then(response => {
                        this.setState({
                            possibleDeliveryZones: response.map(zone => this.getDictOfDeliveryZone(zone.name))
                        });
                    })
                } else {
                    this.setState({
                        possibleDeliveryZones: []
                    });
                    console.log("Error loading possible delivery zones")
                }
            })
    }

    handleGetFilteredRestaurants() {
        this.setState({
            loading: true,
            restaurants: []
        });
        FoodBrowsingService.getRestaurants(this.state.filterName, this.state.filterDeliveryZone).then(response => {
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

    getDictOfDeliveryZone(zoneName) {
        return { value: zoneName, label: zoneName }
    }

    onChangeFilterName(e) {
        this.setState({
            filterName: e.target.value
        });
    }

    onChangeRequestedDeliveryZone(e) {
        if (e != null) {
            this.setState({
                filterDeliveryZone: e.value
            });
        } else {
            this.setState({
                filterDeliveryZone: ""
            });
        }
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
                        <Fragment>
                            <Form
                                ref={c => {
                                    this.form = c;
                                }}
                                history={this.props.history}
                            >
                                <h2>Filters</h2>
                                <div className="form-group">
                                    <label htmlFor="name">Name:</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="name"
                                        value={this.state.filterName}
                                        onChange={this.onChangeFilterName}
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="availableDeliveryZones">Requested Delivery Zone:</label>
                                    <Select options={this.state.possibleDeliveryZones}
                                            name="availableDeliveryZones"
                                            isClearable
                                            value={this.getDictOfDeliveryZone(this.state.filterDeliveryZone)}
                                            onChange={this.onChangeRequestedDeliveryZone}
                                    />
                                </div>
                                <div className="form-group text-center">
                                    <button
                                        className="btn btn-primary btn-block"
                                        disabled={this.state.loading}
                                        type="button"
                                        onClick={this.handleGetFilteredRestaurants}
                                    >
                                        {this.state.loading && (
                                            <span className="spinner-border spinner-border-sm"/>
                                        )}
                                        <span>Apply Filters</span>
                                    </button>
                                </div>
                                {this.state.message && (
                                    <div className="form-group">
                                        <div className="alert alert-danger" role="alert">
                                            {this.state.message}
                                        </div>
                                    </div>
                                )}
                                <CheckButton
                                    style={{display: "none"}}
                                    ref={c => {
                                        this.checkBtn = c;
                                    }}
                                />
                            </Form>

                            <hr/>

                            <div>
                                {restaurantsList}
                            </div>
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