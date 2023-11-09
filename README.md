# DavidPDF - HTML2PDF by Spring Boot, Selenium & Docker
Microservice that converts HTML to PDF

There are two main root endpoints. Local & Docker

Option Docker:
- Clone the repository (I left the target on purpose).
- Launch the docker-compose.yml

End.

Tips on Docker:
- Image of selenium are for my mac m1. Consider taking a look https://github.com/seleniumhq-community/docker-seleniarm
- Dockerfile does not compile or build the application. It only wraps the jar file. So you need to mvn clean compile install.
- If you make any modifications, remember to prune the old image of DavidPDF, if not modifications will not be shown


Option Local:
- Install Chrome version 118 or 119. (It does not mind...)
- In case of newer versions, please use this https://googlechromelabs.github.io/chrome-for-testing/ and replace my chromedriver.
- Click once on the chromedriver so it gets installed.
- Run the Spring Boot app.










