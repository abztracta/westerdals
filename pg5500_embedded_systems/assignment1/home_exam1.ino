#include <LedControl.h>

int loadPin = 10;
int clockPin = 11;
int dataPin = 12;

LedControl lc = LedControl(dataPin, clockPin, loadPin, 1);

char message[] = "PG5500 - Embedded Systems";

int messageSize = strlen(message);

#define NOTE_B0  31
#define NOTE_C1  33
#define NOTE_CS1 35
#define NOTE_D1  37
#define NOTE_DS1 39
#define NOTE_E1  41
#define NOTE_F1  44
#define NOTE_FS1 46
#define NOTE_G1  49
#define NOTE_GS1 52
#define NOTE_A1  55
#define NOTE_AS1 58
#define NOTE_B1  62
#define NOTE_C2  65
#define NOTE_CS2 69
#define NOTE_D2  73
#define NOTE_DS2 78
#define NOTE_E2  82
#define NOTE_F2  87
#define NOTE_FS2 93
#define NOTE_G2  98
#define NOTE_GS2 104
#define NOTE_A2  110
#define NOTE_AS2 117
#define NOTE_B2  123
#define NOTE_C3  131
#define NOTE_CS3 139
#define NOTE_D3  147
#define NOTE_DS3 156
#define NOTE_E3  165
#define NOTE_F3  175
#define NOTE_FS3 185
#define NOTE_G3  196
#define NOTE_GS3 208
#define NOTE_A3  220
#define NOTE_AS3 233
#define NOTE_B3  247
#define NOTE_C4  262
#define NOTE_CS4 277
#define NOTE_D4  294
#define NOTE_DS4 311
#define NOTE_E4  330
#define NOTE_F4  349
#define NOTE_FS4 370
#define NOTE_G4  392
#define NOTE_GS4 415
#define NOTE_A4  440
#define NOTE_AS4 466
#define NOTE_B4  494
#define NOTE_C5  523
#define NOTE_CS5 554
#define NOTE_D5  587
#define NOTE_DS5 622
#define NOTE_E5  659
#define NOTE_F5  698
#define NOTE_FS5 740
#define NOTE_G5  784
#define NOTE_GS5 831
#define NOTE_A5  880
#define NOTE_AS5 932
#define NOTE_B5  988
#define NOTE_C6  1047
#define NOTE_CS6 1109
#define NOTE_D6  1175
#define NOTE_DS6 1245
#define NOTE_E6  1319
#define NOTE_F6  1397
#define NOTE_FS6 1480
#define NOTE_G6  1568
#define NOTE_GS6 1661
#define NOTE_A6  1760
#define NOTE_AS6 1865
#define NOTE_B6  1976
#define NOTE_C7  2093
#define NOTE_CS7 2217
#define NOTE_D7  2349
#define NOTE_DS7 2489
#define NOTE_E7  2637
#define NOTE_F7  2794
#define NOTE_FS7 2960
#define NOTE_G7  3136
#define NOTE_GS7 3322
#define NOTE_A7  3520
#define NOTE_AS7 3729
#define NOTE_B7  3951
#define NOTE_C8  4186
#define NOTE_CS8 4435
#define NOTE_D8  4699
#define NOTE_DS8 4978

byte alphabeth[104*8] = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x02, 0x02, 0x02, 0x02, 0x02, 0x00, 0x02, 0x00,
        0x05, 0x05, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x0A, 0x0A, 0x1F, 0x0A, 0x1F, 0x0A, 0x0A, 0x00,
        0x0E, 0x15, 0x05, 0x0E, 0x14, 0x15, 0x0E, 0x00,
        0x13, 0x13, 0x08, 0x04, 0x02, 0x19, 0x19, 0x00,
        0x06, 0x09, 0x05, 0x02, 0x15, 0x09, 0x16, 0x00,
        0x02, 0x02, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x04, 0x02, 0x01, 0x01, 0x01, 0x02, 0x04, 0x00,
        0x01, 0x02, 0x04, 0x04, 0x04, 0x02, 0x01, 0x00,
        0x00, 0x0A, 0x15, 0x0E, 0x15, 0x0A, 0x00, 0x00,
        0x00, 0x04, 0x04, 0x1F, 0x04, 0x04, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x02, 0x01,
        0x00, 0x00, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00,
        0x10, 0x10, 0x08, 0x04, 0x02, 0x01, 0x01, 0x00,
        0x0E, 0x11, 0x19, 0x15, 0x13, 0x11, 0x0E, 0x00,
        0x04, 0x06, 0x04, 0x04, 0x04, 0x04, 0x0E, 0x00,
        0x0E, 0x11, 0x10, 0x0C, 0x02, 0x01, 0x1F, 0x00,
        0x0E, 0x11, 0x10, 0x0C, 0x10, 0x11, 0x0E, 0x00,
        0x08, 0x0C, 0x0A, 0x09, 0x1F, 0x08, 0x08, 0x00,
        0x1F, 0x01, 0x01, 0x0E, 0x10, 0x11, 0x0E, 0x00,
        0x0C, 0x02, 0x01, 0x0F, 0x11, 0x11, 0x0E, 0x00,
        0x1F, 0x10, 0x08, 0x04, 0x02, 0x02, 0x02, 0x00,
        0x0E, 0x11, 0x11, 0x0E, 0x11, 0x11, 0x0E, 0x00,
        0x0E, 0x11, 0x11, 0x1E, 0x10, 0x08, 0x06, 0x00,
        0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x02, 0x00, 0x02, 0x02, 0x01,
        0x08, 0x04, 0x02, 0x01, 0x02, 0x04, 0x08, 0x00,
        0x00, 0x00, 0x0F, 0x00, 0x0F, 0x00, 0x00, 0x00,
        0x01, 0x02, 0x04, 0x08, 0x04, 0x02, 0x01, 0x00,
        0x0E, 0x11, 0x10, 0x08, 0x04, 0x00, 0x04, 0x00,
        0x0E, 0x11, 0x1D, 0x15, 0x0D, 0x01, 0x1E, 0x00,
        0x04, 0x0A, 0x11, 0x11, 0x1F, 0x11, 0x11, 0x00,
        0x0F, 0x11, 0x11, 0x0F, 0x11, 0x11, 0x0F, 0x00,
        0x0E, 0x11, 0x01, 0x01, 0x01, 0x11, 0x0E, 0x00,
        0x07, 0x09, 0x11, 0x11, 0x11, 0x09, 0x07, 0x00,
        0x1F, 0x01, 0x01, 0x0F, 0x01, 0x01, 0x1F, 0x00,
        0x1F, 0x01, 0x01, 0x0F, 0x01, 0x01, 0x01, 0x00,
        0x0E, 0x11, 0x01, 0x0D, 0x11, 0x19, 0x16, 0x00,
        0x11, 0x11, 0x11, 0x1F, 0x11, 0x11, 0x11, 0x00,
        0x07, 0x02, 0x02, 0x02, 0x02, 0x02, 0x07, 0x00,
        0x1C, 0x08, 0x08, 0x08, 0x08, 0x09, 0x06, 0x00,
        0x11, 0x09, 0x05, 0x03, 0x05, 0x09, 0x11, 0x00,
        0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x0F, 0x00,
        0x11, 0x1B, 0x15, 0x15, 0x11, 0x11, 0x11, 0x00,
        0x11, 0x13, 0x13, 0x15, 0x19, 0x19, 0x11, 0x00,
        0x0E, 0x11, 0x11, 0x11, 0x11, 0x11, 0x0E, 0x00,
        0x0F, 0x11, 0x11, 0x0F, 0x01, 0x01, 0x01, 0x00,
        0x0E, 0x11, 0x11, 0x11, 0x15, 0x09, 0x16, 0x00,
        0x0F, 0x11, 0x11, 0x0F, 0x05, 0x09, 0x11, 0x00,
        0x0E, 0x11, 0x01, 0x0E, 0x10, 0x11, 0x0E, 0x00,
        0x1F, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x00,
        0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x0E, 0x00,
        0x11, 0x11, 0x11, 0x11, 0x0A, 0x0A, 0x04, 0x00,
        0x41, 0x41, 0x41, 0x49, 0x2A, 0x2A, 0x14, 0x00,
        0x11, 0x11, 0x0A, 0x04, 0x0A, 0x11, 0x11, 0x00,
        0x11, 0x11, 0x11, 0x0A, 0x04, 0x04, 0x04, 0x00,
        0x1F, 0x10, 0x08, 0x04, 0x02, 0x01, 0x1F, 0x00,
        0x07, 0x01, 0x01, 0x01, 0x01, 0x01, 0x07, 0x00,
        0x01, 0x01, 0x02, 0x04, 0x08, 0x10, 0x10, 0x00,
        0x07, 0x04, 0x04, 0x04, 0x04, 0x04, 0x07, 0x00,
        0x00, 0x04, 0x0A, 0x11, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0x00,
        0x01, 0x01, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x06, 0x08, 0x0E, 0x09, 0x0E, 0x00,
        0x01, 0x01, 0x0D, 0x13, 0x11, 0x13, 0x0D, 0x00,
        0x00, 0x00, 0x06, 0x09, 0x01, 0x09, 0x06, 0x00,
        0x10, 0x10, 0x16, 0x19, 0x11, 0x19, 0x16, 0x00,
        0x00, 0x00, 0x06, 0x09, 0x07, 0x01, 0x0E, 0x00,
        0x04, 0x0A, 0x02, 0x07, 0x02, 0x02, 0x02, 0x00,
        0x00, 0x00, 0x06, 0x09, 0x09, 0x06, 0x08, 0x07,
        0x01, 0x01, 0x0D, 0x13, 0x11, 0x11, 0x11, 0x00,
        0x01, 0x00, 0x01, 0x01, 0x01, 0x01, 0x02, 0x00,
        0x04, 0x00, 0x06, 0x04, 0x04, 0x04, 0x04, 0x03,
        0x01, 0x01, 0x09, 0x05, 0x03, 0x05, 0x09, 0x00,
        0x03, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x00,
        0x00, 0x00, 0x15, 0x2B, 0x29, 0x29, 0x29, 0x00,
        0x00, 0x00, 0x0D, 0x13, 0x11, 0x11, 0x11, 0x00,
        0x00, 0x00, 0x06, 0x09, 0x09, 0x09, 0x06, 0x00,
        0x00, 0x00, 0x0D, 0x13, 0x13, 0x0D, 0x01, 0x01,
        0x00, 0x00, 0x16, 0x19, 0x19, 0x16, 0x10, 0x10,
        0x00, 0x00, 0x0D, 0x13, 0x01, 0x01, 0x01, 0x00,
        0x00, 0x00, 0x0E, 0x01, 0x06, 0x08, 0x07, 0x00,
        0x00, 0x02, 0x07, 0x02, 0x02, 0x02, 0x04, 0x00,
        0x00, 0x00, 0x11, 0x11, 0x11, 0x19, 0x16, 0x00,
        0x00, 0x00, 0x11, 0x11, 0x11, 0x0A, 0x04, 0x00,
        0x00, 0x00, 0x11, 0x11, 0x15, 0x15, 0x0A, 0x00,
        0x00, 0x00, 0x11, 0x0A, 0x04, 0x0A, 0x11, 0x00,
        0x00, 0x00, 0x09, 0x09, 0x09, 0x0E, 0x08, 0x06,
        0x00, 0x00, 0x0F, 0x08, 0x06, 0x01, 0x0F, 0x00,
        0x04, 0x02, 0x02, 0x01, 0x02, 0x02, 0x04, 0x00,
        0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x00,
        0x01, 0x02, 0x02, 0x04, 0x02, 0x02, 0x01, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x1C, 0x2A, 0x49, 0x49, 0x41, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x51, 0x49, 0x41, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x41, 0x79, 0x41, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x41, 0x49, 0x51, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x41, 0x49, 0x49, 0x2A, 0x1C, 0x00,
        0x1C, 0x22, 0x41, 0x49, 0x45, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x41, 0x4F, 0x41, 0x22, 0x1C, 0x00,
        0x1C, 0x22, 0x45, 0x49, 0x41, 0x22, 0x1C, 0x00
};

byte lengthTable[104] =
{
        2, 2, 3, 5, 5, 5, 5, 2,
        3, 3, 5, 5, 2, 5, 1, 5,
        5, 4, 5, 5, 5, 5, 5, 5,
        5, 5, 1, 2, 4, 4, 4, 5,
        5, 5, 5, 5, 5, 5, 5, 5,
        5, 3, 5, 5, 4, 5, 5, 5,
        5, 5, 5, 5, 5, 5, 5, 7,
        5, 5, 5, 3, 5, 3, 5, 5,
        2, 4, 5, 4, 5, 4, 4, 4,
        5, 2, 3, 4, 2, 6, 5, 4,
        5, 5, 5, 4, 3, 5, 5, 5,
        5, 4, 4, 3, 2, 3, 0, 0,
        7, 7, 7, 7, 7, 7, 7, 7
};

int currentCharIndex = 0;
int currentCharBit = 0;
int currentCharIndexSave = 0;
int currentCharBitSave = 0;
int currentCharIndexSave2 = 0;
int currentCharBitSave2 = 0;
char currentChar = 0;

int melody[] = {

  NOTE_E7, NOTE_E7, 0, NOTE_E7,
  0, NOTE_C7, NOTE_E7, 0,
  NOTE_G7, 0, 0,  0,
  NOTE_G6, 0, 0, 0,
 
  NOTE_C7, 0, 0, NOTE_G6,
  0, 0, NOTE_E6, 0,
  0, NOTE_A6, 0, NOTE_B6,
  0, NOTE_AS6, NOTE_A6, 0,
 
  NOTE_G6, NOTE_E7, NOTE_G7,
  NOTE_A7, 0, NOTE_F7, NOTE_G7,
  0, NOTE_E7, 0, NOTE_C7,
  NOTE_D7, NOTE_B6, 0, 0,
 
  NOTE_C7, 0, 0, NOTE_G6,
  0, 0, NOTE_E6, 0,
  0, NOTE_A6, 0, NOTE_B6,
  0, NOTE_AS6, NOTE_A6, 0,
 
  NOTE_G6, NOTE_E7, NOTE_G7,
  NOTE_A7, 0, NOTE_F7, NOTE_G7,
  0, NOTE_E7, 0, NOTE_C7,
  NOTE_D7, NOTE_B6, 0, 0
};

int noteDurations[] = {
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
 
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
 
  9, 9, 9,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
 
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
 
  9, 9, 9,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
};

int noteCount = 78;

void setup() {
  lc.shutdown(0, false);
  lc.setIntensity(0, 8);
  lc.clearDisplay(0);
}

void loop() {
  int i, j, k;
  int n;

  while (1) {
    currentCharIndexSave2 = currentCharIndex;
    currentCharBitSave2 = currentCharBit;
    for (i = 5; i >= 0; i--) {
      for (j = 0; j < 8; j++) {
        byte outputByte = 0;
        currentChar = message[currentCharIndex];
        currentCharIndexSave = currentCharIndex;
        currentCharBitSave = currentCharBit;
        for (k = 7; k >= 0; k--) {
          byte currentCharBits = alphabeth[((currentChar - 32) * 8) + j];
          if (currentCharBits & (1 << currentCharBit)) {
            outputByte |= (1 << k);
          }
          currentCharBit++;
          if (currentCharBit > lengthTable[currentChar - 32]) {
            currentCharBit = 0;
            currentCharIndex += 1;
            if (currentCharIndex + 1 > messageSize) {
              currentCharIndex = 0;
            }
            currentChar = message[currentCharIndex];
          }
        }
        lc.setRow(i, j, outputByte);
        if (j != 7) {
          currentCharIndex = currentCharIndexSave;
          currentCharBit = currentCharBitSave;
        }
      }
    }
    int noteDuration = 500 / noteDurations[n];
    tone(8, melody[n], noteDuration);
    int pauseBetweenNotes = noteDuration * 1.30;
    delay(pauseBetweenNotes);
    noTone(8);
    n++;
    if (n == noteCount) {
      n = 0;
    }
    
    currentCharIndex = currentCharIndexSave2;
    currentCharBit = currentCharBitSave2;
    currentChar = message[currentCharIndex];
    currentCharBit++;
    if (currentCharBit > lengthTable[currentChar - 32]) {
      currentCharBit = 0;
      currentCharIndex += 1;
      if (currentCharIndex + 1 > messageSize) {
        currentCharIndex = 0;
      }
      currentChar = message[currentCharIndex];
    }
    delay(100);
  }
}

