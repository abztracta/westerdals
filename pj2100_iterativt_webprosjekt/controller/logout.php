<?php
class LogoutController {

    public function performLogout() {
        include_once "model/user.php";
        $user = new User();
        $user->destroySession();
        die(header('Location: ' . ROOT_HOST));
    }
}
?>