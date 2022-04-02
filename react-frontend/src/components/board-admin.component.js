import React, { Component } from "react";

export default class BoardAdmin extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: ""
        };
    }
    componentDidMount() {
        // UserService.getUserBoard().then(
        //     response => {
        //         this.setState({
        //             content: response.data
        //         });
        //     },
        //     error => {
        //         this.setState({
        //             content:
        //                 (error.response &&
        //                     error.response.data &&
        //                     error.response.data.message) ||
        //                 error.message ||
        //                 error.toString()
        //         });
        //     }
        // );
    }
    render() {
        return (
            <div className="container">
                <header className="jumbotron">
                    <h3>admin Board</h3>
                </header>
            </div>
        );
    }
}