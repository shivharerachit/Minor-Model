from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
import joblib
import numpy as np
import pandas as pd
from get_weather import fetch_weather
from datetime import datetime

# Get the current month
current_month = datetime.now().month

# Initialize FastAPI app
app = FastAPI()

# Load the trained model and necessary encoders/scalers
model = joblib.load("crop_model.pkl")
scaler = joblib.load("scaler.pkl")
label_encoder = joblib.load("label_encoder.pkl")





# List of unique states (used for one-hot encoding)
unique_states = [
    "Andaman and Nicobar", "Andhra Pradesh", "Assam", "Chattisgarh",
    "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
    "Jammu and Kashmir", "Karnataka", "Kerala", "Madhya Pradesh",
    "Maharashtra", "Manipur", "Meghalaya", "Nagaland", "Odisha",
    "Pondicherry", "Punjab", "Rajasthan", "Tamil Nadu", "Telangana",
    "Tripura", "Uttar Pradesh", "Uttrakhand", "West Bengal"
]

# Define the input schema
class CropInput(BaseModel):
    N_SOIL: float
    P_SOIL: float
    K_SOIL: float
    ph: float
    STATE: str  # Input state name
    CITY: str  # Input city name
    # TEMPERATURE: float
    # HUMIDITY: float
    # RAINFALL: float

# Root endpoint
@app.get("/")
def read_root():
    return {"message": "Welcome to the Crop Prediction API!"}

# Prediction endpoint
@app.post("/predict")
async def predict_crop(input_data: CropInput):
    try:
        # Ensure the provided state exists in the unique states list
        if input_data.STATE not in unique_states:
            raise HTTPException(
                status_code=400,
                detail=f"Invalid STATE value. Please choose from: {', '.join(unique_states)}"
            )

        # Convert state to one-hot encoding
        state_features = [1 if state == input_data.STATE else 0 for state in unique_states]

        # Fetch weather data
        weather_data = await fetch_weather(input_data.STATE, input_data.CITY)
        

        # Extract numerical features
        # First 10 Days
        f_numerical_features = [
            input_data.N_SOIL, input_data.P_SOIL, input_data.K_SOIL,
            weather_data['f_avg_temp'], weather_data['f_avg_humidity'],
            input_data.ph, weather_data['f_avg_rainfall']
        ]

        # Second 10 Days
        s_numerical_features = [
            input_data.N_SOIL, input_data.P_SOIL, input_data.K_SOIL,
            weather_data['s_avg_temp'], weather_data['s_avg_humidity'],
            input_data.ph, weather_data['s_avg_rainfall']
        ]

        # Third 10 Days
        t_numerical_features = [
            input_data.N_SOIL, input_data.P_SOIL, input_data.K_SOIL,
            weather_data['t_avg_temp'], weather_data['t_avg_humidity'],
            input_data.ph, weather_data['t_avg_rainfall']
        ]
        

        # Normalize numerical features only
        # First 10 Days
        f_normalized_numerical_features = scaler.transform([f_numerical_features])[0]

        # Second 10 Days
        s_normalized_numerical_features = scaler.transform([s_numerical_features])[0]

        # Third 10 Days
        t_normalized_numerical_features = scaler.transform([t_numerical_features])[0]

        # Combine normalized numerical features and one-hot-encoded state features
        # First 10 Days
        f_combined_features = np.concatenate((f_normalized_numerical_features, state_features)).reshape(1, -1)

        # Second 10 Days
        s_combined_features = np.concatenate((s_normalized_numerical_features, state_features)).reshape(1, -1)

        # Third 10 Days
        t_combined_features = np.concatenate((t_normalized_numerical_features, state_features)).reshape(1, -1)

        # Make prediction
        # First 10 Days
        f_prediction = model.predict(f_combined_features)

        # Second 10 Days
        s_prediction = model.predict(s_combined_features)

        # Third 10 Days
        t_prediction = model.predict(t_combined_features)

        # prediction = model.predict(combined_features)

        # Decode the prediction
        # First 10 Days
        f_predicted_crop = label_encoder.inverse_transform(f_prediction)[0]

        # Second 10 Days
        s_predicted_crop = label_encoder.inverse_transform(s_prediction)[0]

        # Third 10 Days
        t_predicted_crop = label_encoder.inverse_transform(t_prediction)[0]

        # predicted_crop = label_encoder.inverse_transform(prediction)[0]

        return {
            "f_predicted_crop": f_predicted_crop,
            "s_predicted_crop": s_predicted_crop,
            "t_predicted_crop": t_predicted_crop
        }

    except ValueError as ve:
        raise HTTPException(status_code=400, detail=str(ve))
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))