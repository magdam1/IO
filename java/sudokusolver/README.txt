sudokusolver

Pakiet 'sudokusolver' pozwala na pełną analizę zadanego sudoku. Umożliwia:
- rozpoznanie nieprawidłowego ustawienia planszy
- pozyskanie listy cyfr, które kolejno należy wpisać w celu rozwiązania sudoku
  (zwracane są w kolejności symulującej rozwiązywanie sudoku przez człowieka)
- zwrócenie pełnej, rozwiązanej planszy

Klasy o widoczności publicznej:
1. SudokuSolver
Głowna klasa pakietu, konstruuje się ją przy pomocy tablicy int[][] lub obiektu
klasy SudokuBoard.
Posiada metodę 'solve()', która spełnia funkcje opisane powyżej. Przyjmuje ona
argument 'maxSolutions', który ogranicza liczbę zwracanych rozwiązań.

2. SudokuBoard
Opakowanie tablicy int[][].

3. SudokuSolution
Rozwiązanie sudoku, czyli lista kolejnych cyfr do wpisania w planszę
(lista obiektów SudokuChoice).

4. SudokuChoice
Obiekt informujący, w który wiersz i kolumnę należy wpisać daną liczbę.

5. NoSolutionException
Wyjątek rzucany przy próbie pozyskania pełnej planszy (czyli wywołania
metody 'getSolvedSudoku()' lub 'getSolvedBoard') w przypadku gdy nie ma
żadnych rozwiązań.

UWAGA: Wyjątek nie jest rzucany przy wywołaniu 'solve()' w przypadku braku
rozwiązań - zwracana jest po prostu pusta lista rozwiązań.



Typowe użycie pakietu to:

int[][] board = {...}
SudokuSolver solver = new SudokuSolver(board);
List<SudokuSolution> solutions = solver.solve(1);

ewentualnie później:
SudokuBoard solved = solver.getSolvedSudoku();
lub:
int[][] solved = solver.getSolvedBoard();