import React, {Component} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import RestaurantManagementService from "../services/restaurant_management.service";
import AuthService from "../services/auth.service"
import Select from 'react-select'


export default class RestaurantSetup extends Component {
    constructor(props) {
        super(props);
        this.handleRestaurantSetup = this.handleRestaurantSetup.bind(this)
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeAddress = this.onChangeAddress.bind(this);
        this.onChangeAvailableDeliveryZones = this.onChangeAvailableDeliveryZones.bind(this)
        this.state = {
            name: "",
            address: "",
            availableDeliveryZones: [],
            possibleDeliveryZones: [],
            successful: false,
            message: "",
            loading: false
        };
    }

    getDictOfDeliveryZone(zoneName) {
        return { value: zoneName, label: zoneName }
    }

    componentDidMount() {
        console.log("DidMount")
        RestaurantManagementService.getAllDeliveryZones()
            .then(response => {
                console.log("Delivery zones response: ", response)
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

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    onChangeAddress(e) {
        this.setState({
            address: e.target.value
        });
    }

    onChangeAvailableDeliveryZones(e) {
        this.setState({
            availableDeliveryZones: e.map(zone => zone.value)
        });
    }

    handleRestaurantSetup(e) {

        e.preventDefault();
        this.setState({
            message: "",
            successful: false,
            loading: false
        });

        this.form.validateAll();
        if (this.checkBtn.context._errors.length === 0) {
            RestaurantManagementService.setupRestaurant(this.state.name, this.state.address, this.state.availableDeliveryZones)
                .then(response => {
                    if (response.ok) {
                        AuthService.setCurrentUserHasRestaurant()
                        this.props.history.push("/home");
                        window.location.reload();
                    } else {
                        response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                            this.setState({
                                successful: false,
                                message: errorMsg
                            });
                        })
                    }
                    }
                );
        } else {
            this.setState({
                loading: false
            });
        }
    }

    render() {

        const options = [
            { value: 'chocolate', label: 'Chocolate' },
            { value: 'strawberry', label: 'Strawberry' },
            { value: 'vanilla', label: 'Vanilla' }
        ]

        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <img
                        src={require('../assets/restaurant.png')}
                        alt="restaurant-img"
                        className="img-card scale-down"
                    />
                    <Form
                        onSubmit={this.handleRestaurantSetup}
                        ref={c => {
                            this.form = c;
                        }}
                        history={this.props.history}
                    >
                        <h1>Restaurant Setup</h1>
                        <div className="form-group">
                            <label htmlFor="name">Name:</label>
                            <Input
                                type="text"
                                className="form-control"
                                name="name"
                                value={this.state.name}
                                onChange={this.onChangeName}
                                validations={[required]}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="address">Address</label>
                            <Input
                                type="text"
                                className="form-control"
                                name="address"
                                value={this.state.address}
                                onChange={this.onChangeAddress}
                                validations={[required]}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="availableDeliveryZones">Available Delivery
                                Zones:</label>
                            <Select options={this.state.possibleDeliveryZones}
                                    isMulti
                                    name="availableDeliveryZones"
                                    onChange={this.onChangeAvailableDeliveryZones}
                            />
                        </div>
                        <div className="form-group text-center">
                            <button
                                className="btn btn-primary btn-block"
                                disabled={this.state.loading}
                            >
                                {this.state.loading && (
                                    <span className="spinner-border spinner-border-sm"/>
                                )}
                                <span>Add Restaurant</span>
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