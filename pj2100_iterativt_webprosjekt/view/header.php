<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="<?php echo ROOT_HOST . 'view/css/style.css';?>"/>
        <link rel="stylesheet" href="<?php echo ROOT_HOST . 'view/css/custombox.css';?>"/>
        <meta charset="utf-8" />
        <title>
            Westerdals Student
        </title>
        <script>
                function show() {
                    document.getElementById('main').src="view/images/slide/001.jpg";
                    setTimeout("show1()",3000);
                }

                function show1() {
                    document.getElementById('main').src="view/images/slide/002.jpg";
                    setTimeout("show2()",3000);
                }

                function show2() {
                    document.getElementById('main').src="view/images/slide/003.jpg";
                    setTimeout("show3()",3000);
                }

                function show3() {
                    document.getElementById('main').src="view/images/slide/004.jpg";
                    setTimeout("show()",3000);
                }
        </script>
    </head>

    <body onload="show()">

        <div id="wrapper">
           <header>
                <div id="header_left">
                    <?php
                        if (isset($_SESSION['uid'])) {
                            echo 'Innlogget som: ' . $_SESSION['firstname'] . " " . $_SESSION['lastname'];
                        }
                    ?>
                </div>
                <div id="header_right">
                    <?php
                        if (isset($_SESSION['uid'])) {
                            include 'controller/acl.php';
                            $acl = new AccessController();
                            if ($acl->getAccessLevel() == 1) {
                                echo "<a href=\"" . ROOT_HOST . "admin\">Admin</a>&nbsp;|&nbsp;";
                            }
                            if ($acl->getCommunityLevel() == 1) {
                                echo "<a href=\"" . ROOT_HOST . "mineutvalg\">Mine Utvalg</a>&nbsp;|&nbsp;";
                            }
                    ?>
                    <a href="<?php echo ROOT_HOST . 'minprofil'?>">Min profil</a>
                    &nbsp;|&nbsp;
                    <a href="<?php echo ROOT_HOST . 'logoutEvent';?>">Logg ut </a>

                    <?php
                        } else {
                    ?>

                    <a href="<?php echo ROOT_HOST . 'login';?>">Logg inn</a>
                    &nbsp;|&nbsp;
                    <a href="<?php echo ROOT_HOST . 'registrering';?>">Registrer</a>
                    
                    <?php
                        }
                    ?>
                </div>

                <nav>
                    <ul>
                        <li><a href="<?php echo ROOT_HOST;?>">Forside</a></li>
                        <li><a href="<?php echo ROOT_HOST . 'nyheter';?>">Nyheter</a></li>
                        <li><a href="<?php echo ROOT_HOST . 'kalender';?>">Kalender</a></li>
                        <li><a href="<?php echo ROOT_HOST . 'studentliv';?>">Studentliv</a></li>
                    </ul>
                </nav>
            </header>

   
            <div id="content">
                <a href="<?php echo ROOT_HOST;?>">
                    <div id="logo">
                    </div>
                </a>