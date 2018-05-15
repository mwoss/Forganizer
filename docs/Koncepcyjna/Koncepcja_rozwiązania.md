## Koncepcja rozwiązania

### 1. Wprowadzenie.

Aplikacja będzie stand alone aplikacją desktopową. Użytkownik będzi mógł wyszukać wpisując do paska, po nazwie, tagu i komentarzu. Wyniki pojawią się od w kolejności od najbardziej trafnego. Użytkownik będzie mógł dodać do programu *Forganizer* plik, aby on został zaindeksowany w celu wyszukiwania. Pliki znajdujące się w programie *Forganizer* będą mogły mieć dodany komentarz.

### 2. Architektura systemu

_**System składa się z:**_

* Modułu interfejsu użytkownika który będzie przyjmował od użytkownika, zapytania i kryterium do wyszukiwania oraz pliki do dodania i komentarze.
* Modułu wyszukiwania który będzie wyszukiwał indeksowane pliki według zapytania  i kryterium od modułu UI.
* Moduł komentowania  który będzie dodawał do plików komentarz, otrzymany przez moduł UI.
* Moduł dodawania plików, który będzie dodawał nazwę pliku i ścieżkę do niego otrzymane od modułu UI do lokalnego folderu w którym będzie indeksował je.

![](https://github.com/agh-ki-io/Forganizer/blob/master/docs/Koncepcyjna/architektura_systemu.png)

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

**d) dodawanie pliku**
* Moduł dodawania pliku
* Moduł indeksowania pliku


### 4. Zakres odpowiedzialności modułów:

**a) Moduł UI:**
* dostarczenie interfejsu użytkownikowi
* pobieranie danych od użytkownika
* zwracanie wyników

**b) Moduł wyszukiwania po nazwie:**
* wyszukanie zindeksowanego pliku po nazwie

**c) Moduł wyszukiwania po tagu:**
* wyszukanie zindeksowanego pliku po tagu pod jakim się on znajduje

**d) Moduł wyszukiwania po komentarzu:**
* wyszukanie zindeksowanego pliku po komentarzu dodanym do niego

**e) Moduł dodawania komentarzy do pliku:**
* dodanie do pliku komentarzu

**f) Moduł dodawania pliku:**
* dodanie do folderu z indeksami tj. jego nazwy i ścieżki do niego.

**g) Moduł indeksowania pliku:**
* poindeksowanie plików w celu ich późniejszego wyszukiwania


### 5. Współpraca modułów

Przepływ informacji między modułami odbywa się przy pomocy struktur języka programowania Java. Dane o plikach znajdują się w folderze lokalnym łącznie z wszystkimi indeksami.

Użytkownik wprowadza do Modułu UI (Interfejs Użytkownika) informacje o tym, w jaki sposób chiałby wyszukać plik oraz kryterium wyszukiwania. Moduł UI jest również odpowiedzialny za wyświetlanie danych i kryterium. Nastepnie jest tworzony obiekt, który jest przesyłany do modułu wyszukiwania, który korzystając z folderu z indeksami wyszukuje pliki spełniające kryterium wyszukiwania. Moduł wyszukiwania mając już listę plików przekazuje je do Modułu GUI, który je otrzymuje i wyświetla dla użytkownika.

Użytkownik wprowadza do Modułu UI (Interfejs Użytkownika) informacje o tym, w jaki sposób chiałby nadać plikowi jakąś własność (komentarz bądź tag) i wprowadza potrzebne do tego informacje. Moduł UI również wyświetla wprowadzone dane. Nastepnie jest tworzony obiekt, który jest dostarczany modułowi komentowania, który zrobi odpowiednią zmianę w folderze lokalnym w postaci komentarzu/tagu.

Użytkownik wprowadza do Modułu UI (Interfejs Użytkownika) informacje o pliku, który chciałby dodać do programu, w raz z potrzebnymi danymi, nastepnie towrzy obiekt, który będzie przekazany modułowi dodawania plików. Modłu UI również wyświetla wprowadzone dane. Moduł dodawania plików dodaje do lokalnego indeks folderu nowy plik.


![](https://github.com/agh-ki-io/Forganizer/blob/master/docs/Koncepcyjna/diagram_sekwencji.png)

_Rys. 1. Diagram sekwencji._
***
