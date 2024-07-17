import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ViewAllCategories = () => {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/categories/all');
            setCategories(response.data);
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this category?')) {
            try {
                await axios.delete(`http://localhost:8080/api/categories/${id}`);
                fetchCategories();
            } catch (error) {
                alert('Error deleting category: ' + error.response?.data?.message || error.message);
            }
        }
    };

    return (
        <div className="container">
            <h1>All Categories</h1>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {categories.map(category => (
                    <tr key={category.id}>
                        <td>{category.id}</td>
                        <td>{category.name}</td>
                        <td>
                            <button className="btn btn-sm btn-warning me-2">Edit</button>
                            <button className="btn btn-sm btn-danger" onClick={() => handleDelete(category.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ViewAllCategories;