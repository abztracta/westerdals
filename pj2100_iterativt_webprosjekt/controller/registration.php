<?php
class RegistrationController {

    public function performRegistration() {
        if (empty($_POST)) {
            die(header('Location: ' . ROOT_HOST));
        }
        $email = $_POST['email'];
        $password = $_POST['password'];
        $retype = $_POST['retype'];
        $firstname = $_POST['firstname'];
        $lastname = $_POST['lastname'];
        $dateofbirth = $_POST['dateofbirth'];
        $studentprogram = $_POST['studentprogram'];

        include_once 'model/user.php';
        $user = new User();
        $canRegister = true;
        if ($user->isUserRegistered($email)) {
            $incorrectEmail = 'Eposten er allerede registrert.';
            $canRegister = false;
        }
        if (strlen($email) < 1) {
            $incorrectEmail = 'Du må skrive inn en epost.';
            $canRegister = false;
        }
        if ($this->isInvalidPassword($password, $retype)) {
            $incorrectPassword = 'Ugyldig passord.';
            $canRegister = false;
        }
        if (strlen($firstname) < 1) {
            $incorrectFirstname = 'Du må ha et fornavn.';
            $canRegister = false;
        }
        if (strlen($lastname) < 1) {
            $incorrectLastname = 'Du må ha et etternavn.';
            $canRegister = false;
        }
        if ($canRegister) {
            $user->registerUser($email, md5($password), $firstname, $lastname, $dateofbirth, $studentprogram);
            $success = 'Gratulerer, du ble registrert.';
        }
        include 'view/registration.php';
    }

    private function isInvalidPassword($password, $retype) {
        return strcmp($password, $retype) != 0 || strlen($password) <= 6;
    }

    public function getStudentPrograms() {
        include_once 'model/user.php';
        $user = new User();
        return $user->getStudentPrograms();
    }
}
?>