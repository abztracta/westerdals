<?php
include_once 'model/user.php';

class AccessController {

    public function getAccessLevel() {
        $user = new User();
        $statement = $user->isAdmin($_SESSION['uid']);
        $result = $statement->fetch();
        if ($result->access_level == 0) {
            return false;
        } else {
            return true;
        }
    }

    public function getCommunityLevel() {
        $user = new User();
        $statement = $user->isCommunityLeader($_SESSION['uid']);
        if ($statement->rowCount() == 0) {
            return false;
        } else {
            $result = $statement->fetch();
            if ($result->leader == 0) {
                return false;
            } else {
                return true;
            }
        }
    }
}
?>