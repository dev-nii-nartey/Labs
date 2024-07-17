$(document).ready(function() {
    $('#addCategoryForm').submit(function(e) {
        e.preventDefault();

        const categoryName = $('#categoryName').val();

        $.ajax({
            url: 'http://localhost:8080/api/categories',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ name: categoryName }),
            success: function(response) {
                alert('Category added successfully!');
                window.location.href = 'index.html';
            },
            error: function(xhr, status, error) {
                alert('Error adding category: ' + error);
            }
        });
    });
});