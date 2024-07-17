import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct, fetchCategories } from '../utils/api';

const AddProduct = () => {
    const [product, setProduct] = useState({ name: '', description: '', price: '', categoryId: '' });
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchCategories()
            .then(response => setCategories(response.data))
            .catch(err => setError('Error fetching categories: ' + err.message));
    }, []);

    const handleChange = (e) => {
        setProduct({ ...product, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            await createProduct({
                ...product,
                price: parseFloat(product.price),
                category: { id: parseInt(product.categoryId) }
            });
            alert('Product added successfully!');
            navigate('/');
        } catch (err) {
            setError(err.response?.data?.message || 'An error occurred while adding the product.');
        }
    };

    return (
        <div className="container">
            <h1>Add Product</h1>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="productName" className="form-label">Product Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="productName"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="productDescription" className="form-label">Description</label>
                    <input
                        type="text"
                        className="form-control"
                        id="productDescription"
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="productPrice" className="form-label">Price</label>
                    <input
                        type="number"
                        className="form-control"
                        id="productPrice"
                        name="price"
                        value={product.price}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="productCategory" className="form-label">Category</label>
                    <select
                        className="form-control"
                        id="productCategory"
                        name="categoryId"
                        value={product.categoryId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select a category</option>
                        {categories.map(category => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">Add Product</button>
            </form>
        </div>
    );
};

export default AddProduct;
