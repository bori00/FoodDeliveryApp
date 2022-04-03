import React, {Component, Fragment} from "react";
import OrderService from "../services/order.service";
import Order from "./order.component";
import Select from "react-select";
import UtilService from "../services/util.service"

export default class OrderManagement extends Component {
    constructor(props) {
        super(props);
        this.onChangeFilteredStatuses = this.onChangeFilteredStatuses.bind(this);
        this.state = {
            loading: true,
            message: "",
            orders: [],
            possibleOrderStatuses: [],
            filteredOrderStatuses: []
        };
    }

    componentDidMount() {
        UtilService.getAllPossibleOrderStatuses()
            .then(response => {
                if (response.ok) {
                    response.json().then( responseStatuses => {
                        this.setState({
                            possibleOrderStatuses: responseStatuses.map(category => this.getDictWithValueLabel(category))
                        });
                    });
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        this.setState({
                            possibleOrderStatuses: []
                        });
                        console.log("Error loading possible order statuses")
                    })
                }}
            );

        this.loadFilteredOrders();
    }

    loadFilteredOrders() {
        this.loadFilteredOrdersForGivenComponent(this)
    }

    loadFilteredOrdersForGivenComponent(thisComp) {
        console.log("Loading for: ", thisComp.state.filteredOrderStatuses)
        thisComp.setState({
            loading: true,
            orders: []
        });
        OrderService.getRestaurantsOrders(thisComp.state.filteredOrderStatuses)
            .then(response => {
                if (response.ok) {
                    response.json().then( responseOrders => {
                        thisComp.setState({
                            loading: false,
                            orders: responseOrders
                        });
                    });
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        thisComp.setState({
                            loading: true,
                            message: errorMsg,
                            orders: []
                        });
                        console.log("Error loading orders")
                    })
                }}
            );
    }

    getDictWithValueLabel(name) {
        return { value: name, label: name }
    }

    onChangeFilteredStatuses(e) {
        if (e != null) {
            this.setState({
                filteredOrderStatuses: e.map(status => status.value)
            }, () => {
                this.loadFilteredOrders();
            })
        } else {
            this.setState({
                filteredOrderStatuses: []
            }, () => {
                this.loadFilteredOrders();
            })
        }
    }

    render() {

        const {loading, message, orders} = this.state;
        const thisComp = this;

        let key = -1;
        const orderList = orders.map(order => {
            key++;
            return <Order key={key} foodOrder={order} userIsAdmin={true} onStatusChange={() => {this.loadFilteredOrdersForGivenComponent(thisComp)}}/>
        });


        return (
            <div className="col-md-12">
                <div className="card large-card-container">
                    <img
                        src={require('../assets/order.png')}
                        alt="food-img"
                        className="img-card scale-down"
                    />
                    <h1>Order History</h1>
                    <hr/>

                    <Fragment>
                        <h4>Filter by Status:</h4>
                        <Select options={this.state.possibleOrderStatuses}
                                isMulti
                                isClearable
                                name="foodCategory"
                                value={this.state.filteredOrderStatuses.map(status => this.getDictWithValueLabel(status))}
                                onChange={this.onChangeFilteredStatuses}
                        />
                    </Fragment>
                    <hr/>

                    {loading && (
                        <Fragment>
                            <p>Loading...</p>
                        </Fragment>
                    )}

                    {!loading && (
                        orderList
                    )}
                </div>
            </div>
        );
    }
}