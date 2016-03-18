<?php
include_once 'model/user.php';

class ProfileController {

    public function performPasswordChange() {
        $oldpw = $_POST['oldpassword'];
        $newpw = $_POST['newpassword'];
        $newretypedpw = $_POST['retypenew'];
        if (!isset($oldpw) || !isset($newpw) || !isset($newretypedpw)) {
            die(header('Location: ' . ROOT_HOST . 'minprofil'));
        }

        $canChangePassword = true;
        if (strcmp($newpw, $newretypedpw) != 0 || strlen($newpw) < 6 || !$this->compareOldNewPassword($oldpw)) {
            $errormsg = 'Feil passord.';
            $isPasswordSuccess = false;
        } else {
            if ($this->changePassword($newpw)) {
                $successmsg = 'Du har byttet passord';
            } else {
                $errormsg = 'Kunne ikke bytte passord.';
                $isPasswordSuccess = false;
            }
        }
        include 'view/myprofile.php';
    }

    private function compareOldNewPassword($oldpw) {
        $user = new User();
        return $user->isEqualPassword($_SESSION['uid'], md5($oldpw));
    }

    private function changePassword($password) {
        $user = new User();
        return $user->setPassword($_SESSION['uid'], md5($password));
    }

    public function performDeleteAccount() {
        if (!isset($_SESSION['uid'])) {
            die(header('Location: ' . ROOT_HOST . 'minprofil'));
        }
        $user = new User();
        if ($user->removeUser($_SESSION['uid'])) {
            $user->destroySession();
            die(header('Location: ' . ROOT_HOST));
        } else {
            $deleteError = 'Kunne ikke slette din konto. Kontakt en administrator.';
        }
        include 'view/myprofile.php';
    }
}
?>