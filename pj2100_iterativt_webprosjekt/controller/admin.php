<?php
include_once 'model/user.php';

class AdminController {

    public function performCommunityAccessChange() {
        $studentcommunity = $_POST['studentcommunity'];
        $userr = $_POST['user'];
        $access = $_POST['accessright'];
        if (!isset($studentcommunity) || !isset($userr) || !isset($access)) {
            die(header('Location: ' . ROOT_HOST . 'admin'));
        }
        $user = new User();
        $user->setCommunityAccess($userr, $studentcommunity, $access);
        include 'view/admin.php';
    }

    public function performAdminAccessChange() {

    }

    public function getUsers() {
        include_once 'model/user.php';
        $user = new User();
        return $user->getUsers();
    }

    public function getStudentCommunities() {
        include_once 'model/user.php';
        $user = new User();
        return $user->getStudentCommunities();
    }
}
?>