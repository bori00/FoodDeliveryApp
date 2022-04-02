import React, {Component} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import RestaurantManagementService from "../services/restaurant_management.service";
import AuthService from "../services/auth.service"
import UtilService from "../services/util.service"
import Select from 'react-select'


export default class AddFood extends Component {
    constructor(props) {
        super(props);
        this.handleSaveFood = this.handleSaveFood.bind(this)
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this)
        this.onChangeFoodCategory = this.onChangeFoodCategory.bind(this)
        this.state = {
            name: "",
            description: "",
            price: 0.0,
            foodCategory: undefined,
            possibleFoodCategories: [],
            successful: false,
            message: "",
            loading: false
        };
    }

    getDictOfFoodCategory(foodName) {
        return { value: foodName, label: foodName }
    }

    componentDidMount() {
        UtilService.getAllFoodCategories()
            .then(response => {
                if (response.ok) {
                    response.json().then(response => {
                        this.setState({
                            possibleFoodCategories: response.map(category => this.getDictOfFoodCategory(category))
                        });
                    }).then(console.log(this.state.possibleFoodCategories))
                } else {
                    this.setState({
                        possibleFoodCategories: []
                    });
                    console.log("Error loading possible food categories")
                }
            })
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    onChangeDescription(e) {
        this.setState({
            description: e.target.value
        });
    }

    onChangeFoodCategory(e) {
        this.setState({
            foodCategory: e.value
        });
    }

    onChangePrice(e) {
        this.setState({
            price: e.target.value
        })
    }

    handleSaveFood(e) {

        e.preventDefault();
        this.setState({
            message: "",
            successful: false,
            loading: false
        });

        this.form.validateAll();
        if (this.checkBtn.context._errors.length === 0) {
            RestaurantManagementService.addFoodToMenu(this.state.name, this.state.price, this.state.description, this.state.foodCategory)
                .then(response => {
                        if (response.ok) {
                            this.props.history.push("/home");
                            window.location.reload();
                        } else {
                            response.json().then(response => response.messages.join("\n")).then(errorMsg => {
                                this.setState({
                                    successful: false,
                                    message: errorMsg
                                });
                            })
                        }
                    }
                );
        } else {
            this.setState({
                loading: false
            });
        }
    }

    render() {

        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <img
                        src={require('../assets/food.png')}
                        alt="food-img"
                        className="img-card scale-down"
                    />
                    <Form
                        onSubmit={this.handleSaveFood}
                        ref={c => {
                            this.form = c;
                        }}
                        history={this.props.history}
                    >
                        <h1>Add a Menu Item</h1>
                        <div className="form-group">
                            <label htmlFor="name">Name:</label>
                            <Input
                                type="text"
                                className="form-control"
                                name="name"
                                value={this.state.name}
                                onChange={this.onChangeName}
                                validations={[required]}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="price">Price:</label>
                            <Input
                                type="number"
                                className="form-control"
                                name="price"
                                value={this.state.price}
                                onChange={this.onChangePrice}
                                validations={[required]}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Description:</label>
                            <Input
                                type="text"
                                className="form-control"
                                name="description"
                                value={this.state.description}
                                onChange={this.onChangeDescription}
                                validations={[required]}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="foodCategory">Food Category:</label>
                            <Select options={this.state.possibleFoodCategories}
                                    name="foodCategory"
                                    onChange={this.onChangeFoodCategory}
                            />
                        </div>
                        <div className="form-group text-center">
                            <button
                                className="btn btn-primary btn-block"
                                disabled={this.state.loading}
                            >
                                {this.state.loading && (
                                    <span className="spinner-border spinner-border-sm"/>
                                )}
                                <span>Save Item</span>
                            </button>
                        </div>
                        {this.state.message && (
                            <div className="form-group">
                                <div className="alert alert-danger" role="alert">
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <CheckButton
                            style={{display: "none"}}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
            </div>
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