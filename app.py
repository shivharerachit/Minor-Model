from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
import joblib
import numpy as np
import pandas as pd

# Initialize FastAPI app
app = FastAPI()

# Load the trained model and necessary encoders/scalers
model = joblib.load("gradient_boosting_crop_model.pkl")
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
    TEMPERATURE: float
    HUMIDITY: float
    ph: float
    RAINFALL: float
    STATE: str  # Input state name

# Root endpoint
@app.get("/")
def read_root():
    return {"message": "Welcome to the Crop Prediction API!"}

# Prediction endpoint
@app.post("/predict")
def predict_crop(input_data: CropInput):
    try:
        # Ensure the provided state exists in the unique states list
        if input_data.STATE not in unique_states:
            raise HTTPException(
                status_code=400,
                detail=f"Invalid STATE value. Please choose from: {', '.join(unique_states)}"
            )

        # Convert state to one-hot encoding
        state_features = [1 if state == input_data.STATE else 0 for state in unique_states]

        # Extract numerical features
        numerical_features = [
            input_data.N_SOIL, input_data.P_SOIL, input_data.K_SOIL,
            input_data.TEMPERATURE, input_data.HUMIDITY,
            input_data.ph, input_data.RAINFALL
        ]

        # Normalize numerical features only
        normalized_numerical_features = scaler.transform([numerical_features])[0]

        # Combine normalized numerical features and one-hot-encoded state features
        combined_features = np.concatenate((normalized_numerical_features, state_features)).reshape(1, -1)

        # Make prediction
        prediction = model.predict(combined_features)
        predicted_crop = label_encoder.inverse_transform(prediction)[0]

        return {"predicted_crop": predicted_crop}

    except ValueError as ve:
        raise HTTPException(status_code=400, detail=str(ve))
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
