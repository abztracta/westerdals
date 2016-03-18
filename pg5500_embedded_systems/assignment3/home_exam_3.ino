#include <Arduino.h>
#include <SD.h>
#include <SPI.h>
#include <TFT.h>

#define SD_CS 4
#define LCD_CS 10
#define DC 9
#define RST 8

#define IR_IN A0

#define MAX_COUNTRY_COUNT 197
#define COUNTRYNAME_LENGTH 13
#define ALTERNATIVES_LENGTH 4

TFT screen = TFT(LCD_CS, DC, RST);
PImage currentFlag;
String correctAlternative;
String alternatives[ALTERNATIVES_LENGTH];
int points;
int isRunning;

// variables for remote control
int Pulse_Width = 0;
int ir_code = 0x00;
char adrL_code = 0x00;
char adrH_code = 0x00;
char lastCode;

void timer1_init() {
    TCCR1A = 0X00;
    TCCR1B = 0X05;
    TCCR1C = 0X00;
    TCNT1 = 0X00;
    TIMSK1 = 0X00;
}

char logic_value() {
    TCNT1 = 0x00;
    while (!(digitalRead(IR_IN)));
    Pulse_Width = TCNT1;
    TCNT1 = 0;
    if (Pulse_Width >= 7 && Pulse_Width <= 10) {
        while (digitalRead(IR_IN));
        Pulse_Width = TCNT1;
        TCNT1 = 0;
        if (Pulse_Width >= 7 && Pulse_Width <= 10) //560us
            return 0;
        else if (Pulse_Width >= 25 && Pulse_Width <= 27) //1.7ms
            return 1;
    }
    return -1;
}

void pulse_deal() {
    int i;
    int j;
    ir_code = 0x00;
    adrL_code = 0x00;
    adrH_code = 0x00;

    for (i = 0; i < 16; i++) {
        if(logic_value() == 1)
            ir_code |= (1 << i);
    }
    for (i = 0; i < 8; i++) {
        if(logic_value() == 1)
            adrL_code |= (1 << i);
    }
    for (j = 0; j < 8; j++) {
        if(logic_value() == 1)
            adrH_code |= (1 << j);
    }
}

void remote_decode() {
    TCNT1 = 0x00;
    while (digitalRead(IR_IN))
    {
        if (TCNT1 >= 1563)
        {
            ir_code = 0x00ff;
            adrL_code = 0x00;
            adrH_code = 0x00;
            return;
        }
    }
    TCNT1 = 0x00;
    while (!(digitalRead(IR_IN)));
    Pulse_Width = TCNT1;
    TCNT1 = 0;
    if (Pulse_Width >= 140 && Pulse_Width <= 141) { //9ms
        while (digitalRead(IR_IN));
        Pulse_Width = TCNT1;
        TCNT1 = 0;
        if (Pulse_Width >= 68 && Pulse_Width <= 72) { //4.5ms
            pulse_deal();
            return;
        }
        else if (Pulse_Width >= 34 && Pulse_Width <= 36) { //2.25ms
            while (!(digitalRead(IR_IN)));
            Pulse_Width = TCNT1;
            TCNT1 = 0;
            if (Pulse_Width >= 7 && Pulse_Width <= 10) { //560us
                return;
            }
        }
    }
}

void initRound() {
    isRunning = 1;
    randomSeed(millis());
    int current = random(MAX_COUNTRY_COUNT);
    char filename[15];
    sprintf(filename, "images/%d.bmp", current);
    currentFlag = screen.loadImage(filename);


    sprintf(filename, "cnames/%d.txt", current);
    String name = readNameFromFile(filename);
    correctAlternative = name;
    alternatives[0] = name;

    Serial.println("Alternatives: ");
    Serial.println(alternatives[0]);
    for (int i = 1; i < ALTERNATIVES_LENGTH; i++) {
        randomSeed(millis());
        sprintf(filename, "cnames/%d.txt", random(MAX_COUNTRY_COUNT));
        alternatives[i] = readNameFromFile(filename);
        Serial.println(alternatives[i]);
    }
    Serial.println("----");
    randomizeAlternatives();
    drawGameScreen();
}

void drawGameScreen() {
    screen.background(255, 255, 255);
    screen.stroke(0, 0, 0);
    screen.rect(51, 1, 52, 42);
    screen.image(currentFlag, 52, 2);

    screen.text("Points:", 106, 0);

    char pts[6];
    String(points).toCharArray(pts, 6);
    screen.text(pts, 106, 16);

    screen.fill(0, 255, 0);

    screen.rect(2, 45, 78, 40);
    screen.rect(81, 45, 78, 40);
    screen.rect(2, 86, 78, 40);
    screen.rect(81, 86, 78, 40);

    screen.text("1:", 6, 50);
    screen.text("2:", 84, 50);
    screen.text("3:", 6, 91);
    screen.text("4:", 84, 91);

    char buffer[COUNTRYNAME_LENGTH];
    alternatives[0].toCharArray(buffer, COUNTRYNAME_LENGTH);
    screen.text(buffer, 4, 65);
    alternatives[1].toCharArray(buffer, COUNTRYNAME_LENGTH);
    screen.text(buffer, 83, 65);
    alternatives[2].toCharArray(buffer, COUNTRYNAME_LENGTH);
    screen.text(buffer, 4, 106);
    alternatives[3].toCharArray(buffer, COUNTRYNAME_LENGTH);
    screen.text(buffer, 83, 106);
}

void randomizeAlternatives() {
    for (int i = 0; i < ALTERNATIVES_LENGTH; i++) {
        int rand = random(ALTERNATIVES_LENGTH);
        String tmp = alternatives[i];
        alternatives[i] = alternatives[rand];
        alternatives[rand] = tmp;
    }
}

String readNameFromFile(char filename[]) {
    String cname = "";
    File file = SD.open(filename);
    int i = 0;
    while (file.available()) {
        char c = file.read();
        cname += c;
    }
    file.close();
    return cname;
}

void drawGameOverScreen() {
    screen.background(0, 0, 0);
    screen.stroke(0, 0, 255);
    screen.setTextSize(2);
    screen.text("Game Over", 30, 44);
    screen.setTextSize(1);
    screen.stroke(255, 255, 255);
    char ptsBuffer[20];
    sprintf(ptsBuffer, "Points: %d!", points);
    screen.text(ptsBuffer, 55, 60);
    char buffer[40];
    sprintf(buffer, "Press \"ok\" to play again!");
    screen.text(buffer, 7, 80);
}

void endRound() {
    currentFlag.close();
}

void setup() {
    isRunning = 1;
    pinMode(IR_IN, INPUT);
    timer1_init();

    screen.begin();
    screen.background(255, 255, 255);

    screen.stroke(0, 0, 255);
    screen.println();
    screen.println(F("Arduino TFT Bitmap Example"));
    screen.stroke(0, 0, 0);
    screen.println(F("Open serial monitor"));
    screen.println(F("to run the sketch"));

    Serial.begin(9600);
    while (!Serial) ;

    screen.background(255, 255, 255);

    Serial.print(F("Initializing SD card..."));
    if (!SD.begin(SD_CS)) {
        Serial.println(F("failed!"));
        return;
    }
    Serial.println(F("OK!"));

    screen.begin();
    screen.background(255, 255, 255);

    initRound();
}

int codeToAlternative(int code) {
    switch (code) {
        case 22:
            return 0;
        case 25:
            return 1;
        case 13:
            return 2;
        case 12:
            return 3;
        default:
            return -1;
    }
}

void evaluateAnswer(int answer) {
    int alternative = codeToAlternative(answer);
    Serial.print("Correct answer is ");
    Serial.print(correctAlternative);
    Serial.print(" and user picked ");
    Serial.println(alternatives[alternative]);
    if (alternatives[alternative].equals(correctAlternative)) {
        points++;
        endRound();
        // TODO: Play success sound
        initRound();
    } else {
        isRunning = 0;
        endRound();
        // TODO: Play error sound
        drawGameOverScreen();
        points = 0;
    }
}

void loop() {
    remote_decode();
    if (adrL_code != lastCode && adrL_code != 0) {
        lastCode = adrL_code;
        int code = (int) lastCode;
        if ((code == 22 || code == 25 || code == 13 || code == 12) && isRunning) {
            evaluateAnswer(code);
        } else if (code == 64 && !isRunning) {
            initRound();
        }
        lastCode = 0;
    }
}