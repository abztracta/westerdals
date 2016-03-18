<?php
session_start();

include_once 'config.php';

$filename = $_GET['url'];

if (strcmp($filename, 'error') == 0) {
    include 'view/error.php';
    die();
}

include 'view/header.php';
switch ($filename) {
    case '':
        include 'view/main.php';
        break;
    case 'index.php':
        include 'index.php';
        break;
    case 'login':
        include 'view/login.php';
        break;
    case 'nyheter':
        include 'view/news.php';
        break;
    case 'registrering':
        include 'view/registration.php';
        break;
    case 'kalender':
        include  'view/calendar.php';
        break;
    case 'kontakt':
        include 'view/contact.php';
        break;
    case 'studentliv':
        include 'view/studentlife.php';
        break;
    case'ny-aktivitet':
        include 'view/newactivity.php';
        break;
    case 'admin':
        include 'view/admin.php';
        break;
    case 'minprofil':
        include 'view/myprofile.php';
        break;
    case 'loginEvent':
        include 'controller/login.php';
        $controller = new LoginController();
        $controller->performLogin();
        break;
    case 'logoutEvent':
        include 'controller/logout.php';
        $controller = new LogoutController();
        $controller->performLogout();
        break;
    case 'registrationEvent':
        include 'controller/registration.php';
        $controller = new RegistrationController();
        $controller->performRegistration();
        break;
    case 'endrePassordEvent':
        include 'controller/profile.php';
        $controller = new ProfileController();
        $controller->performPasswordChange();
        break;
    case 'slettBrukerEvent':
        include 'controller/profile.php';
        $controller = new ProfileController();
        $controller->performDeleteAccount();
        break;
    case 'newActivityEvent':
        include 'controller/activity.php';
        $controller = new ActivityController();
        $controller->createNewActivity();
        break;
    case 'hostSignup':
        include 'controller/hostSignup.php';
        $controller = new hostSignupController();
        $controller->performSignup();
        break;
    default:
        die(header('Location: error'));
        break;
}
include 'view/footer.php';

?>