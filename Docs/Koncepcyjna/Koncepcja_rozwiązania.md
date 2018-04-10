##Koncepcja rozwiązania

###1. Wprowadzenie.

Aplikacja będzie stand alone aplikacją desktopową. Użytkownik będzi mógł wyszukać wpisując do paska, po nazwie, tagu i komentarzu. Wyniki pojawią się od w kolejności od najbardziej trafnego. Użytkownik będzie mógł dodać do programu *Forganizer* plik, aby on został zaindeksowany w celu wyszukiwania. Pliki znajdujące się w programie *Forganizer* będą mogły mieć dodany komentarz.

### 2. Architektura systemu

_**System składa się z:**_

* Modułu interfejsu użytkownika który będzie przyjmował od użytkownika, zapytania i kryterium do wyszukiwania oraz pliki do dodania i komentarze.
* Modułu wyszukiwania który będzie wyszukiwał indeksowane pliki według zapytania  i kryterium od modułu UI.
* Moduł komentowania  który będzie dodawał do plików komentarz, otrzymany przez moduł UI.
* Moduł dodawania plików, który będzie dodawał nazwę pliku i ścieżkę do niego otrzymane od modułu UI do bazy i indeksował je.
* Baza, (nie)relacyjna baza danych w której będzie trzymany plik tj. nazwa pliku, ścieżka do niego, komentarz jeśli taki będzie oraz id.
