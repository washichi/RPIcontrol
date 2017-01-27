#! /usr/bin/env python
import RPi.GPIO as GPIO
import sys
import time
 
# RGB LED pinnen configureren.
pinRed = 17
pinGreen = 27
pinBlue = 22
 
# GPIO setup.
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
 
# Zet de GPIO pinnen als uitgang.
GPIO.setup(pinRed, GPIO.OUT)
GPIO.setup(pinGreen, GPIO.OUT)
GPIO.setup(pinBlue, GPIO.OUT)
 
# Gebruik PWM op de pinnen.
RED = GPIO.PWM(pinRed, 1000)
GREEN = GPIO.PWM(pinGreen, 1000)
BLUE = GPIO.PWM(pinBlue, 1000)
RED.start(0)
GREEN.start(0)
BLUE.start(0)
 
if len(sys.argv) > 3:
  # converteer de waarde 255 tot max 100 voor PWM.
  redvalue = (int(sys.argv[1]) * 100) / 255
  greenvalue = (int(sys.argv[2]) * 100) / 255
  bluevalue = (int(sys.argv[3]) * 100) / 255
 
  RED.ChangeDutyCycle(redvalue)
  GREEN.ChangeDutyCycle(greenvalue)
  BLUE.ChangeDutyCycle(bluevalue)
  time.sleep(300)
  #RED.stop()
  #GREEN.stop()
  #BLUE.stop()
 
#GPIO.cleanup()