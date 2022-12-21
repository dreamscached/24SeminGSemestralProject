import java.util.Scanner;

/**
 * Trida Input zpracovava vstup a ho uklada.
 *
 * @author Herman S.
 * @version 1.0.0 20/12/2022
 */
public final class Input {
    /**
     * Scanner pro nacitani vstupu.
     */
    private final Scanner scanner;

    /**
     * Zadana uzivateli matice.
     */
    private final int[][] matrix;

    /**
     * Sestavuje novou instanci classu Input a nacita rozmer matici a matici z stdin.
     */
    public Input() {
        this.scanner = new Scanner(System.in);
        int n = readMatrixSize();
        if (n <= 0) this.matrix = null;
        else this.matrix = readMatrix(n, n);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vraci matice kterou zadal uzivatel.
     *
     * @return matice.
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Kontroluje kdyz uzivatel zadal vhodny rozmer matice (vetsi nebo rovna se jedne.)
     * @return true kdyz se potrebuje zpracovat vstupni matice, jinak false.
     */
    public boolean isValid() {
        return matrix != null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Nacita rozmer matici, predtim vypisuje 'rozmer matice' jako napovedu.
     *
     * @return zadane cislo, coz znamena rozmer matici
     */
    private int readMatrixSize() {
        System.out.println("Rozmer matice");
        return scanner.nextInt();
    }

    /**
     * Nacita matici s rozmerem M x N, predtim vypisuje 'zadej matici' jako navopedu.
     *
     * @param m pocet radku
     * @param n pocet sloupcu
     * @return matici zadene uzivateli
     */
    private int[][] readMatrix(int m, int n) {
        // Kdyz uzivatel zadava rozmery 0x0 vrati prazdnou matici.
        if (m == n && n == 0) return new int[0][0];

        // Deklaruje matice.
        int[][] matrix = new int[m][n];

        // Nacita matice z stdin v tomto dvojitem cyklu a je vrati.
        System.out.println("Zadej matici");
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = scanner.nextInt();
        return matrix;
    }
}
