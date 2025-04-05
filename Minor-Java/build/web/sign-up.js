function SignUpForm() {
    this.currentStep = 1;
    this.form = document.getElementById('signUpForm');
    this.progressSteps = document.querySelectorAll('.progress-step');
    this.progressLines = document.querySelectorAll('.progress-line');
    this.formSteps = document.querySelectorAll('.form-step');

    this.initializeForm();
}

// Initialize form, set up event listeners and other functionalities
SignUpForm.prototype.initializeForm = function() {
    this.setupEventListeners();
    this.setupLocationDropdowns();
    this.setupProfileUpload();
    this.showStep(1);  // Show the first step on load
};

SignUpForm.prototype.setupEventListeners = function() {
    // Navigation buttons for "Next" and "Previous"
    var nextButtons = document.querySelectorAll('.next-btn');
    for (var i = 0; i < nextButtons.length; i++) {
        nextButtons[i].addEventListener('click', this.nextStep.bind(this));
    }

    var prevButtons = document.querySelectorAll('.prev-btn');
    for (var i = 0; i < prevButtons.length; i++) {
        prevButtons[i].addEventListener('click', this.prevStep.bind(this));
    }

    // Form submission
    this.form.addEventListener('submit', this.handleSubmit.bind(this));

    // Input validation for fields
    var inputs = this.form.querySelectorAll('input, select');
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].addEventListener('blur', this.validateField.bind(this));
        inputs[i].addEventListener('input', this.clearError.bind(this));
    }
};

// Location dropdown setup (State and City)
SignUpForm.prototype.setupLocationDropdowns = function() {
    var stateSelect = document.getElementById('state');
    var citySelect = document.getElementById('city');

    if (stateSelect && citySelect) {
        stateSelect.addEventListener('change', function() {
            this.updateCities(stateSelect.value);
        }.bind(this));
    }
};

// Profile picture upload functionality
SignUpForm.prototype.setupProfileUpload = function() {
    var profileInput = document.getElementById('profilePicture');
    var profilePreview = document.getElementById('profilePreview');

    if (profileInput && profilePreview) {
        profileInput.addEventListener('change', function(e) {
            var file = e.target.files[0];
            if (file) {
                // Validate file type and size (Example: max 5MB)
                if (file.type.startsWith('image/') && file.size <= 5 * 1024 * 1024) {
                    var reader = new FileReader();
                    reader.onload = function(e) {
                        profilePreview.src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                } else {
                    alert('Please upload a valid image (JPG/PNG) with size less than 5MB');
                }
            }
        });
    }
};

// Show the corresponding step
SignUpForm.prototype.showStep = function(step) {
    for (var i = 0; i < this.formSteps.length; i++) {
        if (i + 1 === step) {
            this.formSteps[i].classList.add('active');
        } else {
            this.formSteps[i].classList.remove('active');
        }
    }

    // Update progress bar
    for (var i = 0; i < this.progressSteps.length; i++) {
        if (i < step) {
            this.progressSteps[i].classList.add('active');
        } else {
            this.progressSteps[i].classList.remove('active');
        }
    }

    for (var i = 0; i < this.progressLines.length; i++) {
        if (i < step - 1) {
            this.progressLines[i].classList.add('active');
        } else {
            this.progressLines[i].classList.remove('active');
        }
    }
};

// Validate an individual input field
SignUpForm.prototype.validateField = function(input) {
    var value = input.value ? input.value.trim() : '';  // Safe check for trim
    var isValid = true;
    var errorMessage = '';

    switch (input.id) {
        case 'phone':
            isValid = /^[0-9]{10}$/.test(value);
            errorMessage = 'Please enter a valid 10-digit mobile number';
            break;
        case 'aadhar':
            isValid = /^[0-9]{12}$/.test(value);
            errorMessage = 'Please enter a valid 12-digit Aadhar number';
            break;
        case 'email':
            isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
            errorMessage = 'Please enter a valid email address';
            break;
        // Add more validation cases as needed
    }

    if (!isValid) {
        this.showError(input, errorMessage);
    }

    return isValid;
};

// Show error message for invalid field
SignUpForm.prototype.showError = function(input, message) {
    var errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    if (input.parentElement) {
        input.parentElement.appendChild(errorDiv);
    }
    input.classList.add('error');
};

// Clear error message
SignUpForm.prototype.clearError = function(input) {
    if (input.parentElement) {
        var errorDiv = input.parentElement.querySelector('.error-message');
        if (errorDiv) {
            errorDiv.remove();
            input.classList.remove('error');
        }
    }
};

// Validate fields in the current step before proceeding
SignUpForm.prototype.validateStep = function(step) {
    var currentStepElement = document.querySelector('.form-step[data-step="' + step + '"]');
    if (!currentStepElement) return false;

    var inputs = currentStepElement.querySelectorAll('input[required], select[required]');
    var isValid = true;

    for (var i = 0; i < inputs.length; i++) {
        if (!this.validateField(inputs[i])) {
            isValid = false;
        }
    }

    return isValid;
};

// Move to the next step
SignUpForm.prototype.nextStep = function() {
    if (this.validateStep(this.currentStep)) {
        this.currentStep++;
        this.showStep(this.currentStep);
    }
};

// Move to the previous step
SignUpForm.prototype.prevStep = function() {
    if (this.currentStep > 1) {
        this.currentStep--;
        this.showStep(this.currentStep);
    }
};

// Update city dropdown based on selected state
SignUpForm.prototype.updateCities = function(state) {
    var citySelect = document.getElementById('city');
    if (citySelect) {
        citySelect.innerHTML = '<option value="">Select City</option>';
        
        var cities = {
            'Maharashtra': ['Mumbai', 'Pune', 'Nagpur', 'Nashik'],
            'Karnataka': ['Bangalore', 'Mysore', 'Hubli'],
            // Add more states and cities as needed
        };

        if (cities[state]) {
            for (var i = 0; i < cities[state].length; i++) {
                var option = document.createElement('option');
                option.value = cities[state][i];
                option.textContent = cities[state][i];
                citySelect.appendChild(option);
            }
            citySelect.disabled = false;
        } else {
            citySelect.disabled = true;
        }
    }
};

SignUpForm.prototype.handleSubmit = function (e) {
    if (this.validateStep(this.currentStep)) {
        // Allow the form to submit normally
        return true;
    } else {
        // Prevent submission if validation fails
        e.preventDefault();
    }
};


// Handle form submission
//SignUpForm.prototype.handleSubmit = function(e) {
//    e.preventDefault();
//
//    if (this.validateStep(this.currentStep)) {
//        try {
//            // Prepare form data
//            var formData = new FormData(this.form);
//
//            // Create an XMLHttpRequest object
//            var xhr = new XMLHttpRequest();
//            
//            // Open a POST request
//            xhr.open('POST', '/signup', true);
//
//            // Set up the callback function for when the request completes
//            xhr.onload = function() {
//                if (xhr.status === 200) {
//                    var data = JSON.parse(xhr.responseText);
//                    if (data.success) {
//                        // Show crop details modal
//                        var modal = document.getElementById('cropModal');
//                        modal.classList.add('active');
//
//                        // Handle crop form submission
//                        var cropForm = document.getElementById('cropForm');
//                        cropForm.addEventListener('submit', this.handleCropSubmission.bind(this));
//                    } else {
//                        alert('Submission failed. Please try again.');
//                    }
//                } else {
//                    alert('Submission failed. Please try again.');
//                }
//            }.bind(this); // Bind `this` to refer to the SignUpForm instance
//
//            // Set up the callback function for errors
//            xhr.onerror = function() {
//                console.error('Error:', xhr.statusText);
//                alert('Submission failed. Please try again.');
//            };
//
//            // Send the form data
//            xhr.send(formData);
//
//        } catch (error) {
//            console.error('Registration error:', error);
//            alert('Registration failed. Please try again.');
//        }
//    }
//};

// Handle crop data submission
SignUpForm.prototype.handleCropSubmission = function(e) {
    e.preventDefault();
    var cropData = {
        crop: document.getElementById('currentCrop').value,
        yield: document.getElementById('yield').value
    };

    // Store crop data in localStorage
    localStorage.setItem('cropData', JSON.stringify(cropData));
    
    // Hide modal and redirect
    document.getElementById('cropModal').classList.remove('active');
    this.redirectToDashboard();
};

// Redirect to dashboard after successful submission
SignUpForm.prototype.redirectToDashboard = function() {
    window.location.href = '/dashboard';
};

// Initialize form when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    new SignUpForm();
});
