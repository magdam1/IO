digitrecognizer

Pakiet 'digitrecognizer' pozwala na rozpoznanie cyfry z czarno-białego
obrazka o wymiarach 20 x 20 pikseli.

Typowe użycie pakietu polega na:
1. Skonstruowanie obiektu klasy Core podając w argumencie plik z zapisaną w nim
   siecią neuronową (np. "network.txt").
2. Wywołanie funkcji 'recognizeDigit' podając w argumencie tablicę 'double[][]'
   opisującą obraz (patrz poniżej: Format danych wejściowych).
   Opcjonaly drugi parametr 'threshold' to próg ufności (z przedziału [0,1]).
   Jeśli sieć nie jest wystarczająca "pewna" w rozpoznaniu, zwraca -1.
   Wywołanie 'recognizeDigit' bez drugiego argumentu zawsze powoduje zwrócenie
   jakiejś liczby całkowitej od 0 do 9 (chyba że zostanie rzucony wyjątek).


Format danych wejściowych:
1. Argument funkcji 'recognizeDigit' to tablica 'double[][] data' opisująca
   obraz zapisany kolumnowy, czyli w formacie:
   data[col][row] = intensywność piksela w kolumnie 'col' i wierszu 'row'
                    jako liczba z przedziału [0,1] (np. intensywność piksela)
                    0 oznacza kolor tła
                    1 oznacza kolor pierwszoplanowy (rozpoznawanej cyfry)

   W szególności:
   data[0][0]       to lewy górny piksel,
   data[0][size]    to lewy dolny piksel,
   data[size][0]    to prawy górny piksel,
   data[size][size] to prawy dolny piksel,

   Wymiar obrazu musi zgadzać się z wymiarem wynikającym z sieci, który można
   odczytać ze zmiennej 'imageSize' klasy 'Core'.
   Z tym przypadku wymiar to 20 x 20 pikseli.

   Nieprawidłowy format obrazu powoduje rzucenie wyjątku
   'InvalidImageFormatException'


2. Plik opisujący sieć neuronową. Zawiera opis macierzy pomiędzy warstwami:
    - pierwszy wiersz: 1 liczba całkowita oznaczająca liczbę macierzy
    - fragmenty opisujące jedną macierz (ich liczba jest zapisana w 1 wierszu):
      pierwszy wiersz: liczba wierszy, drugi wiersz: liczba kolumn, następnie
      tyle wierszy ile w macierzy liczba oddzielonych białymi znakami w liczbie
      równej liczbie kolumn.

   Nieprawidłowy format pliku wejściowego powoduje rzucenie wyjątku
   'InvalidFileFormatException'


Podsumowując, typowe użycie pakietu to:
double[][] image = new double[20][20];
core = new Core(new File("network.txt"));
int recognized_digit = core.recognizeDigit(image, 0.3);
if (recognized_digit == -1) {
  System.out.println("Nie rozpoznano cyfry! Rozpoznawacz nie osiągnął pewności 30%.")
} else {
  System.out.println("Rozpoznana cyfra to: " + String.valueOf(recognized_digit));
}