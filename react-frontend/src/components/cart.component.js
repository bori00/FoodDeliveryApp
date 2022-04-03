import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Form from "react-validation/build/form";
import CartService from "../services/cart.service"
import OrderService from "../services/order.service"
import MenuItem from "./menu-item.component";


export default class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            message: "",
            restaurantName: "",
            itemsToQuantity: {},
            totalPrice: 0,
            emptyCart: true
        };
    }

    onLoadCartContent() {
        this.onLoadCartContentForGivenCart(this)
    }

    onLoadCartContentForGivenCart(cart) {
        CartService.getCustomersCartContent()
            .then(response => {
                if (response.ok) {
                    response.json().then( responseCart => {
                        if (responseCart.restaurantName != null) {
                            cart.setState({
                                restaurantName: responseCart.restaurantName,
                                loading: false,
                                itemsToQuantity: responseCart.selectedItemsToQuantity,
                                totalPrice: getTotalPrice(responseCart.selectedItemsToQuantity),
                                emptyCart: false
                            });
                        } else {
                            console.log("EMPTY")
                            cart.setState({
                                loading: false,
                                emptyCart: true
                            });
                        }
                    });
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        cart.setState({
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
        OrderService.placeOrder()
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
        const {loading, message, restaurantName, itemsToQuantity, totalPrice, emptyCart} = this.state;

        let itemComponentList;

        if (!emptyCart) {
            const cart = this;
            itemComponentList = Object.entries(itemsToQuantity).map(function (item) {
                console.log("Item: ", item[0])
                return <MenuItem key={JSON.parse(item[0]).name} foodItem={JSON.parse(item[0])} userIsAdmin={false} restaurantName={restaurantName} onCartQuantityChange={() => {cart.onLoadCartContentForGivenCart(cart)}}/>
            });
        } else {
            itemComponentList = <div/>
        }


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

                    {!loading && !emptyCart && (
                        <Fragment>
                            <h3>From Restaurant: {restaurantName}</h3>
                            <h4>Total Price: {totalPrice}</h4>

                            <Form
                                onSubmit={this.handlePlaceOrder}
                                ref={c => {
                                    this.form = c;
                                }}
                                history={this.props.history}
                            >
                                <div className="text-center">
                                    <button className="btn btn-primary btn-block">
                                        <span>Place Order</span>
                                    </button>
                                </div>
                            </Form>

                            <h4>Items:</h4>
                            <div>
                                {itemComponentList}
                            </div>
                        </Fragment>
                    )}

                    {emptyCart && (
                        <p>Your cart is currently empty. Explore our extensive catalogue of <a href={"/restaurants"}>restaurants</a> to find the most delicius foods!</p>
                    )}
                </div>
            </div>
        );
    }
}

function getTotalPrice(selectedItemsToQuantity) {
    let price = 0;
    Object.entries(selectedItemsToQuantity).map(function(item) {
        price += JSON.parse(item[0]).price *  item[1]
    });
    return price;
}