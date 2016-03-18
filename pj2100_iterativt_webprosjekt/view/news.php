<h1><img src="view/images/h1.png" alt="Overskrift"> Nyheter</h1>
    <?php
        include 'controller/news.php';
        $controller = new NewsController();
        $statement = $controller->getNewsItems();
        while ($data = $statement->fetch()) {
            echo '<h2>' . $data->title . '</h2>';
            echo '<p>' . $data->post . '</p>';
            $date = new DateTime($data->posted);
            echo '<footer>Postet av <b>' . $data->firstname . ' ' . $data->lastname . '</b> @ ' . $date->format('d/m/y') .  '</footer>';
        }
    ?>