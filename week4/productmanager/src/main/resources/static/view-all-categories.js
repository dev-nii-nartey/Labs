$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/api/categories/all',
        type: 'GET',
        success: function(categories) {
            const tbody = $('#allCategoriesTable tbody');
            categories.forEach(function(category) {
                tbody.append(`
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>
                            <button class="btn btn-sm btn-primary edit-category" data-id="${category.id}">Edit</button>
                            <button class="btn btn-sm btn-danger delete-category" data-id="${category.id}">Delete</button>
                        </td>
                    </tr>
                `);
            });
        },
        error: function(xhr, status, error) {
            alert('Error loading categories: ' + error);
        }
    });

    // Edit category
    $(document).on('click', '.edit-category', function() {
        const categoryId = $(this).data('id');
        // Implement edit functionality (e.g., redirect to an edit page)
        alert('Edit category with ID: ' + categoryId);
    });

    // Delete category
    $(document).on('click', '.delete-category', function() {
        const categoryId = $(this).data('id');
        if (confirm('Are you sure you want to delete this category?')) {
            $.ajax({
                url: `http://localhost:8080/api/categories/${categoryId}`,
                type: 'DELETE',
                success: function() {
                    alert('Category deleted successfully!');
                    location.reload();
                },
                error: function(xhr, status, error) {
                    alert('Error deleting category: ' + error);
                }
            });
        }
    });
});