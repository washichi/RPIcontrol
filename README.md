RPIcontrol

simple android application to control a RGB led, connected to de Raspberry PI GPIO.
Uses the pigpiod library to implement PWM and to send RGB values.

The android app sends HTTP get requests to a .php page hosted on the Raspberry PI. 
The .php page handles the requests and calls .py scripts.

to get this working I followed this tutorial: http://www.instructables.com/id/Control-Raspberry-Pi-GPIO-Using-an-App/
