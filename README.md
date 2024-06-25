# DACD_Final_Project
Práctica final de la asignatura DACD

## Academic Information
- **Tittle**: Intelligent Travel Based on Weather.
- **Subject**: DCAD (Data Science Applications Development).
- **Course**: Second Year.
- **Degree**: Data Science and Engeneering.
- **College**: Computers Engeneering and Mathematics.
- **University**: ULPGC (Universidad de las Palmas de Gran Canaria)

## Summary of Functionality
This project implements an intelligent travel planning system that integrates weather and hotel reservation data to offer personalized recommendations to travelers. The platform suggests destinations based on the desired weather during your travel dates and helps book flights and accommodations accordingly. Various APIs (OpenWeatherMap and Xotelo) are used to obtain data in real time and the information is stored in a SQL database for later analysis.

The project is divided into several modules and packages for better organization and separation of responsibilities. The main modules are:
1. **PredictionProvider:** This module is responsible for obtaining weather predictions using the OpenWeatherMap API and publishing this data in a broker topic (ActiveMQ).
2. **HotelProvider:** This module queries the Xotelo API to obtain information about hotel availability and rates and publishes this data in a broker topic.
3. **DatalakeBuilder:** This module subscribes to the broker's topics, consumes the published events and stores the data in a datalake following a temporally organized directory structure.
4. **BusinessUnit:** This module combines information from weather events and hotel reservations, storing them in a SQL database and exploiting the chosen business model to provide recommendations to users.


