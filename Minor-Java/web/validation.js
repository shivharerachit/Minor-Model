// Input validation utilities
var validators = {
    phone: function(value) {
        var regex = /^[0-9]{10}$/;
        return {
            isValid: regex.test(value),
            message: 'Please enter a valid 10-digit mobile number'
        };
    },
    
    aadhar: function(value) {
        var regex = /^[0-9]{12}$/;
        return {
            isValid: regex.test(value),
            message: 'Please enter a valid 12-digit Aadhar number'
        };
    },
    
    email: function(value) {
        var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return {
            isValid: regex.test(value),
            message: 'Please enter a valid email address'
        };
    },
    
    username: function(value) {
        var regex = /^[a-zA-Z0-9_]{4,20}$/;
        return {
            isValid: regex.test(value),
            message: 'Username must be 4-20 characters and can contain letters, numbers, and underscores'
        };
    },
    
    age: function(value) {
        var age = parseInt(value, 10);
        return {
            isValid: age >= 18 && age <= 120,
            message: 'Age must be between 18 and 120'
        };
    }
};

// Function to show error message
function showError(input, message) {
    var errorDiv = input.parentElement.querySelector('.error-message') || 
                    Object.assign(document.createElement('div'), {
                        className: 'error-message'
                    });
    errorDiv.textContent = message;
    input.parentElement.appendChild(errorDiv);
    input.classList.add('error');
}

// Function to clear error message
function clearError(input) {
    var errorDiv = input.parentElement.querySelector('.error-message');
    if (errorDiv) errorDiv.remove();
    input.classList.remove('error');
}
