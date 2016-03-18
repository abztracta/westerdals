<?php
class NewsController {

    public function getNewsItems() {
        include 'model/newsitem.php';
        $news = new NewsItem();
        return $news->getNewsItem();
    }
}
?>