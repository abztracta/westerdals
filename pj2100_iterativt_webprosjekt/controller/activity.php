<?php
include 'model/activity.php';

class ActivityController {

    public function getActivities() {
        $activity = new Activity();
        return $activity->getActivities();
    }

    public function createNewActivity()
    {
    	if (!isset($_POST['title'], $_POST['host'], $_POST['date'], $_POST['place'], $_POST['information'], $_POST['image']))
    		die(header('Location: ' . ROOT_HOST));

    	$errors = array();

    	if (strlen(trim($_POST['title'])) < 1)
    		$errors['title'] = 'Invalid title. vennsligst bruk svensk språk';

    	if (!$this->isValidHost($_POST['host']))
    		$errors['host'] = 'Invalid host gtfo.';

    	if (strtotime($_POST['date']) < time())
    		$errors['date'] = 'Invalid date.';

    	if (strlen(trim($_POST['place'])) < 1)
    		$errors['place'] = 'Invalid place';

    	if (strlen(trim($_POST['information'])) < 1)
    		$errors['information'] = 'Invalid information';

    	if (!filter_var($_POST['image'], FILTER_VALIDATE_URL) && $_POST['image'] != '')
    		$errors['image'] = 'Invalid image';

    	if (!$errors)
    	{
			global $connection;
			$s = $connection->prepare("INSERT INTO activities SET title = :title, location = :place, date = :date, host = :host, description = :information, image = :image");
			$s->setFetchMode(PDO::FETCH_OBJ);
			$s->execute(array
			(
				':title' => $_POST['title'],
				':place' => $_POST['place'],
				':date' => $_POST['date'],
				':host' => intval($_POST['host']),
				':information' => $_POST['information'],
				':image' => $_POST['image']
			));

			$success_msg = 'Aktiviteten ble opprettet.';
    	}

    	include 'view/newactivity.php';
    }

    private function isValidHost($uid)
    {
		global $connection;
		$s = $connection->prepare("SELECT uid FROM studentcommunity WHERE uid = :uid;");
		$s->setFetchMode(PDO::FETCH_OBJ);
		$s->execute(array(':uid' => intval($uid)));
		return $s->rowCount() == 1;
    }
}
?>