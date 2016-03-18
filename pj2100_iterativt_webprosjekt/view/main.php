<?php
$activities = array();
$buttons = $info = '';

global $connection;

$s = $connection->prepare("SELECT title, location, date, host FROM activities");
$s->setFetchMode(PDO::FETCH_OBJ);
$s->execute();

while ($a = $s->fetch())
{
    if (!isset($activities[$a->host]))
        $activities[$a->host] = array();

    $activities[$a->host][] = array($a->title, $a->location, new DateTime($a->date));
}

$s = $connection->prepare("SELECT uid, title, description, image_btn, image_banner FROM studentcommunity ORDER BY uid DESC");
$s->setFetchMode(PDO::FETCH_OBJ);
$s->execute();

while ($host = $s->fetch())
{
    $buttons .= '<a href="#h_'.$host->uid.'" class="small_links btn-custombox"><img src="view/images/studentcommunity/'.$host->image_btn.'.jpg" alt="" /><span>'.$host->title.'</span></a>';

    $info .= '
<div id="h_'.$host->uid.'" class="custombox">
    <img src="view/images/banner/'.$host->image_banner.'.jpg" alt="" class="custombox-img" />
    <div class="custombox-text">
        <h2>'.$host->title.'</h2>
        '.$host->description.'
        <p><a href="'.ROOT_HOST.'hostSignup?id='.$host->uid.'">Meld deg på utvalg.</a></p>
    </div>';

    if (isset($activities[$host->uid]))
    {
        $info .= '
    <div class="calendar-small-wrapper">
        <h2>Aktiviteter</h2>';

        foreach ($activities[$host->uid] as $a)
        {
            $info .= '
        <a href="#">
            <div class="calendar-small-event">
                <div class="calendar-small-img"><img src="" alt="" /></div>
                <div class="calendar-small-content">
                    <span class="calendar-small-title">'.$a[0].'</span>
                    <span class="calendar-small-place-date">'.$a[1].', '.$a[2]->format('d.m.Y h:i').'</span>
                </div>
            </div>
        </a>';
        }

        $info .= '
    </div>';
    }

    $info .= '
</div>';
}
?>
<div id="utvalg_wrapper"><?php echo $buttons;?></div>
<?php echo $info;?>
