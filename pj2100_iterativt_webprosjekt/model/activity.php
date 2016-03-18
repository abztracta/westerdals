<?php
class Activity {

    public function getActivities() {
        global $connection;
        $sql = "SELECT activities.uid, activities.title, activities.location, activities.date, studentcommunity.name, activities.description, activities.image, studentcommunity.color FROM activities INNER JOIN studentcommunity ON activities.host = studentcommunity.uid ORDER BY date ASC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute();
        return $statement;
    }
}
?>