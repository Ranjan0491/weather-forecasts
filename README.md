# Spring Boot with JPA app

## Required tools
1. [Java 17](https://adoptopenjdk.net/)
2. [MySQL](https://dev.mysql.com/downloads/mysql/)
3. [Docker](https://www.docker.com/products/docker-desktop/)

##### Features
This project uses a hexagonal architecture pattern and follows an API first approach. Following functionalities are available in the application via requests:

- **GET** http request that returns a list of all weather forecast stored in the database in sorted order.
- **POST** http request that stores a new weather forecast data in the database.

##### How to build the project?
1. Run `mvn clean install`
2. This will also create a docker image `celonis/weather-api:latest`

##### How to run from local?
1. Add a jvm argument `-Dspring.profiles.active=local`
2. Run the main class in `WeatherApiApplication.java`

##### How to run from docker?
1. Run `docker compose -f docker-compose.yaml --profile app up`. This will start the application and mysql containers. The application is accessible from `http://localhost:8080`
2. If only MySQL container is required run `docker compose -f docker-compose.yaml up`

##### Invoke APIs for storing and accessing weather forecasts
1. Fetch and store weather forecast for today and tomorrow:
    Use `POST http://localhost:8080/v1/weather-forecasts?city=Madrid`
    ```
   [
        {
            "city": "Madrid",
            "updatedDate": "2025-04-11",
            "maxTemperatureCentigrade": 18.0,
            "minTemperatureCentigrade": 10.9,
            "totalPrecipitationMilliMeter": 1.31,
            "averageHumidity": 66,
            "condition": "Patchy rain nearby"
        },
        {
            "city": "Madrid",
            "updatedDate": "2025-04-12",
            "maxTemperatureCentigrade": 19.6,
            "minTemperatureCentigrade": 10.2,
            "totalPrecipitationMilliMeter": 1.96,
            "averageHumidity": 73,
            "condition": "Patchy rain nearby"
        }
    ]
   ```
2. List weather forecast data in sorted order
    Use `GET http://localhost:8080/v1/weather-forecasts`
    ```
   [
        {
            "city": "Madrid",
            "updatedDate": "2025-04-11",
            "maxTemperatureCentigrade": 18.0,
            "minTemperatureCentigrade": 10.9,
            "totalPrecipitationMilliMeter": 1.31,
            "averageHumidity": 66,
            "condition": "Patchy rain nearby"
        },
        {
            "city": "Madrid",
            "updatedDate": "2025-04-12",
            "maxTemperatureCentigrade": 19.6,
            "minTemperatureCentigrade": 10.2,
            "totalPrecipitationMilliMeter": 1.96,
            "averageHumidity": 73,
            "condition": "Patchy rain nearby"
        }
     ]
   ```

##### Applications properties
| Name                    | Value         |
|-------------------------|---------------|
| Application name        | `weather-api` |
| Application Port        | `8080`        |
| Docker Application Port | `8080`        |
| MySQL Port              | `3306`        |

##### Improvements
1. The hexagonal architecture components can be divided in multi-module project structure with a parent pom.
2. Change RestTemplate to FeignClient.
3. More tests can be introduced to have better coverage.
4. The API key can be encrypted using a secret.