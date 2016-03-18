<?php
include_once 'controller/admin.php';
$control = new AdminController();
$statement = $control->getUsers();
$statement2 = $control->getStudentCommunities();
?>
<div>
    <h1><img src="view/images/h1.png" alt="Overskrift"> Administrator</h1>
    <h2>Gi bruker rettigheter i utvalg</h2>

    <u>Gjør bruker til utvalgsleder</u>
    <form action="<?php echo ROOT_HOST . 'endreUtvalgsLeder';?>" method="post">
        <label for="studentcommunity">Studentutvalg</label>
        <select name="studentcommunity">
            <?php
                while ($data2 = $statement2->fetch()) {
                    echo '<option>' . $data2->name . '</option>';
                }
            ?>
        </select><br/>
        <label for="user">Bruker</label>
        <select name="user">
            <?php
                while ($data = $statement->fetch()) {
                    echo '<option>' . $data->email . '</option>';
                }
            ?>
        </select><br/>
        <label for="accessright">Aksessrettighet</label>
        <input type="text" name="accessright" placeholder="Aksessrettighet (0-1)" /><br/>
        <input type="submit" value="Endre" /><br/><br/>
    </form>

    <u>Legg til / Fjern administrator</u>
    <form action="<?php echo ROOT_HOST . 'endreAdministrator';?>" method="post">
        <label for="user">Bruker</label>
        <select name="user">
            <?php
            $statement = $control->getUsers();
            while ($data = $statement->fetch()) {
                echo '<option>' . $data->email . '</option>';
            }
            ?>
        </select><br/>
        <label for="accessright">Aksessrettighet</label>
        <input type="text" name="accessright" placeholder="Aksessrettighet (0-1)" /><br/>
        <input type="submit" value="Endre" /><br/><br/>
    </form>
</div>