import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Select from "react-select";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import CartService from "../services/cart.service"


export default class MenuItem extends Component {
    constructor(props) {
        super(props);
        this.handleAddToCart = this.handleAddToCart.bind(this)
        this.onChangeQuantity = this.onChangeQuantity.bind(this)
        this.state = {
            fetchedQuantity: 0,
            quantity: 0,
            message: "",
            successful: true
        };
    }

    componentDidMount() {
        if (!this.props.userIsAdmin) {
            CartService.getItemCartQuantity(this.props.foodItem.name, this.props.restaurantName)
                .then(response => {
                        if (response.ok) {
                            response.json().then(responseQuantity => {
                                this.setState({
                                    quantity: Number(responseQuantity),
                                    fetchedQuantity: Number(responseQuantity)
                                });
                            });
                        } else {
                            response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                                this.setState({
                                    message: errorMsg
                                });
                                console.log("Error loading quantity")
                            })
                        }
                    }
                );
        }
    }

    handleAddToCart() {
        CartService.addItemToCart(this.props.foodItem.name, this.state.quantity, this.props.restaurantName)
            .then(response => {
                if (!response.ok) {
                    this.setState({
                        quantity: this.state.fetchedQuantity
                    });
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        this.setState({
                            message: errorMsg,
                            successful: false
                        });
                    })
                    console.log("Error loading possible food categories")
                } else {
                    this.setState({
                        message: "Saved",
                        successful: true
                    });
                }
            })
    }

    onChangeQuantity(e) {
        this.setState({
            quantity: e.target.value
        })
    }

    getAddToCartForm() {
        if (this.props.userIsAdmin) {
            return <div></div>
        } else {
            return <Fragment>
                <hr/>

                <Card.Body>
                    <Form
                        ref={c => {
                            this.form = c;
                        }}
                        history={this.props.history}
                    >
                        <div className={"lineComp"}>
                            <div>
                                <label htmlFor="quantity">Cart Quantity:</label>
                                <Input
                                    type="number"
                                    name="quantity"
                                    value={String(this.state.quantity)}
                                    onChange={this.onChangeQuantity}
                                    validations={[required]}
                                />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-secondary btn-block" type="button" onClick={this.handleAddToCart}>
                                    <span>Save</span>
                                </button>
                            </div>
                        </div>
                    </Form>
                    {this.state.message && (
                        <div className="form-group">
                            <div
                                className={
                                    this.state.successful
                                        ? "alert alert-success"
                                        : "alert alert-danger"
                                }
                                role="alert"
                            >
                                {this.state.message}
                            </div>
                        </div>
                    )}
                </Card.Body>
            </Fragment>
        }
    }

    render() {

        const foodItem = this.props.foodItem;
        const userIsAdmin = this.props.userIsAdmin;

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    <Card.Title>{foodItem.name}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted">Price: {foodItem.price}$</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">{foodItem.foodCategory}</Card.Subtitle>
                    <Card.Text>
                        {foodItem.description}
                    </Card.Text>

                    {this.getAddToCartForm()}
                </Card.Body>
            </Card>
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