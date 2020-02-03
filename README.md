# Exámen Mercado Libre - Diego Chiari
- [Asumo](#Asumo)
- [Ambiente](#ambiente)
- [Instrucciones](#Instrucciones)
- [URLs](#URLs)
- [Tests](#Tests)

## Asumo
Leyendo el enunciado asumo que se recibe **ya validada** una matriz de nXn y que las letras son (A,T,C,G), por lo que en este código esas dos validaciones no las hago. 
También entiendo que la diagonalidad de una secuencia de letras iguales solo es de **izquierda a derecha**, como en sentido de lectura, por el ejemplo dado.

## Ambiente
El proyecto está hecho con SpringBoot en Java 8, y usa MongoDB 4.2 como Base de datos.
Está subido en heroku.com y la BD en MongoDB Atlas.

## Instrucciones
Es necesario tener maven para levantarlo local, este único comando va a instalar las dependencias necesarias y levantar el repositorio localmente
```
mvn spring-boot:run
```

## URLs
1. **Producción**
[https://meli-challenge-diego-chiari.herokuapp.com/](https://meli-challenge-diego-chiari.herokuapp.com/)

2. **Local**
[http://localhost:8080/](http://localhost:8080/)

## Tests
La verdad nunca tuve oportunidad de trabajar con tests por lo que no creo cumplir el code coverage pedido, hice unos tests básicos probando los dos casos en el endpoint de **/mutant** y su posterior test para el endpoint de **/stats**. También que se hayan guardado los datos en la BD.
Corren contra otra BD distinta de la productiva.
El comando para correr los tests es:
```
mvn test -Dspring.profiles.active=test
```
