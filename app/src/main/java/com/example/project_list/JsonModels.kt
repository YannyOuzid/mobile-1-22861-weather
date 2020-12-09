package com.example.project_list

class WeatherCoord (val lat: Double, var lon: Double)

class WeatherMain (val temp: Double, val feels_like: Double, val pressure: Double, val humidity: Double)

class Weatharray( val id: Int, val main: String, val description: String)

class WeatherStation (val main: WeatherMain, val weather: List<Weatharray>, val id: Int, val name: String, val dt: Int)

class Weather(val list: List<WeatherStation>)
