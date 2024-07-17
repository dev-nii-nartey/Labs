import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchProducts = (searchTerm = '') => {
    return axios.get(`${API_BASE_URL}/products?search=${searchTerm}`);
};

export const fetchCategories = () => {
    return axios.get(`${API_BASE_URL}/categories`);
};

export const deleteProduct = (id) => {
    return axios.delete(`${API_BASE_URL}/products/${id}`);
};

export const deleteCategory = (id) => {
    return axios.delete(`${API_BASE_URL}/categories/${id}`);
};

export const createCategory = (category) => {
    return axios.post(`${API_BASE_URL}/categories`, category);
};

export const createProduct = (product) => {
    return axios.post(`${API_BASE_URL}/products`, product);
};
