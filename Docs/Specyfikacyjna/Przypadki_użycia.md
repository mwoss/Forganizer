## Przypadki użycia

### Przypadek użycia: Wyszukiwanie pliku po nazwie
Aktor główny: Użytkownik  
Streszczenie: Użytkownik wpisuje w pasek wyszukiwania nazwę, lub jej część, i pojawiają mu się pliki po nazwie.  
Warunek początkowy: Użytkownik chce wyszukać plik pamiętając nazwę pliku lub tylko jego część.

#### Scenariusz podstawowy:
1. Użytkownik wpisuje w pole wyszukiwania dostępne dla systemu słowo będące nazwą pliku lub jego częścią.
2. _**Lucene**_ na podstawie tej nazwy wyszukuje zindeksowane wcześniej nazwy plików i przekazuje je do systemu.
3. System prezentuje użytkownikowi otrzymane wyniki.

***

### Przypadek użycia: Dodawanie komentarzy
Aktor główny: Użytkownik  
Streszczenie: Użytkownik chce dodać komentarz do konkretnego pliku.  
Warunek początkowy: Chęć dodania dodatkowej informacji do pliku przez użytkownika celem późniejszego łatwego wyszukiwania.

#### Scenariusz podstawowy:
1. Użytkownik wybiera plik i klika przycisk “dodaj komentarz”.
2. System wyświetla użytkownikowi pole do wpisania tekstu (komentarza).
3. Użytkownik wprowadza komentarz zatwierdzając go odpowiednim przyciskiem.
4. System zapisuje komentarz dla danego pliku.

#### Scenariusz alternatywny:
1. Użytkownik wybiera plik i klika przycisk “dodaj komentarz”.
2. System wyświetla użytkownikowi pole do wpisania tekstu (komentarza).
3. Użytkownik odrzuca wprowadzony przez siebie komentarz odpowiednim przyciskiem.
4. System nie zapisuje komentarza, anuluje tę operację.

***

### Przypadek użycia: Wyszukiwanie po komentarzu
Aktor główny: Użytkownik
Streszczenie: Użytkownik chce wyszukać plik pamiętając komentarz lub jego część, jaką dodał do tego pliku.
Warunek początkowy: Użytkownik wybiera opcję wyszukiwania po komentarzu.

#### Scenariusz podstawowy:
1. Użytkownik wpisuje w pole wyszukiwania dostępne dla systemu słowo będące komentarzem lub jego częścią dla plików, które je zawierają
2. System wyszuka, posegreguje według trafności i wyświetli użytkownikowi pliki na podstawie wprowadzonych informacji.

***

### Przypadek użycia: Dodawanie tagu do pliku
Aktor główny: Użytkownik
Streszczenie: Użytkownik chce dodać tag do konkretnego pliku.
Warunek początkowy: Chęć dodania dodatkowej informacji do pliku przez użytkownika celem pogrupowania ich w zbiory o kryteriach zadanych przez użytkownika, służące łatwiejszemu ich wyszukiwaniu.

#### Scenariusz podstawowy:
1. Użytkownik klika dla pliku w przycisk “dodaj tag”.
2. System wyświetla użytkownikowi pole do wpisania tekstu (tag).
3. System na bieżąco podrzuca użytkownikowi propozycje nazw tagów już istniejących w systemie.
4. Użytkownik wybiera z wcześniejszych tagów lub wprowadza nowy tag.
5. Użytkownik zatwierdza swój wybór odpowiednim przyciskiem.
6. System dopisuje dany tag do pliku.

#### Scenariusz alternatywny:
1. Użytkownik klika dla pliku w przycisk “dodaj tag”.
2. System wyświetla użytkownikowi pole do wpisania tekstu (tag).
3. System na bieżąco podrzuca użytkownikowi propozycje nazw tagów już istniejących w systemie.
4. Użytkownik wybiera z wcześniejszych tagów lub wprowadza nowy tag.
5. Użytkownik odrzuca swój wybór odpowiednim przyciskiem.
6. System nie zapisuje zmian powiązań dla pliku.

***
### Przypadek użycia: Wyszukiwanie plików po tagu
Aktor główny: Użytkownik
Streszczenie: Użytkownik chce wyszukać pliki otagowane konkretną etykietą.
Warunek początkowy: Użytkownik wybiera opcję wyszukiwania po tagu.

#### Scenariusz podstawowy:
1. Użytkownik wpisuje w pole wyszukiwania dostępne dla systemu słowo będące tagiem dla wyszukiwanych plików.
2. System wyszuka, posegreguje według trafności i wyświetli użytkownikowi pliki na podstawie wprowadzonych tagów.

***

### Przypadek użycia: Dodawanie pliku do systemu
Aktor główny: Użytkownik
Streszczenie: Użytkownik chce dodać plik do systemu, celem zaindeksowania go.
Warunek początkowy: Plik nie jest jeszcze w systemie i użytkownik wybrał opcję dodania go.

#### Scenariusz podstawowy:
1. Użytkownik wybiera plik spośród listy plików, nie będących w systemie, lub podając jego lokalizację.
2. System sprawdza, czy ma już ten plik zindeksowany w systemie.
3. System dodaje plik do systemu, informując o tym użytkownika.

#### Scenariusz alternatywny:
1. Użytkownik wybiera plik spośród listy plików, nie będących w systemie, lub podając jego lokalizację.
2. System sprawdza, czy ma już ten plik zindeksowany w systemie.
3. System nie dodaje plik do systemu, informując użytkownika o tym, że ma już zindeksowany taki plik.

***

### Przypadek użycia: Wyszukiwanie pliku po zawartości
Aktor główny: Użytkownik
Streszczenie: Użytkownik wpisuje w pasek wyszukiwania jakąś frazę z pliku.
Warunek początkowy: Użytkownik chce wyszukać plik pamiętając fragment zawartości z tego pliku oraz wybierze opcję wyszukiwania po zawartości.

#### Scenariusz podstawowy:
1. Użytkownik wpisuje w pole wyszukiwania dostępne dla systemu frazę występującą w zawartości szukanego pliku.
2. _**Lucene**_ na podstawie tej frazy wyszukuje zindeksowane wcześniej zawartości plików i zwraca dopasowania przekazując je do systemu.
3. System prezentuje użytkownikowi otrzymane wyniki.
