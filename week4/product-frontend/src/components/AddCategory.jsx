import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCategory } from '../utils/api';

const AddCategory = () => {
    const [name, setName] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            await createCategory({ name });
            alert('Category added successfully!');
            navigate('/');
        } catch (err) {
            setError(err.response?.data?.message || 'An error occurred while adding the category.');
        }
    };

    return (
        <div className="container">
            <h1>Add Category</h1>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="categoryName" className="form-label">Category Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="categoryName"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">Add Category</button>
            </form>
        </div>
    );
};

export default AddCategory;
