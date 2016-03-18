<?php
class User {

    public function doLogin($email, $password) {
        global $connection;
        $sql = "SELECT uid, email, firstname, lastname FROM users WHERE email = :email AND password = :password;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':email' => $email, ':password' => $password));
        if ($statement->rowCount() == 1) {
            $data = $statement->fetch();
            $this->startSession($data->uid, $data->email, $data->firstname, $data->lastname);
            return true;
        }
        return false;
    }

    public function isUserRegistered($email) {
        global $connection;
        $sql = "SELECT uid FROM users WHERE email = :email;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':email' => $email));
        return $statement->rowCount() == 1;
    }

    public function registerUser($email, $password, $firstname, $lastname, $dateofbirth, $studentprogram) {
        global $connection;
        $sql = "INSERT INTO users (email, password, firstname, lastname, dateofbirth, studentprogram) VALUES (:email, :password, :firstname, :lastname, :dateofbirth, (SELECT studentprograms.uid FROM studentprograms WHERE studentprograms.name = :studentprogram));";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':email' => $email, ':password' => $password, ':firstname' => $firstname, ':lastname' => $lastname, ':dateofbirth' => $dateofbirth, ':studentprogram' => $studentprogram));
    }

    public function getStudentPrograms() {
        global $connection;
        $sql = "SELECT name FROM studentprograms ORDER BY name ASC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute();
        return $statement;
    }

    public function isEqualPassword($uid, $password) {
        global $connection;
        $sql = "SELECT password FROM users WHERE uid = :uid AND password = :password;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':uid' => $uid, ':password' => $password));
        return $statement->rowCount() == 1;
    }

    public function setPassword($uid, $password) {
        global $connection;
        $sql = "UPDATE users SET password = :password WHERE uid = :uid;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        return $statement->execute(array(':uid' => $uid, ':password' => $password));
    }

    public function removeUser($uid) {
        global $connection;
        $sql = "DELETE FROM communitymembers WHERE user = :uid;
                DELETE FROM news WHERE author = :uid;
                DELETE FROM users WHERE uid = :uid;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        return $statement->execute(array(':uid' => $uid));
    }

    public function getCommunities($uid) {
        global $connection;
        $sql = "SELECT studentcommunity.uid, studentcommunity.name FROM studentcommunity INNER JOIN communitymembers ON studentcommunity.uid = communitymembers.studentcommunity WHERE communitymembers.user = :uid ORDER BY studentcommunity.name ASC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':uid' => $uid));
        return $statement;
    }

    public function isAdmin($uid) {
        global $connection;
        $sql = "SELECT access_level FROM users WHERE uid = :uid;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':uid' => $uid));
        return $statement;
    }

    public function isCommunityLeader($uid) {
        global $connection;
        $sql = "SELECT leader FROM communitymembers WHERE user = :uid LIMIT 1";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':uid' => $uid));
        return $statement;
    }

    public function getUsers() {
        global $connection;
        $sql = "SELECT email FROM users ORDER BY email ASC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute();
        return $statement;
    }

    public function getStudentCommunities() {
        global $connection;
        $sql = "SELECT name FROM studentcommunity ORDER BY name ASC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute();
        return $statement;
    }

    public function setCommunityAccess($email, $studentcommunity, $access_level) {
        global $connection;
        $sql = "SELECT user FROM communitymembers WHERE studentcommunity IN (SELECT uid FROM studentcommunity WHERE name = :studentcommunity) AND user IN (SELECT uid FROM users WHERE email = :email);";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute(array(':studentcommunity' => $studentcommunity, ':email' => $email));
        if ($statement->rowCount() == 1) {
            $sql = "UPDATE communitymembers SET leader = :access_level WHERE user IN (SELECT uid FROM users WHERE email = :email) AND studentcommunity IN (SELECT uid FROM studentcommunity WHERE name = :studentcommunity);";
            $statement2 = $connection->prepare($sql);
            $statement2->setFetchMode(PDO::FETCH_OBJ);
            $statement2->execute(array(':access_level' => $access_level, ':email' => $email, ':studentcommunity' => $studentcommunity));
        } else {
            $sql = "INSERT INTO communitymembers (studentcommunity, user, leader) VALUES ((SELECT uid FROM studentcommunity WHERE name = :studentcommunity), (SELECT uid FROM users WHERE email = :email), :access_level);";
            $statement = $connection->prepare($sql);
            $statement->setFetchMode(Pdo::FETCH_OBJ);
            $statement->execute(array(':studentcommunity' => $studentcommunity, ':email' => $email, ':access_level' => $access_level));
        }
        die(header('Location: ' . ROOT_HOST . 'admin'));
    }

    private function startSession($uid, $email, $firstname, $lastname) {
        $_SESSION['uid'] = $uid;
        $_SESSION['email'] = $email;
        $_SESSION['firstname'] = $firstname;
        $_SESSION['lastname'] = $lastname;
    }

    public function destroySession() {
        if (isset($_SESSION['uid'])) {
            session_regenerate_id();
            session_destroy();
        }
    }
}
?>