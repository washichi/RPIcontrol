<?php 
exec("gpio mode 0 out");
exec("gpio mode 2 out");
exec("gpio mode 3 out");

$ledR = 0;
$ledG = 0;
$ledB = 0;
$RGB = 0;

if (isset($_GET['led1'])) {	
	if($_GET['led1'] == 1) {
		exec("gpio write 0 1");// pin 0 in wiring pi is gpio 17
		$ledR = 255; 
	} else {
		exec("gpio write 0 0");
	}
}
if(isset($_GET['led2'])) {
	if($_GET['led2'] == 1) {
		exec("gpio write 2 1");// pin 2 in wiring pi is gpio 27
		$ledG = 255;
	} else {
		exec("gpio write 2 0");
	}
}
if(isset($_GET['led3'])) {
	if($_GET['led3'] == 1) {
		//exec("gpio write 3 1");// pin 3 in wiring pi is gpio 22
		exec("gpio mode 0 pwm");
		exec("gpio mode 2 pwm");
		exec("gpio mode 3 pwm");
		exec("gpio pwm 0 0");
		exec("gpio pwm 2 1023");
		exec("gpio pwm 3 1023");
		$ledB = 255;
	} else {
		exec("gpio write 3 0");
	}
}

if(isset($_GET['R'])) {
    $ledR = $_GET['R'];
    //RGBhandler();
}

if(isset($_GET['G'])) {
    $ledG = $_GET['G'];
    //RGBhandler();
}

if(isset($_GET['B'])) {
    $ledB = $_GET['B'];
    RGBhandler();

}

function RGBhandler() {
	global $ledR, $ledG, $ledB;
    $command = "sudo python rgb.py " . $ledR . " " . $ledG . " " . $ledB;
    $output = shell_exec($command);
 }



/*
if(isset($_GET['ledRGB'])) {
    $RGBvalue = $_GET['ledRGB'];
    $command = "python rgb.py " + $RGBvalue;
    $output = shell_exec($command);
}
*/

//$output = shell_exec("python fading.py");
?>
