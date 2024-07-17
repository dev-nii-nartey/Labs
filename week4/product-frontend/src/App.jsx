import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import AddCategory from './components/AddCategory';
import AddProduct from './components/AddProduct';
import Products from './components/Products';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/add-category" element={<AddCategory />} />
                <Route path="/add-product" element={<AddProduct />} />
                <Route path="/products" element={<Products />} />
            </Routes>
        </Router>
    );
}

export default App;
