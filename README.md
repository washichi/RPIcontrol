RPIcontrol

simple android application to control a RGB led, connected to de Raspberry PI GPIO. uses the pigpiod library to implement PWM and to send RGB values.

The android app sends HTTP get requests to a .php page hosted on the Raspberry PI. the .php page handles the requests and calls .py scripts.
