import React, {Component} from "react";
import { Card } from 'react-bootstrap';


export default class RestaurantItem extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const restaurant = this.props.restaurant;

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    <Card.Title>{restaurant.name}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted">Address: {restaurant.address}</Card.Subtitle>
                </Card.Body>
            </Card>
        );
    }
}