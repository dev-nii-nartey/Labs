import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ViewAllProducts = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/products/all');
            setProducts(response.data);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            try {
                await axios.delete(`http://localhost:8080/api/products/${id}`);
                fetchProducts();
            } catch (error) {
                alert('Error deleting product: ' + error.response?.data?.message || error.message);
            }
        }
    };

    return (
        <div className="container">
            <h1>All Products</h1>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {products.map(product => (
                    <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>{product.description}</td>
                        <td>${product.price.toFixed(2)}</td>
                        <td>{product.category.name}</td>
                        <td>
                            <button className="btn btn-sm btn-warning me-2">Edit</button>
                            <button className="btn btn-sm btn-danger" onClick={() => handleDelete(product.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ViewAllProducts;