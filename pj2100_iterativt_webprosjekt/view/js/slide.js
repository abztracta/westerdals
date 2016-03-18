function show() {
        document.getElementById('main').src="../view/images/slide/001.jpg";
        setTimeout("show1()",3000);
}

function show1() {
        document.getElementById('main').src="../view/images/slide/002.jpg";
        setTimeout("show2()",3000);
}

function show2() {
        document.getElementById('main').src="../view/images/slide/003.jpg";
        setTimeout("show3()",3000);
}

function show3() {
        document.getElementById('main').src="../view/images/slide/004.jpg";
        setTimeout("show()",3000);
}