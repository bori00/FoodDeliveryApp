import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Select from "react-select";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import CartService from "../services/cart.service"


export default class Order extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const order = this.props.foodOrder;

        let dateTime;
        if (order.dateTime != null) {
            dateTime = this.getDateTimeString(order.dateTime);
        } else {
            dateTime = "unknown";
        }

        const total_price = this.getTotalPrice(order.orderedItemsToQuantity)

        const order_content = this.getOrderContent(order.orderedItemsToQuantity)

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    {this.getCardTitle(order)}
                    <Card.Subtitle className="mb-3 text-muted"><b>Time: {dateTime}</b></Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">Status: {order.status}</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">Total Price: {total_price}$</Card.Subtitle>
                    <div>
                        {order_content}
                    </div>
                </Card.Body>
            </Card>
        );
    }

    getCardTitle(order) {
        if (order.restaurantName) {
            return <Card.Title>Restaurant: {order.restaurantName}</Card.Title>
        } else {
            return <Card.Title>Client: {order.clientName}</Card.Title>
        }
    }

    getTotalPrice(orderedItemsToQuantity) {
        let price = 0;
        Object.entries(orderedItemsToQuantity).map(function(item) {
            price += JSON.parse(item[0]).price *  item[1]
        });
        return price;
    }

    getDateTimeString(dateTime) {
        return dateTime[2] + "." + dateTime[1] + "." + dateTime[0] + ", " + dateTime[3] + ":" + dateTime[4]
    }

    getOrderContent(orderedItemsToQuantity) {
        let id = -1;
        return Object.entries(orderedItemsToQuantity).map(function(item) {
            const foodItem = JSON.parse(item[0])
            id++;
            return <p key={id}>{foodItem.name}  ................   {foodItem.price}$ x {item[1]}</p>
        })
    }
}