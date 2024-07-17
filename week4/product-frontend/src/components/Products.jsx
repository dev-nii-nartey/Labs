import React, { useState, useEffect } from 'react';
import { fetchProducts, deleteProduct } from '../utils/api';

const Products = () => {
    const [products, setProducts] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await fetchProducts(searchTerm);
            setProducts(response.data);
        } catch (err) {
            setError('Error fetching products: ' + err.message);
        }
    };

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    useEffect(() => {
        fetchData();
    }, [searchTerm]);

    const handleDeleteProduct = async (id) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            try {
                await deleteProduct(id);
                fetchData();
            } catch (err) {
                setError('Error deleting product: ' + err.message);
            }
        }
    };

    return (
        <div className="container">
            <h1>Products</h1>
            {error && <div className="alert alert-danger">{error}</div>}
            <div className="mb-3">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Search products..."
                    value={searchTerm}
                    onChange={handleSearch}
                />
            </div>
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
                            <button className="btn btn-sm btn-danger" onClick={() => handleDeleteProduct(product.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Products;
