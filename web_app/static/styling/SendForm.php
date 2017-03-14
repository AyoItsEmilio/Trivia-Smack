<?php

    $to = "receiver@domain.com";
    $name = $_POST['name'];
    $email = $_POST['email'];
    $message = $_POST['message'];
    $body = "$message\n\nFrom: $name";

	mail($to, "Subject: Contact Form", $body, "From: Name<email@domain.com>");

?>
