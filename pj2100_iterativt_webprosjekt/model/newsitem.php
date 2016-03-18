<?php
class NewsItem {

    public function getNewsItem() {
        global $connection;
        $sql = "SELECT news.uid, news.title, users.uid, users.firstname, users.lastname, news.posted, news.post FROM news LEFT JOIN users ON news.author = users.uid ORDER BY news.posted DESC;";
        $statement = $connection->prepare($sql);
        $statement->setFetchMode(PDO::FETCH_OBJ);
        $statement->execute();
        return $statement;
    }
}
?>