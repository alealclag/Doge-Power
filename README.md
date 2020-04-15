# Doge Power

#### Collar de Perro Inteligente

 

### **Introducción**

Cada vez son más las personas que tienen un animal de compañía; en España, el porcentaje alcanza el 40% (según la Asociación Madrileña de Veterinarios de Animales de Compañía), de los cuales el 60% son de perros. A pesar de un porcentaje tan alto, el mundo de las mascotas y la tecnología se encuentran actualmente muy alejados, lo que deja un amplio campo de ideas para facilitar el cuidado de estos por parte de sus dueños. Es por esto por lo que hemos decidido desarrollar un collar que facilite el cuidado de estos gracias a funciones como localizador GPS, sistema anti-escape y modo amaestramiento entre otros. 

Para su desarrollo, hemos planteado mecanismos que a priori suponemos que serán factibles, tanto a nivel de conocimientos como económico, por lo que es posible que conforme avance el proyecto vayamos añadiendo y/o eliminando funcionalidades.

El producto va destinado principalmente a los dueños de perros de todas las razas, pero su uso también es adecuado para otras mascotas como gatos, hurones, caballos, etc., pero no descartamos que alguien quiera usarlo para, por ejemplo, una iguana.

### **Funcionalidades**

·    Sistema anti-escape

Una de las principales funciones que trataremos de implementar es un sistema por el cual, si se detecta que el perro entra en una zona cercana a una puerta abierta, esta se cerrará. Para ello, una posible implementación sería un sensor en el collar y otro en la puerta, que, al estar a cierta distancia, activa un motor que la cierra. Un detalle para tener en cuenta es que la puerta puede golpear al perro, por ello deberemos cerrar la puerta con poca fuerza o implementar un sensor parecido al de las puertas de cochera, que detecta si hay un objeto en medio para detener el motor.

Además, si el perro consigue escapar, se notificará al dueño de esto. Usaremos un sensor que detecta si el collar se encuentra dentro de cierta zona, y para notificar usaremos email o vía aplicación.



·    Localizador

Mediante un módulo GPS, podremos obtener la ubicación de la mascota en tiempo real. El usuario mandará una petición al servidor, y este obtendrá la ubicación aproximada.



·    Luz

El usuario podrá enviar una petición para encender un led y así poder encontrar a su perro en situaciones de poca visibilidad

 

·    Alerta por alarma y streaming

El collar implementará un micrófono que, en caso de detectar un ruido fuerte, enviará una notificación al usuario, que podrá comenzar una conexión para escuchar lo que ocurre. El objetivo es conocer cuando el perro detecta un peligro, como por ejemplo un intruso, ya que este comenzará a ladrar.

 

·    Sensor ahorcamiento 

En caso de que el collar quede enganchado y exista peligro de asfixia, se enviará una notificación al usuario. Se colocará un sensor de presión en el cuello del perro para detectarlo.

 

·    Chip NFC 

En caso de perderse, se podrá escanear un chip NFC con datos importantes para que pueda ser llevado con su dueño, ya que así no es necesario llevarlo a un veterinario para que lea el típico chip que llevan.

 

·    Modo amaestramiento

El collar tendrá implementado un motor de vibración para funcionar como los collares de amaestramiento. Simplemente el usuario mandará una señal para que el collar vibre con distinta intensidad según la necesidad (limitado para no herir al perro) y así amaestrarlo.

 

·    App (Opcional) 

En caso de disponer del suficiente tiempo, se tratará de implementar una app para dispositivos móviles para el uso del collar y de las notificaciones, en lugar de una web y notificaciones por email.

 

### **Base de datos**

![](https://github.com/alealclag/Doge-Power/blob/Modificaciones-Ale/Doge-power%20Diagrama%20UML.png)

La base de datos diseñada para nuestro proyecto es relativamente simple, puesto que la única información que almacenaremos será la de los usuarios, dispositivos, sensores y actuadores. Para ello, hemos creado una tabla para cada uno, excepto para los dos últimos, que presentan tablas para la información del sensor/actuador, y por separado tablas para los valores. Estos, por regla general, solo precisan del id del sensor, el valor y el timestamp, pero luego existen otros distintos, como es por ejemplo la ubicación, que precisa de dos coordenadas, además de otras extras. El resultado es el siguiente diagrama UML:

 


Para casi todas las tablas hemos usado solo atributos básicos con el objetivo de obtener una base de datos simple y que no nos lleve una gran cantidad de tiempo trabajar con ella. Como hemos indicado anteriormente, para sensor y actuator tenemos atributos tales como el dispositivo al que pertenecen, el nombre del sensor/actuador (pressure, led, etc.) y el tipo. Con el tipo nos referimos a si es básico (sensor/actuador, valor y timestamp) o no, que en nuestro caso son el tipo Location, ya que estos sensores recogen el valor de las coordenadas en x e y, y Distance, que además de recoger el valor de la distancia, también recoge si el dispositivo se encuentra dentro o fuera de la zona, que suele ser una casa, de ahí el nombre del atributo “distance_to_door”.

