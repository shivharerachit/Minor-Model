import pandas as pd
from datetime import datetime

# Load the rainfall data
file_path = 'district_wise_rainfall_normal.csv'
data = pd.read_csv(file_path)

# Get the current month
current_month = datetime.now().month

# Map month numbers to column names
month_map = {
    1: 'JAN', 2: 'FEB', 3: 'MAR', 4: 'APR', 5: 'MAY', 6: 'JUN',
    7: 'JUL', 8: 'AUG', 9: 'SEP', 10: 'OCT', 11: 'NOV', 12: 'DEC'
}

# Function to fetch rainfall for the current month based on the given state and district
def get_rainfall_for_state_district(state, district):
    if current_month < 1 or current_month > 12:
        raise ValueError(f"Invalid month: {current_month}")
    state = state.upper()
    district = district.upper()
    # Filter the data for the given state and district
    state_district_data = data[(data['STATE_UT_NAME'] == state) & (data['DISTRICT'] == district)]
    
    # Extract the rainfall data for the current month
    month_column_1 = month_map[current_month] 
    # print(month_column_1)
    month_column_2 = month_map[(current_month % 12) + 1] 
    # print(month_column_2)
    month_column_3 = month_map[((current_month + 1) % 12) + 1]
    # print(month_column_3)
    month_column_4 = month_map[((current_month + 2) % 12) + 1]
    # print(month_column_4)
    month_column_5 = month_map[((current_month + 3) % 12) + 1]
    # print(month_column_5)
    month_column_6 = month_map[((current_month + 4) % 12) + 1]
    # print(month_column_6)
    # month_column = month_map[current_month] + month_map[current_month + 1] + month_map[current_month +1]

    # print(state_district_data[month_column_1].values[0], state_district_data[month_column_2].values[0], state_district_data[month_column_3].values[0], state_district_data[month_column_4].values[0], state_district_data[month_column_5].values[0], state_district_data[month_column_6].values[0])

    rainfall = state_district_data[month_column_1].values[0] + state_district_data[month_column_2].values[0] + state_district_data[month_column_3].values[0] + state_district_data[month_column_4].values[0] + state_district_data[month_column_5].values[0] + state_district_data[month_column_6].values[0]
    
    return rainfall


# Example usage
# state = "Madhya pradesh"
# district = "indore"
# rainfall = get_rainfall_for_state_district(state, district)
# print(f"Rainfall for {state}, {district} in month {current_month}: {rainfall}")
