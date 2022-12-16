# w2m

Hola, aquí dejo algunos datos para la aplicación:

Docker para generar el imagen y contenedor:
    1 - ubicar la termina donde esta la aplicación
    2 - docker build -t "w2m-docker" .
    3 - docker run --name w2m-app -p 8080:8080 w2m-docker:latest
    
Postman:
    - Collection se encuentra en el package Util en common
    - El primero que debes ejecutar es el Post Login.