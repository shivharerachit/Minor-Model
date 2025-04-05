import requests
import os
import json
import httpx
from dotenv import load_dotenv
from rain_data import get_rainfall_for_state_district
import asyncio

load_dotenv()

def encoder(obj):
    if isinstance(obj, set):
        return list(obj)
    return obj

# def get_weather(city):
#     api_key = os.getenv('weather_api')
#     base_url = "https://pro.openweathermap.org/data/2.5/forecast/climate"
#     params = {
#         'q': city,
#         'appid': api_key,
#         'cnt': 30,
#         'units': 'metric',
#         'lang': 'en'
#     }

#     response = requests.get(base_url, params=params)
#     data = response.json()
#     return data

async def get_weather(city):
    api_key = os.getenv('weather_api')
    base_url = "http://api.openweathermap.org/data/2.5/forecast/climate"
    params = {
        'q': city,
        'cnt': 30,
        'appid': api_key,
        'units': 'metric',
        'lang': 'en'
    }

    async with httpx.AsyncClient() as client:
        response = await client.get(base_url, params=params)
        data = response.json()
    return data

# def fetch_weather(state, city):
    data = get_weather(city)

    rain_data = get_rainfall_for_state_district(state, city)

    w_data = {}

    # First 10 Days
    f_temp = 0
    f_humidity = 0
    f_rainfall = 0
    for i in range(0, 10):
        f_temp += data['list'][i]['temp']['day']
        f_humidity += data['list'][i]['humidity']
        try:
            f_rainfall += data['list'][i]['rain']
        except:
            f_rainfall += 0.0
    
    w_data['f_avg_temp'] = f_temp / 10
    w_data['f_avg_humidity'] = f_humidity / 10
    w_data['f_avg_rainfall'] = rain_data

    # Second 10 Days
    s_temp = 0
    s_humidity = 0
    s_rainfall = 0
    for i in range(10, 20):
        s_temp += data['list'][i]['temp']['day']
        s_humidity += data['list'][i]['humidity']
        try:
            s_rainfall += data['list'][i]['rain']
        except:
            s_rainfall += 0.0
    
    w_data['s_avg_temp'] = s_temp / 10
    w_data['s_avg_humidity'] = s_humidity / 10
    w_data['s_avg_rainfall'] = s_rainfall / 10

    # Third 10 Days
    t_temp = 0
    t_humidity = 0
    t_rainfall = 0
    for i in range(20, 30):
        t_temp += data['list'][i]['temp']['day']
        t_humidity += data['list'][i]['humidity']
        try:
            t_rainfall += data['list'][i]['rain']
        except:
            t_rainfall += 0.0
    
    w_data['t_avg_temp'] = t_temp / 10
    w_data['t_avg_humidity'] = t_humidity / 10
    w_data['t_avg_rainfall'] = t_rainfall / 10
    
    # print(w_data)
    # print(type(w_data))

    # w_json_string = json.dumps(w_data, default=encoder)

    # print(w_json_string)
    # print(type(w_json_string))

    return w_data






async def fetch_weather(state, city):
    data = await get_weather(city)

    w_data = {}

    rain_data = get_rainfall_for_state_district(state, city)

    # First 10 Days
    f_temp = 0
    f_humidity = 0
    for i in range(0, 10):
        f_temp += data['list'][i]['temp']['day']
        f_humidity += data['list'][i]['humidity']
    
    w_data['f_avg_temp'] = f_temp / 10
    w_data['f_avg_humidity'] = f_humidity / 10
    w_data['f_avg_rainfall'] = rain_data

    # Second 10 Days
    s_temp = 0
    s_humidity = 0
    s_rainfall = 0
    for i in range(10, 20):
        s_temp += data['list'][i]['temp']['day']
        s_humidity += data['list'][i]['humidity']
        try:
            s_rainfall += data['list'][i]['rain']
        except KeyError:
            s_rainfall += 0.0
    
    w_data['s_avg_temp'] = s_temp / 10
    w_data['s_avg_humidity'] = s_humidity / 10
    w_data['s_avg_rainfall'] = rain_data


    # Third 10 Days
    t_temp = 0
    t_humidity = 0
    t_rainfall = 0
    for i in range(20, 30):
        t_temp += data['list'][i]['temp']['day']
        t_humidity += data['list'][i]['humidity']
        try:
            t_rainfall += data['list'][i]['rain']
        except KeyError:
            t_rainfall += 0.0
    
    w_data['t_avg_temp'] = t_temp / 10
    w_data['t_avg_humidity'] = t_humidity / 10
    w_data['t_avg_rainfall'] = rain_data

    return w_data

if __name__ == "__main__":
    state = "Madhya Pradesh"
    city = "Indore"
    weather_data = asyncio.run(fetch_weather(state, city))
    print(weather_data)