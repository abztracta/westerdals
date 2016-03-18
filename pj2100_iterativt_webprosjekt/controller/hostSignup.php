<?php

class hostSignupController
{
	public function performSignup()
	{
		if (!isset($_SESSION['uid']))
			$error_msg = 'Du må være innlogget for å melde deg opp.';

		if (!isset($_GET['id']))
			$error_msg = 'Mangler id.';

		elseif (!$this->hostIDExists($_GET['id']))
			$error_msg = 'Ugyldig host ID.';

		if (!isset($error_msg))
		{
			if ($this->isSignedUp($_SESSION['uid'], $_GET['id']))
				$error_msg = 'Du er allerede oppmeldt.';
		}

		if (!isset($error_msg))
		{
			global $connection;
			$s = $connection->prepare("INSERT INTO signups SET uid = :uid, host_id = :host");
			$s->setFetchMode(PDO::FETCH_OBJ);
			$s->execute(array
			(
				':uid' => $_SESSION['uid'],
				':host' => intval($_GET['id'])
			));

			$success_msg = 'Du ble oppmeldt.';
		}

		include 'view/hostSignup.php';
	}

	private function hostIDExists($id)
	{
		global $connection;
		$s = $connection->prepare("SELECT uid FROM studentcommunity WHERE uid = :uid;");
		$s->setFetchMode(PDO::FETCH_OBJ);
		$s->execute(array(':uid' => intval($id)));
		return $s->rowCount() == 1;
	}

	private function isSignedUp($uid, $host)
	{
		global $connection;
		$s = $connection->prepare("SELECT uid FROM signups WHERE uid = :uid AND host_id = :host");
		$s->setFetchMode(PDO::FETCH_OBJ);
		$s->execute(array(':uid' => $uid, ':host' => intval($host)));
		return $s->rowCount() != 0;
	}
}
