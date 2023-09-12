# ClickerBot.Bots.ClickerBot For Sunflower Land

Version: 1.0.0

## Requirements

- Java JDK version 11.0.16

## Dotacja

Jeżeli ktoś chciałbym się odwzdzieczyć za tego bota, to zachęcam do wysłania MATIC lub SFL na poniższy adres

```0x8c8B0a1039c6ce8f557fBF08204a281Dda45FB87```

## Włączanie konsoli w różnych systemach:

1) Windows - Najprośćiej jest w wejść w katalog, gdzie mamy naszego bota, kliknąć `Plik -> `Otwórz program Windows PowerShell`
2) Linux - Podobnie jak windows wchodzimy w katalog, gdzie jest bot i klikamy prawym na pustym miejscu obok folderów i wybieramy `Otwórz w terminalu`
3) Mac - nie mam wiec, nie podpowiem :P ale myśle, ze jest podobne rozwiązanie

## Instalacja Java

1) Instalujemy Java JDK z oficjalnej strony - https://www.java.com/en/ - jest to platforma, która dziła na windowsie, linuxie czy macu, wiec każdemu z was powinno to działąć.
2) Po zainstalowaniu spradzamy w konsoli czy poprawnie zainstalowaliśmy Jave po przez wpisanie `java --version`, jeżeli wszystko jest ok powiniśmy dostać coś takiego
```text
openjdk 11.0.20.1 2023-08-24
OpenJDK Runtime Environment (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)
```

## Odczytywanie koordynatów

1) Kompilujemy nasz program do odczytywania koordynatów. (Robimy to tylko raz na początku, jest to czysto techniczna rzecz, nie musisz jej rozumieć, wystarczy że wpiszesz w konsoli poniższą komende)
```shell
javac ClickerBot/CoordinateMain.java
```
2) Odpalamy nasz program za pomocą polecenia:
```shell
java ClickerBot.CoordinateMain
```

3) Program się uruchomi i mamy 2 sekundy na ustawienie myszki w miejscu gdzie poźniej bot ma wykonywać akcje i czekamy. Dostajemy taką informacje
```text
----- Coordinate printer ------
Program uruchomi się za 2 sekundy.
Position X: 1056
Position Y: 440
{1056,440}
```
Najwazniejsze dla nas są `{1056,440}` ten kawałek kodu będziemy kopiować bezpośrendio do konfiguracji.

## Konfiguracja wombat
1) Kopiujemy plik `wombat.json_example` pod nową nazwą `wombat.json`. Tak wygląda przykładowy plik.
```json
{
  "waitRun": 301,
  "maxRuns": 180,
  "runButton": [750,493],
  "rewardButton": [796,638],
  "wombatTab": [370,12],
  "refresh": [87,52],
  "treasure": [1440,150],
  "requestAll": [758,532],
  "helpAll": [1423,786]
}
```
2) Ustawiamy w `waitRun` czas w sekundach ile trwa nasz run + 1 sekunda zapasu (301 jest dla 5min runów)
3) Dla `maxRuns` ustalamy ile runów ma zrobić podczas jednego uruchomienia.
4) Teraz ustawiamy koordynaty dla poszczególnych przycisków interfacu (opis jak odczytać koordynaty jest wyżej `Odczytywanie koordynatów`)

runButton - to przycisk do startu nowego runu
rewardButton - to przycisk, który klikamy po zakończeniu się runu
wombatTab - tutaj podejmy współrzędne naszej zakładki w przeglądarce z botem (bot ma funkcje pracy także z SFL na przemian)
refresh - tutaj podejemy współrzędne przycisku odświeżenia strony w przeglądarce
treasure - tataj podajemy współrzędne przycisku z zbieraniem skarbu
requestAll - tutaj podejemy współrzędne przycisku do poproszenia o pomoc wszystkich (jeżeli masz kupioną tą opcje, to pod aktualnym pozostałym czasem przebiegu się on pokazuje)
helpAll - tutaj podejym współrzeðne przycisku do udzielania pomocy (jest on w prawym dolnym rogu obok menu po odpaleniu kolejengo runu)

UWAGA! - Jak nie masz wykupionej pomocy dla wszystkich, to `requestAll` i `helpAll` wpisz koordynaty jakiegoś pustego miejsca na strony, tak aby bot mógł tam sobie kliknąć, ale nie wykonał żadnej akcji

## Odpalanie bota dla wombat

1) W pierwszej kolejności kompilujemy naszego bota (tak samo jak robiliśmy to dla programu do odczytywania koordynatów).
```shell
javac -sourcepath . -cp json-20140107.jar ClickerBot/WombatMain.java
```
2) Odpalamy bota
```shell
java ClickerBot.WombatMain
```

Pogram po 2 sekunsach zacznie działać :)