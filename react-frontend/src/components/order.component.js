import React, {Component, Fragment} from "react";
import { Card, Button } from 'react-bootstrap';
import Input from "react-validation/build/input";
import Select from "react-select";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import UtilService from "../services/util.service"
import OrderService from "../services/order.service"

export default class Order extends Component {
    constructor(props) {
        /** props:
         * foodOrder, either of CustomerOrderDTO, or AdminOrderDTO type.
         * userIsAdmin: boolean
         */
        super(props);
        this.handleChangeOrderStatus = this.handleChangeOrderStatus.bind(this)
        this.onChangeSelectedNewStatus = this.onChangeSelectedNewStatus.bind(this)
        this.state = {
            possibleNextStatuses: [],
            selectedNewStatus: "",
            currentStatus: this.props.foodOrder.status
        };
    }

    componentDidMount() {
        if (this.props.userIsAdmin) {
            this.setNextPossiblesStates();
        }
    }

    setNextPossiblesStates() {
        UtilService.getAllPossibleNextOrderStatuses(this.state.currentStatus)
            .then(response => {
                    if (response.ok) {
                        response.json().then(response => {
                            this.setState({
                                possibleNextStatuses: response.map(status => this.getDictOfStatus(status)),
                                message: undefined
                            });
                        });
                    } else {
                        response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                            this.setState({
                                possibleNextStatuses: [],
                                message: errorMsg
                            });
                            console.log("Error loading quantity")
                        })
                    }
                }
            );
    }

    handleChangeOrderStatus() {
        OrderService.changeOrderStatus(this.props.foodOrder.id, this.state.selectedNewStatus)
            .then(response => {
                    if (response.ok) {
                        this.setState({
                            currentStatus: this.state.selectedNewStatus,
                            selectedNewStatus: ""
                        }, () => {
                            this.setNextPossiblesStates()
                        })
                        this.componentDidMount();
                    } else {
                        response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                            this.setState({
                                message: errorMsg
                            });
                            console.log("Error changing status")
                        })
                    }
                }
            );
    }

    getDictOfStatus(status) {
        return { value: status, label: status }
    }

    onChangeSelectedNewStatus(e) {
        this.setState({
            selectedNewStatus: e.value
        });
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

        const status_change_component = this.getStatusChangeComponent()

        return (
            <Card className="fitToParent menuItemCard">
                <Card.Body>
                    {this.getCardTitle(order)}
                    <Card.Subtitle className="mb-3 text-muted"><b>Time: {dateTime}</b></Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">Status: {this.state.currentStatus}</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">Total Price: {total_price}$</Card.Subtitle>
                    <div>
                        {order_content}
                    </div>
                    <div>
                        {status_change_component}
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

    getStatusChangeComponent() {
        if (this.props.userIsAdmin && this.state.possibleNextStatuses && this.state.possibleNextStatuses.length > 0) {
            return <Fragment>
                <hr/>
                <Form
                    ref={c => {
                        this.form = c;
                    }}
                    history={this.props.history}
                >
                    <div className="form-group">
                        <label htmlFor="nextStatus">Set new status to</label>
                        <Select options={this.state.possibleNextStatuses}
                                name="nextStatus"
                                value={this.getDictOfStatus(this.state.selectedNewStatus)}
                                onChange={this.onChangeSelectedNewStatus}
                        />
                    </div>
                    <div className="form-group text-center">
                        <button type="button" onClick={this.handleChangeOrderStatus}
                            className="btn btn-primary btn-block"
                            disabled={this.state.loading}
                        >
                            {this.state.loading && (
                                <span className="spinner-border spinner-border-sm"/>
                            )}
                            <span>Save</span>
                        </button>
                    </div>
                    {this.state.message && (
                        <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {this.state.message}
                            </div>
                        </div>
                    )}
                </Form>
            </Fragment>
        } else {
            return <div/>
        }
    }
}