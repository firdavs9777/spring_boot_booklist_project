// Edit book function with modal
function editBook(button) {
    const bookId = button.getAttribute('data-id');
    const card = button.closest('.book-card');
    
    // Get current book data from the card
    const title = card.querySelector('h3').textContent;
    const author = card.querySelector('.book-author').textContent.replace('by ', '');
    const description = card.querySelector('.book-description')?.textContent || '';
    
    // Get ISBN and Reader if they exist
    const metaItems = card.querySelectorAll('.meta-item span');
    let isbn = '';
    let reader = '';
    
    metaItems.forEach(item => {
        const text = item.parentElement.textContent;
        if (text.includes('📘')) {
            isbn = item.textContent;
        }
        if (text.includes('👤')) {
            reader = item.textContent;
        }
    });
    
    // Show modal with form
    showEditModal(bookId, title, author, description, isbn, reader);
}

function showEditModal(id, title, author, description, isbn, reader) {
    // Create modal HTML
    const modalHTML = `
        <div id="editModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>✏️ Edit Book</h2>
                    <span class="close" onclick="closeEditModal()">&times;</span>
                </div>
                <form id="editBookForm" onsubmit="submitEdit(event, ${id})">
                    <div class="form-group">
                        <label for="edit-title">Title *</label>
                        <input type="text" id="edit-title" value="${title}" required/>
                    </div>
                    <div class="form-group">
                        <label for="edit-author">Author *</label>
                        <input type="text" id="edit-author" value="${author}" required/>
                    </div>
                    <div class="form-group">
                        <label for="edit-isbn">ISBN</label>
                        <input type="text" id="edit-isbn" value="${isbn}"/>
                    </div>
                    <div class="form-group">
                        <label for="edit-reader">Reader</label>
                        <input type="text" id="edit-reader" value="${reader}"/>
                    </div>
                    <div class="form-group">
                        <label for="edit-description">Description</label>
                        <textarea id="edit-description" rows="3">${description}</textarea>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-secondary" onclick="closeEditModal()">Cancel</button>
                        <button type="submit" class="btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    `;
    
    // Add modal to page
    document.body.insertAdjacentHTML('beforeend', modalHTML);
}

function closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
        modal.remove();
    }
}

function submitEdit(event, bookId) {
    event.preventDefault();
    
    const updatedBook = {
        title: document.getElementById('edit-title').value,
        author: document.getElementById('edit-author').value,
        isbn: document.getElementById('edit-isbn').value,
        reader: document.getElementById('edit-reader').value,
        description: document.getElementById('edit-description').value
    };
    
    // Send PUT request
    fetch(`/api/books/${bookId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedBook)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Update failed');
    })
    .then(data => {
        closeEditModal();
        // Reload page to show updated data
        window.location.reload();
    })
    .catch(error => {
        alert('Error updating book: ' + error.message);
    });
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        closeEditModal();
    }
}

// Smooth scroll for form validation errors
document.addEventListener('DOMContentLoaded', function() {
    const alertError = document.querySelector('.alert-error');
    if (alertError) {
        alertError.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
});

console.log('Script loaded successfully!');