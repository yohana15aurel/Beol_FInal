<?php

    //Filename  : connection.php
    //Date      : 16 Feb 2020
    //Developer : MoCar
    
    //CONNECTION CONFIGURATION
    $host = "localhost";
    $db   = "prob7812_mit";
    $user = "prob7812_mit"; //SESUAIKAN DENGAN KONFIGURASI PADA LAPTOP MASING-MASING
    $pwd  = "prob7812_mit";     //SESUAIKAN DENGAN KONFIGURASI PADA LAPTOP MASING-MASING
	
    //CONNECTION STRING
    $conn = new mysqli($host,$user,$pwd,$db) or die ("Failed");
	
?>