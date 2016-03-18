<h1><img src="view/images/h1.png" alt="Overskrift"> Opprett aktivitet</h1>
<?php if (!isset($success_msg)) {?>
<form action="<?php echo ROOT_HOST . 'newActivityEvent';?>" method="post">
	<table>
		<tr>
			<td style="width: 200px;">
				<label for="title">Tittel:</label></td>
			<td>
				<input type="text" name="title" value="<?php if (isset($_POST['title'])) echo htmlspecialchars($_POST['title']);?>" />
				<?php if (isset($errors['title'])) echo $errors['title'];?>
			</td>
		</tr>

		<tr>
			<td>
				<label for="host">Utvalg:</label></td>
			<td>
				<select name="host"><?php

				global $connection;
				$statement = $connection->prepare("SELECT uid, title FROM studentcommunity ORDER BY uid DESC");
				$statement->setFetchMode(PDO::FETCH_OBJ);
				$statement->execute();

				while ($host = $statement->fetch())
				{
					echo '
					<option value="'.$host->uid.'"'.(@$_POST['host'] == $host->uid ? ' selected="selected"' : '').'>'.$host->title.'</option>';
				}

				?></select>
				<?php if (isset($errors['host'])) echo $errors['host'];?>
			</td>
		</tr>

		<tr>
			<td>
				<label for="date">Dato:</label></td>
			<td>
				<input type="datetime-local" name="date" value="<?php if (isset($_POST['date'])) echo htmlspecialchars($_POST['date']);?>" />
				<?php if (isset($errors['date'])) echo $errors['date'];?>
			</td>
		</tr>

		<tr>
			<td>
				<label for="place">Sted:</label></td>
			<td>
				<input type="text" name="place" value="<?php if (isset($_POST['place'])) echo htmlspecialchars($_POST['place']);?>" />
				<?php if (isset($errors['place'])) echo $errors['place'];?>
			</td>
		</tr>

		<tr>
			<td>
				<label for="information">Informasjon:</label></td>
			<td>
				<textarea name="information"><?php if (isset($_POST['information'])) echo htmlspecialchars($_POST['information']);?></textarea>
				<?php if (isset($errors['information'])) echo $errors['information'];?>
			</td>
		</tr>

		<tr>
			<td>
				<label for="place">Bilde:</label></td>
			<td>
				<input type="text" name="image" value="<?php if (isset($_POST['image'])) echo htmlspecialchars($_POST['image']);?>" />
				<?php if (isset($errors['image'])) echo $errors['image'];?>
			</td>
		</tr>

		<tr>
			<td colspan="2"><input type="submit" value="Opprett aktivitet" /></td>
		</tr>

	</table>
</form>
<?php
}
else
{
	echo '
<h1>'.$success_msg.'</strong></h1>
<script type="text/javascript">
// <!--
window.setTimeout(function()
{
	window.location.href = \''.ROOT_HOST.'\';
}, 2500);
// -->
</script>';
}
