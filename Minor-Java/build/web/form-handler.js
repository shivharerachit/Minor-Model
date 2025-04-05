function FormHandler(form) {
    this.form = form;
    this.data = new FormData();
}

// Method to collect data from the form step
FormHandler.prototype.collectStepData = function (step) {
    var stepElement = document.querySelector('[data-step="' + step + '"]');
    var inputs = stepElement.querySelectorAll('input, select');

    var _this = this;  // Preserve the context of 'this'

    inputs.forEach(function (input) {
        if (input.type === 'file') {
            if (input.files[0]) {
                _this.data.append(input.id, input.files[0]);
            }
        } else if (input.type === 'checkbox') {
            _this.data.append(input.id, input.checked);
        } else {
            _this.data.append(input.id, input.value);
        }
    });
};

//// Method to submit the form data via an API
//FormHandler.prototype.submitForm = function () {
//    var _this = this;  // Preserve the context of 'this'
//
//    return new Promise(function (resolve, reject) {
//        // Implement API call here
//        fetch('/api/register', {
//            method: 'POST',
//            body: _this.data
//        })
//        .then(function (response) {
//            if (!response.ok) {
//                throw new Error('Registration failed');
//            }
//            return response.json();
//        })
//        .then(function (data) {
//            resolve(data);
//        })
//        .catch(function (error) {
//            console.error('Registration error:', error);
//            reject(error);
//        });
//    });
//};
