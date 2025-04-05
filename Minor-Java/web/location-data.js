var states = [
    'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh',
    'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh', 'Jharkhand', 'Karnataka',
    'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur', 'Meghalaya', 'Mizoram',
    'Nagaland', 'Odisha', 'Punjab', 'Rajasthan', 'Sikkim', 'Tamil Nadu',
    'Telangana', 'Tripura', 'Uttar Pradesh', 'Uttarakhand', 'West Bengal'
];

var cities = {
    'Maharashtra': ['Mumbai', 'Pune', 'Nagpur', 'Nashik', 'Aurangabad'],
    'Karnataka': ['Bangalore', 'Mysore', 'Hubli', 'Mangalore', 'Belgaum'],
    // Add more cities for each state
};

// Function to populate location dropdowns
function populateLocationDropdowns() {
    var stateSelect = document.getElementById('state');
    var citySelect = document.getElementById('city');

    // Populate states
    for (var i = 0; i < states.length; i++) {
        var state = states[i];
        var option = new Option(state, state);
        stateSelect.add(option);
    }

    // Handle city population on state change
    stateSelect.addEventListener('change', function () {
        citySelect.innerHTML = '<option value="">Select City</option>';
        var selectedState = stateSelect.value;
        
        if (selectedState && cities[selectedState]) {
            for (var j = 0; j < cities[selectedState].length; j++) {
                var city = cities[selectedState][j];
                var option = new Option(city, city);
                citySelect.add(option);
            }
            citySelect.disabled = false;
        } else {
            citySelect.disabled = true;
        }
    });
}
