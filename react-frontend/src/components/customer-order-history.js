import React, {Component, Fragment} from "react";
import OrderService from "../services/order.service";
import Order from "./order.component";

export default class CustomerOrderHistory extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            message: "",
            orders: []
        };
    }

    componentDidMount() {
        OrderService.getCurstomersOrders()
            .then(response => {
                if (response.ok) {
                    response.json().then( responseOrders => {
                        console.log(responseOrders)
                        this.setState({
                            loading: false,
                            orders: responseOrders
                        });
                    });
                } else {
                    response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                        this.setState({
                            loading: true,
                            message: errorMsg,
                            orders: []
                        });
                        console.log("Error loading orders")
                    })
                }}
            );
    }

    render() {

        console.log(this.state.orders)

        const {loading, message, orders} = this.state;

        let key = -1;
        const orderList = orders.map(order => {
            key++;
            console.log("Order: ", order)
            return <Order key={key} foodOrder={order}/>
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