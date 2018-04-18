## Koncepcja rozwiązania

### 1. Wprowadzenie.

Aplikacja będzie stand alone aplikacją desktopową. Użytkownik będzi mógł wyszukać wpisując do paska, po nazwie, tagu i komentarzu. Wyniki pojawią się od w kolejności od najbardziej trafnego. Użytkownik będzie mógł dodać do programu *Forganizer* plik, aby on został zaindeksowany w celu wyszukiwania. Pliki znajdujące się w programie *Forganizer* będą mogły mieć dodany komentarz.

### 2. Architektura systemu

_**System składa się z:**_

* Modułu interfejsu użytkownika który będzie przyjmował od użytkownika, zapytania i kryterium do wyszukiwania oraz pliki do dodania i komentarze.
* Modułu wyszukiwania który będzie wyszukiwał indeksowane pliki według zapytania  i kryterium od modułu UI.
* Moduł komentowania  który będzie dodawał do plików komentarz, otrzymany przez moduł UI.
* Moduł dodawania plików, który będzie dodawał nazwę pliku i ścieżkę do niego otrzymane od modułu UI do bazy i indeksował je.
* Baza, relacyjna baza danych w której będzie trzymany plik tj. nazwa pliku, ścieżka do niego, komentarz jeśli taki będzie oraz id.

![](https://github.com/agh-ki-io/Forganizer/blob/master/Docs/Koncepcyjna/architektura_systemu.png)

_Rys. 1. Schemat architektury systemu._


### 3. Moduły:

**a) po stronie interfejsu użytkownika:**
* Moduł UI

**b) po stronie modułu wyszukiwania:**
* Moduł wyszukiwania po nazwie
* Moduł wyszukiwania po tagu
* Moduł wyszukiwania po komentarzu

**c) dodawanie komentarzy:**
* Moduł dodawania komentarzy do pliku

**d)dodawanie pliku**
* Moduł dodawania pliku
* Moduł indeksowania pliku

**e) baza z nazwą pliku i ścieżką do niego**


### 4. Zakres odpowiedzialności modułów:

**a) Moduł UI:**
* dostarczenie interfejsu użytkownikowi
* pobieranie danych od użytkownika
* zwracanie wyników

**b)Moduł wyszukiwania po nazwie:**
* wyszukanie po indeksowanego pliku po nazwie

**c)Moduł wyszukiwania po tagu:**
* wyszukanie po indeksowanego pliku po tagu pod jakim się on znajduje

**d)Moduł wyszukiwania po komentarzu:**
* wyszukanie po indeksowanego pliku po komentarzu dodanym do niego

**e) Moduł dodawania komentarzy do pliku:**
* dodanie do pliku komentarzu

**f) Moduł dodawania pliku:**
* dodanie do bazy pliku tj. jego nazwy i ścieżki do niego.

**g) Moduł indeksowania pliku:**
* po indeksowanie plików w celu ich późniejszego wyszukiwania

**h) baza z nazwą pliku i ścieżką do niego:**
* przechowywanie nazwy pliku, ścieżki do niego, jego tagu i id.
