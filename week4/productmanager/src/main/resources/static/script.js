const API_URL = 'http://localhost:8080/api';
let currentProductPage = 0;
let currentCategoryPage = 0;
let currentCategoryId = null;

$(document).ready(function() {
    loadProducts(currentProductPage);
    loadCategories(currentCategoryPage);

    $('#searchInput').on('input', function() {
        loadProducts(0, $(this).val());
    });
});

function loadProducts(page, search = '') {
    let url = `${API_URL}/products?page=${page}&size=10&sort=name,asc`;
    if (search) {
        url += `&name=${search}`;
    } else if (currentCategoryId) {
        url = `${API_URL}/products/category/${currentCategoryId}?page=${page}&size=10&sort=name,asc`;
    }

    $.ajax({
        url: url,
        type: 'GET',
        success: function(response) {
            displayProducts(response.content);
            updatePagination(response, 'productsPagination', loadProducts);
        },
        error: function(xhr, status, error) {
            showError('Error loading products: ' + error);
        }
    });
}

function loadCategories(page) {
    $.ajax({
        url: `${API_URL}/categories?page=${page}&size=10&sort=name,asc`,
        type: 'GET',
        success: function(response) {
            displayCategories(response.content);
            updatePagination(response, 'categoriesPagination', loadCategories);
        },
        error: function(xhr, status, error) {
            showError('Error loading categories: ' + error);
        }
    });
}

function displayProducts(products) {
    const tbody = $('#productsTable tbody');
    tbody.empty();
    products.forEach(product => {
        tbody.append(`
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>$${product.price.toFixed(2)}</td>
                <td>${product.category.name}</td>
                <td>
                    <button onclick="editProduct(${product.id})" class="btn btn-sm btn-warning">Edit</button>
                    <button onclick="deleteProduct(${product.id})" class="btn btn-sm btn-danger">Delete</button>
                </td>
            </tr>
        `);
    });
}

function displayCategories(categories) {
    const table = $('#categoriesTable');
    table.empty();
    categories.forEach(category => {
        table.append(`
            <tr>
                <td><a href="#" onclick="loadProductsByCategory(${category.id})">${category.name}</a></td>
                <td>
                    <button onclick="editCategory(${category.id})" class="btn btn-sm btn-warning">Edit</button>
                    <button onclick="deleteCategory(${category.id})" class="btn btn-sm btn-danger">Delete</button>
                </td>
            </tr>
        `);
    });
}

function updatePagination(response, elementId, loadFunction) {
    const pagination = $(`#${elementId}`);
    pagination.empty();

    if (!response.first) {
        pagination.append(`<li class="page-item"><a class="page-link" href="#" onclick="${loadFunction.name}(${response.number - 1})">Previous</a></li>`);
    }

    for (let i = 0; i < response.totalPages; i++) {
        pagination.append(`<li class="page-item ${i === response.number ? 'active' : ''}"><a class="page-link" href="#" onclick="${loadFunction.name}(${i})">${i + 1}</a></li>`);
    }

    if (!response.last) {
        pagination.append(`<li class="page-item"><a class="page-link" href="#" onclick="${loadFunction.name}(${response.number + 1})">Next</a></li>`);
    }
}

function loadProductsByCategory(categoryId) {
    currentCategoryId = categoryId;
    loadProducts(0);
}

function editProduct(id) {
    window.location.href = `edit-product.html?id=${id}`;
}

function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        $.ajax({
            url: `${API_URL}/products/${id}`,
            type: 'DELETE',
            success: function() {
                loadProducts(currentProductPage);
            },
            error: function(xhr, status, error) {
                showError('Error deleting product: ' + error);
            }
        });
    }
}

function editCategory(id) {
    window.location.href = `edit-category.html?id=${id}`;
}

function deleteCategory(id) {
    if (confirm('Are you sure you want to delete this category?')) {
        $.ajax({
            url: `${API_URL}/categories/${id}`,
            type: 'DELETE',
            success: function() {
                loadCategories(currentCategoryPage);
            },
            error: function(xhr, status, error) {
                showError('Error deleting category: ' + error);
            }
        });
    }
}

function showError(message) {
    alert(message); // You can replace this with a more sophisticated error display method
}