$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/api/products/all',
        type: 'GET',
        success: function(products) {
            const tbody = $('#allProductsTable tbody');
            products.forEach(function(product) {
                tbody.append(`
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.category.name}</td>
                        <td>
                            <button class="btn btn-sm btn-primary edit-product" data-id="${product.id}">Edit</button>
                            <button class="btn btn-sm btn-danger delete-product" data-id="${product.id}">Delete</button>
                        </td>
                    </tr>
                `);
            });
        },
        error: function(xhr, status, error) {
            alert('Error loading products: ' + error);
        }
    });

    // Edit product
    $(document).on('click', '.edit-product', function() {
        const productId = $(this).data('id');
        // Implement edit functionality (e.g., redirect to an edit page)
        alert('Edit product with ID: ' + productId);
    });

    // Delete product
    $(document).on('click', '.delete-product', function() {
        const productId = $(this).data('id');
        if (confirm('Are you sure you want to delete this product?')) {
            $.ajax({
                url: `http://localhost:8080/api/products/${productId}`,
                type: 'DELETE',
                success: function() {
                    alert('Product deleted successfully!');
                    location.reload();
                },
                error: function(xhr, status, error) {
                    alert('Error deleting product: ' + error);
                }
            });
        }
    });
});