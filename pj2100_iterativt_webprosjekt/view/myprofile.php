<h1><img src="view/images/h1.png" alt="Overskrift"> <?php echo $_SESSION['firstname'] . " " . $_SESSION['lastname'];?></h1>

<form action="endrePassordEvent" method="post">
    
    <label for="oldpassword">Skriv ditt nåværende passord:</label>
    <input type="password" name="oldpassword" />
    <?php
        if (isset($canChangePassword)) {
            if (isset($errormsg)) {
                echo $errormsg;
            } else {
                echo $successmsg;
            }
        }
    ?><br/>
    <label for="newpassword">Skriv nytt nye passord:</label>
    <input type="password" name="newpassword" /><br />
    <label for="retypename">Gjenta ditt nye passord:</label>
    <input type="password" name="retypenew" /><br/>
    <input type="submit" value="Endre passord" /><br /><br />
</form>

<form action="slettBrukerEvent" method="post">
    <input type="submit" name="delete" value="Slett konto" />
    <?php
    if (isset($deleteError)) {
        echo $deleteError;
    }
    ?>
    
    <p>Din konto vil bli slettet og ditt medlemskap i alle utvalg vil opphøre. Du kan ikke angre dette valget.</p>
</form>
