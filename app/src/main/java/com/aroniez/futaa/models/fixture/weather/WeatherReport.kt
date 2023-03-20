package com.aroniez.futaa.models.fixture.weather

import java.io.Serializable

class WeatherReport(
        val code: String,
        val type: String,
        val icon: String,
        val temperature: Temperature,
        val temperature_celcius: Temperature,
        val clouds: String,
        val humidity: String,
        val pressure: Double,
        val updated_at: String
) : Serializable