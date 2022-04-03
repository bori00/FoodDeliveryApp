import React, {Component} from "react";
import { Card, Button } from 'react-bootstrap';
import {Link} from "react-router-dom";


export default class RestaurantItem extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const restaurant = this.props.restaurant;

        const menuLink = "/menu/" + restaurant.name;

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    <Card.Title>{restaurant.name}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted">Address: {restaurant.address}</Card.Subtitle>
                    <Card.Text>
                        Delivers to: {restaurant.availableDeliveryZoneNames.join(", ")}
                    </Card.Text>
                    <Card.Link href={menuLink}>View Menu</Card.Link>
                </Card.Body>
            </Card>
        );
    }
}