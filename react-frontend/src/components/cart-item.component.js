import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Select from "react-select";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import CartService from "../services/cart.service"


export default class CartItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            foodItem: JSON.parse(this.props.foodItem)
        };
    }

    render() {

        const foodItem = this.state.foodItem;
        const quantity = this.props.quantity;

        const total_price = quantity * foodItem.price;

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    <Card.Title>{foodItem.name}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted"><b>Total Price: {total_price}</b></Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">Unit Price: {foodItem.price}, Quantity: {quantity}</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">{foodItem.foodCategory}</Card.Subtitle>
                    <Card.Text>
                        {foodItem.description}
                    </Card.Text>
                </Card.Body>
            </Card>
        );
    }
}