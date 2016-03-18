<div id="register">

<?php if (!isset($success)) { ?>
    <form action="<?php echo ROOT_HOST . 'registrationEvent';?>" method="post">
        <table>
            <tr>
                <td>
                    <label for="email">Epost</label></td>
                <td>
                    <input type="email" name="email" value="<?php if (!isset($incorrectEmail) && isset($_POST['email'])) {echo $_POST['email'];}?>" /><?php if (isset($incorrectEmail)) {echo $incorrectEmail;}?>
                </td>
            </tr>
            <tr>
                <td> 
                    <label for="password">Passord</label>
                </td>
                <td>
                    <input type="password" name="password" value="" /><?php if (isset($incorrectPassword)) {echo $incorrectPassword;} ?>
                </td>
            </tr>
            <tr>
                <td> 
                    <label for="retype">Gjenta passord</label>
                </td>
                <td>
                    <input type="password" name="retype" value="" />
                </td>
            <tr>
                <td> 
                    <label for="firstname">Fornavn</label>
                </td>
                <td>
                    <input type="text" name="firstname" value="<?php if (isset($_POST['firstname'])) {echo $_POST['firstname'];}?>" /><?php if (isset($incorrectFirstname)) {echo $incorrectFirstname;}?>
                </td>
            </tr>
            <tr>
                <td> 
                    <label for="lastname">Etternavn</label>
                </td>
                <td>
                    <input type="text" name="lastname" value="<?php if (isset($_POST['lastname'])) {echo $_POST['lastname'];}?>" /><?php if (isset($incorrectLastname)) {echo $incorrectLastname;}?>
                </td>
            </tr>
            <tr>
                <td> 
                    <label for="dateofbirth">Fødselsdato</label>
                </td>
                <td>
                    <input type="date" name="dateofbirth" />
                </td>
            <tr>
                <td> 
                    <label for="studentprogram">Studieretning</label>
                </td>
                <td>
                    <select name="studentprogram">
                        <?php
                            global $filename;
                            if (strcmp($filename, 'registrationEvent') != 0) {
                            include 'controller/registration.php';
                            }
                            $controller = new RegistrationController();
                            $statement = $controller->getStudentPrograms();
                            while ($data = $statement->fetch()) {
                        ?>
                    
                        <option <?php if (isset($_POST['studentprogram']) && strcmp($data->name, $_POST['studentprogram']) == 0) {echo 'selected';}?>>
                            <?php
                                    echo $data->name;
                            ?>
                        </option>
            
                        <?php
                            }
                        ?>
                    </select>
                </td>
            </tr>
        
            <tr>
                <td>
                    <input type="submit" value="Registrer" />
                </td>
            </tr>
        </table>
    </form>

    <?php
    } else {
    ?>
    <h1><?php echo $success;?></h1>
        <script>
            window.setTimeout(function() {
                window.location.href = '<?php echo ROOT_HOST;?>';
            }, 2500);
        </script>
    <?php
    }
    ?>
</div>