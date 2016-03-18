<div id="login">

    <h1><img src="view/images/h1.png" alt="Overskrift" /> Logg inn</h1>
    <?php
        if (isset($loginFailed)) {
    ?>
    <p id="login_error">
        <?php echo $loginFailed;?>
    </p>
    <?php
        }
    ?>


    <form action="<?php echo ROOT_HOST . 'loginEvent';?>" method="post">
        <input type="text" name="email" placeholder="Epost" /><br />
        <input type="password" name="password" placeholder="Passord" /><br />
        <input type="submit" value="Login"/>
    </form>


</div>