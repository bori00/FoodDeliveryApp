import React, {Component} from "react";
import { Card } from 'react-bootstrap';


export default class MenuItem extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const foodItem = this.props.foodItem;
        const userIsAdmin = this.props.userIsAdmin;

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    <Card.Title>{foodItem.name}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted">Price: {foodItem.price}</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">{foodItem.foodCategory}</Card.Subtitle>
                    <Card.Text>
                        {foodItem.description}
                    </Card.Text>
                </Card.Body>
            </Card>
        );
    }
}