<div id="calendar-wrapper">
	<h1><img src="view/images/h1.png" alt="Overskrift"> Kalender</h1>

    <?php 
        if (isset($_SESSION['uid'])) {
            include_once 'controller/acl.php';
            $controller = new AccessController();
            if ($controller->getCommunityLevel() == 1 || $controller->getAccessLevel() == 1) {
    ?>
             <p><a href="<?php echo ROOT_HOST . 'ny-aktivitet'?>">Opprett ny aktivitet</a></p>
            
    <?php
            }
        }
    ?>

    <?php
        $popups = '';

        include 'controller/activity.php';
        $controller = new ActivityController();
        $statement = $controller->getActivities();
        while ($data = $statement->fetch()) {
            $date = new DateTime($data->date);
            echo "<a href=\"#activity_{$data->uid}\" class=\"btn-custombox\">";
            echo "<div class=\"calendar-event\">";
            echo "<div class=\"calendar-img\">";
			echo "<img src=\"{$data->image}\" alt=\"\" />";
            echo "<div class=\"calendar-date\">";
			echo "<span style=\"background-color: #" . $data->color . "\">" . $date->format('M') . "</span>" . $date->format('d');
		    echo "</div>";
			echo "</div>";
            echo "<div class=\"calendar-content\">";
		    echo "<span class=\"calendar-utvalg\" style=\"color: #" . $data->color . "\">" . $data->name . "</span><br/>";
			echo "<span class=\"calendar-title\">" . $data->title . "</span>";
			echo "<span class=\"calendar-place-date\">";
            echo $data->location . ", " . $date->format('d.m.Y h:i');
            echo "</span></div></div></a>";

            $popups .= '
<div id="activity_'.$data->uid.'" class="custombox">
    <div class="custombox-text custombox-activity">
        <p class="activity-host" style="color: #'.$data->color.';">'.$data->name.'</p>
        <h2>'.$data->title.'</h2>
        <div class="activity-description">'.$data->description.'</div>
        <p class="activity-place-date">'.$data->location.', '.$date->format('d.m.Y h:i').'</p>
    </div>
</div>';

        }

        echo $popups;
    ?>
</div>