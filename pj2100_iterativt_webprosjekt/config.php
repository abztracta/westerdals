<?php
$host = 'localhost';
$username = 'root';
$password = '';
$schema = 'pj2100';
$options = array(
    PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8',
);

$connection = null;
try {
    $connection = new PDO("mysql:host=$host;dbname=$schema", $username, $password, $options);
} catch (Exception $e) {
    die(include 'view/error.php');
}
@define('ROOT_HOST', 'localhost');

?>