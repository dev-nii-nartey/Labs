$(document).ready(function() {
    // Load categories for the dropdown
    $.ajax({
        url: 'http://localhost:8080/api/categories/all',
        type: 'GET',
        success: function(categories) {
            categories.forEach(function(category) {
                $('#productCategory').append($('<option>', {
                    value: category.id,
                    text: category.name
                }));
            });
        },
        error: function(xhr, status, error) {
            alert('Error loading categories: ' + error);
        }
    });

    $('#addProductForm').submit(function(e) {
        e.preventDefault();

        const product = {
            name: $('#productName').val(),
            description: $('#productDescription').val(),
            price: parseFloat($('#productPrice').val()),
            category: { id: parseInt($('#productCategory').val()) }
        };

        $.ajax({
            url: 'http://localhost:8080/api/products',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(product),
            success: function(response) {
                alert('Product added successfully!');
                window.location.href = 'index.html';
            },
            error: function(xhr, status, error) {
                alert('Error adding product: ' + error);
            }
        });
    });
});