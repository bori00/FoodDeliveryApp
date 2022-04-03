import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Form from "react-validation/build/form";
import CartService from "../services/cart.service"
import CartItem from "./cart-item.component";


export default class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            message: "",
            restaurantName: "",
            itemsToQuantity: {},
            totalPrice: 0
        };
    }

    onLoadCartContent() {
        CartService.getCustomersCartContent()
            .then(response => {
                if (response.ok) {
                    response.json().then( responseCart => {
                        this.setState({
                            restaurantName: responseCart.restaurantName,
                            loading: false,
                            itemsToQuantity: responseCart.selectedItemsToQuantity,
                            totalPrice: this.getTotalPrice(responseCart.selectedItemsToQuantity)
                        });
                    });
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        this.setState({
                            loading: true,
                            message: errorMsg,
                            totalPrice: -1
                        });
                        console.log("Error loading quantity")
                    })
                }}
            );
    }

    componentDidMount() {
        this.onLoadCartContent()
    }

    handlePlaceOrder() {
        CartService.placeOrder()
            .then(response => {
                if (response.ok) {
                    this.onLoadCartContent();
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        this.setState({
                            message: errorMsg
                        });
                    })
                    console.log("Error placing order")
                }
            })
    }

    getTotalPrice(selectedItemsToQuantity) {
        let price = 0;
        Object.entries(selectedItemsToQuantity).map(function(item) {
            price += JSON.parse(item[0]).price *  item[1]
        });
        return price;
    }

    render() {

        const {loading, message, restaurantName, itemsToQuantity, totalPrice} = this.state;

        console.log("State: ", this.state)

        const itemComponentList = Object.entries(itemsToQuantity).map(function(item) {
            console.log("Item: ", item[0])
            return <CartItem key={JSON.parse(item[0]).name} foodItem={item[0]} quantity={item[1]}/>
        });


        return (
            <div className="col-md-12">
                <div className="card large-card-container">
                    <img
                        src={require('../assets/cart.png')}
                        alt="food-img"
                        className="img-card scale-down"
                    />
                    <h1>My Cart</h1>

                    <hr/>

                    {loading && (
                        <Fragment>
                            <p>Loading...</p>
                        </Fragment>
                    )}

                    {!loading && (
                        <Fragment>
                            <h3>From Restaurant: {restaurantName}</h3>
                            <h4>Total Price: {totalPrice}</h4>
                            <h4>Items:</h4>
                            <div>
                                {itemComponentList}
                            </div>
                        </Fragment>
                    )}
                </div>
            </div>
        );
    }
}