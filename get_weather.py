import requests
import os
from dotenv import load_dotenv

load_dotenv()

def get_weather(city):
    api_key = os.getenv('weather_api')
    base_url = "https://pro.openweathermap.org/data/2.5/forecast/climate"
    params = {
        'q': city,
        'appid': api_key,
        'cnt': 30,
        'units': 'metric',
        'lang': 'en'
    }

    response = requests.get(base_url, params=params)
    if response.status_code == 200:
        data = response.json()
        if 'list' in data and len(data['list']) > 0:
            weather = data['list'][10]
            temperature = weather['temp']['day']
            humidity = weather['humidity']
            rainfall = weather.get('rain', {}).get('1h', 0.0)  
            return {
                'Temperature': temperature, 
                'Humidity': humidity, 
                'Rainfall': rainfall
                }
        else:
            return "No weather data available"
    else:
        return "Failed to get weather data"

if __name__ == "__main__":
    city = "Indore"
    weather_data = get_weather(city)
    if weather_data:
        print(weather_data)
    else:
        print("Failed to get weather data")