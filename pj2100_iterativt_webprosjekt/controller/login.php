<?php
class LoginController {

    public function performLogin() {
        include 'model/User.php';
        $user = new User();
        $success = $user->doLogin($_POST['email'], md5($_POST['password']));
        if ($success) {
            die(header('Location: ' . ROOT_HOST));
        } else {
            $loginFailed = 'Feil brukernavn eller passord';
        }
        include 'view/login.php';
    }
}
?>