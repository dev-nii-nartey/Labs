import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div className="container">
            <h1>Welcome to the Product Management System</h1>
            <div className="mt-3">
                <Link to="/add-category" className="btn btn-primary">Add Category</Link>
            </div>
            <div className="mt-3">
                <Link to="/add-product" className="btn btn-primary">Add Product</Link>
            </div>
            <div className="mt-3">
                <Link to="/products" className="btn btn-primary">View All Products</Link>
            </div>
        </div>
    );
};

export default Home;
