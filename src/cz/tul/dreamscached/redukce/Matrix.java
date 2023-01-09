package cz.tul.dreamscached.redukce;

/**
 * Trida Matrix je utility class pro zpracovani matic.
 *
 * @author Herman S.
 * @version 1.0.0 20/12/2022
 */
public final class Matrix {
    /**
     * Soukromy konstruktor protoze cz.tul.dreamscached.redukce.Matrix je utility class a ma jenom staticke metody.
     */
    private Matrix() {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vytiska matici.
     *
     * @param mat matice
     */
    public static void printReducedMatrix(int[][] mat) {
        // V pripade kdyz je to matice z jenom jednim cislem, tak to taky muzeme optimalizovat a
        // vytisknout jenom mat[0][0].
        if (isSq1UnreducibleMatrix(mat)) {
            System.out.println("Redukovana matice (1 x 1)");
            System.out.println(mat[0][0]);

        // Ve vsech ostatnich pripadech pouzijeme cykly.
        } else {
            System.out.printf("Redukovana matice (%d x %d)\n", mat.length, mat[0].length);
            String format = "%" + findLongestElementCharLength(mat) + "d"; // Nachazi nejdelsi cislo pro padding

            for (int[] row : mat) {
                System.out.printf(format, row[0]); // Vytiskuje prvni prvek bez predchoziho mezerniku
                for (int i = 1; i < row.length; i++) System.out.printf(" " + format, row[i]); // Vytiskuje s pocatkem 1
                System.out.println(); // Vytiskuje newline a ukonci radek
            }
        }

        // Vytisknout navic jeden line break
        System.out.println();
    }

    /**
     * Nachazi nejdelsi cislo v matici (pocet znaku v cisle.)
     *
     * @param mat matice
     * @return pocet znaku v retezcovem zapisu cisla
     */
    private static int findLongestElementCharLength(int[][] mat) {
        int max = getNumberCharLength(mat[0][0]);
        for (int[] row : mat) {
            for (int it : row) {
                int len = getNumberCharLength(it);
                if (len > max) max = len;
            }
        }

        return max;
    }

    /**
     * Vypocita pocet znaku v cisle.
     *
     * @param n cislo pro ktere se potrebuje najit pocet znaku
     * @return pocet znaku v retezcovem zapisu cisla
     */
    private static int getNumberCharLength(int n) {
        // log10 od nuly bude nula, ale nula stale potrebuje jeden znak.
        if (n == 0) return 1;

        // Pro zaporne cisla se taky potrebuje znak minus.
        if (n < 0) return (int) Math.log10(Math.abs(n)) + 2;

        // log10 + 1 vrati cislo ktere je pocet znaku + nejaka drobna cast.
        return (int) Math.log10(n) + 1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Konstantni hodnota vracena z isSq2ReducesToSq1Matrix znamenajici ze 2x2 matrice ma tvar [0 X, Y 0],
     * coz znamena ze levy horni roh matici je nulou.
     */
    public static final int REDUCIBLE_SQ2_MATRIX_LC_ZERO = 0;

    /**
     * Konstantni hodnota vracena z isSq2ReducesToSq1Matrix znamenajici ze 2x2 matrice ma tvar [X 0, 0 Y],
     * coz znamena ze pravy horni roh matici je nulou.
     */
    public static final int REDUCIBLE_SQ2_MATRIX_RC_ZERO = 1;

    /**
     * Konstantni hodnota vracena z isSq2ReducesToSq1Matrix znamenajici ze matice nema tvar 2x2.
     */
    public static final int NON_SQ2_MATRIX = -2;

    /**
     * Konstantni hodnota vracena z isSq2ReducesToSq1Matrix znamenajici ze matice nemuze byt optimalizovane
     * redukovana protoze nema tvar [0 X, Y 0] nebo [X 0, 0 Y].
     */
    public static final int NON_REDUCIBLE_SQ2_MATRIX = -1;

    /**
     * Kontroluje kdyz zadana matice je jenom jedne cislo.
     *
     * @param mat matice
     * @return true kdyz matice ma jenom jeden prvek
     */
    public static boolean isSq1UnreducibleMatrix(int[][] mat) {
        return mat.length == 1 && mat[0].length == 1;
    }

    /**
     * Kontroluje kdyz zadana matice je dvouprvkova matice
     * a muze byt zredukovana do jednoho prvku (kdyz ma tvar [X 0, 0 X] nebo [0 X, X 0].)
     *
     * @param mat matice
     * @return -2 kdyz matice nema tvar 2x2, -1 kdyz nemuze byt zredukovana zadnym zpusobem, 0
     * kdyz muze byt zredukovana a vysledkem bude cislo na pozici x=1, y=1, 1 kdyz vysledkem
     * bude cislo na pozici x=1, y=0
     * @see Matrix#REDUCIBLE_SQ2_MATRIX_LC_ZERO
     * @see Matrix#REDUCIBLE_SQ2_MATRIX_RC_ZERO
     * @see Matrix#NON_SQ2_MATRIX
     * @see Matrix#NON_REDUCIBLE_SQ2_MATRIX
     */
    public static int isSq2ReducesToSq1Matrix(int[][] mat) {
        // Matice nema tvar 2x2.
        if (mat.length != 2 || mat[0].length != 2) return NON_SQ2_MATRIX;
        // Matice ma nuly na (0,0) a (1, 1) - nula je v levem rohu.
        if (mat[0][0] == 0 && mat[1][1] == 0) return REDUCIBLE_SQ2_MATRIX_LC_ZERO;
        // Matice ma nuly na (0,1) a (1, 0) - nula je v pravem rohu.
        if (mat[0][1] == 0 && mat[1][0] == 0) return REDUCIBLE_SQ2_MATRIX_RC_ZERO;
        // Jinak nemuze zadnym zpusobem jeji redukovat.
        return NON_REDUCIBLE_SQ2_MATRIX;
    }

    /**
     * Dela rychlou castecnou kontrolu kdyz matice muze byt zredukovana
     * (vrati true kdyz matice nema nuly v prvnim radku, coz znamena ze matice nemuze mit
     * takovej sloupec a radek ktere by obsahovali jenom nuly a nejake nenulove cislo ve stredu.)
     *
     * @param mat matice
     * @return false kdyz se potrebuje redukce, true kdyz redukce je mozna preskocit
     */
    private static boolean canSkipReduction(int[][] mat) {
        // Rychle proskenuje prvni radek a kdyz se narazi na nulu tak vrati false.
        for (int i = 0; i < mat[0].length; i++) if (mat[0][i] == 0) return false;
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Nachazi radek a sloupec ve kterem matice ma jenom jedne nenulove cislo a vrati jeho polohu.
     *
     * @param mat matice
     * @return pole s delkou 2 kde 1. prvek je radek a 2. prvek je sloupec
     */
    public static int[] findSingleNonZeroValuePosition(int[][] mat) {
        // V pripade kdyz to redukuje do [1] tak to vyhazuje IndexOutOfBounds,
        // a to nepotrebujeme
        if (isSq1UnreducibleMatrix(mat)) return null;

        // V tomto dvojitem cyklu pro kazdy prvek se provadi kontrola
        // kdyz je to sloupec a radek ktere se potrebuje odstranit
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++)
                // Kdyz ty radek a sloupec se potrebuje odstranit tak vrati
                // jejich polohu jako pole s dva prvky, x a y
                if (isNonZeroCenterCross(mat, i, j))
                    return new int[]{i, j};

        // Kdyz zadne radek a sloupec nebyli nalezeny tak vrati null
        return null;
    }

    /**
     * Kontroluje, kdyz zadana poloha je centrem (nenulovem cislem) v krizi nul (sloupec a radek jsou jenom nuly mimo
     * centrelniho cisla ktere neni nula.)
     *
     * @param mat matice
     * @param x   radek
     * @param y   sloupec
     * @return true kdyz zadana poloha je centrem, jinak false
     */
    private static boolean isNonZeroCenterCross(int[][] mat, int x, int y) {
        // Protoze dle ulohy hleda kriz z nul se stredem majici nenulovou hodnotu,
        // kdyz zadana poloha ukazuje na nulu tak to neni tim co hledame.
        if (mat[x][y] == 0) return false;

        for (int i = 0; i < mat.length; i++) {
            if (i != x) {
                // Pokud neukazuje i na radek x, tak kontroluje ze v radku i na miste y mame nulu, jinak
                // neni to tim co potrebujeme.
                if (mat[i][y] != 0) return false;
            } else for (int j = 0; j < mat[0].length; j++) {
                // Kdyz narazil cyklus na radek i, tak se potrebuje zkontrolovat
                // ze ma radek z nul s nenulovym stredem, jinak vrati false.
                if (j != y && mat[i][j] != 0) return false;
            }
        }

        // Vsechno v poradku, je to kriz.
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Odstranuje zadany radek z matici.
     * <p>
     * Pozor! Tato metoda predpoklada, ze volajici odstranuje radek ktery v
     * matice existuje, to znamena ze volajici nezkusi odstranovat radek s indexem
     * vetsim, nez vyska matice.
     *
     * @param mat matice
     * @param n   cislo radku
     * @return nova matice bez zadaneho radku
     */
    public static int[][] popRow(int[][] mat, int n) {
        // newMat je nova matice s poctem radku o jeden mensi nez puvodni matice,
        // protoze nejaky radek potrebujeme odstranit.
        int[][] newMat = new int[mat.length - 1][mat[0].length];

        // V tomto dvojitem cyklu metoda prekopiruje matice mat do matice newMat
        // a preskakuje radek s indexem n.
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++)
                // Pokud jeste index j je mensi nez index odstraneneho radku, tak proste
                // kopiruje hodnoty z puvodni matice do nove.
                if (i < n) newMat[i][j] = mat[i][j];
                    // Kdyz j je vetsi nez n tak dal pouzije j-1.
                else if (i > n) newMat[i - 1][j] = mat[i][j];

        return newMat;
    }

    /**
     * Odstranuje zadany sloupec z matici.
     * <p>
     * Pozor! Tato metoda predpoklada, ze volajici odstranuje sloupec ktery v
     * matice existuje, to znamena ze volajici nezkusi odstranovat sloupec s indexem
     * vetsim, nez sirka matice.
     *
     * @param mat matice
     * @param n   cislo sloupcu
     * @return nova matice bez zadaneho sloupce
     */
    public static int[][] popColumn(int[][] mat, int n) {
        // newMat je nova matice s poctem sloupcu o jeden mensi nez puvodni matice,
        // protoze nejaky sloupec potrebujeme odstranit.
        int[][] newMat = new int[mat.length][mat[0].length - 1];

        // V tomto dvojitem cyklu metoda prekopiruje matice mat do matice newMat
        // a preskakuje sloupec s indexem n.
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++)
                // Pokud jeste index j je mensi nez index odstraneneho sloupce, tak proste
                // kopiruje hodnoty z puvodni matice do nove.
                if (j < n) newMat[i][j] = mat[i][j];
                    // Kdyz j je vetsi nez n tak dal pouzije j-1.
                else if (j > n) newMat[i][j - 1] = mat[i][j];

        return newMat;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Zkusi redukovat matici pomoci nejrychlejsi zpusoby kdyz matice splni jednou z uvedenych
     * podminek:
     * <ul>
     *     <li>redukce matice muze byt preskocena</li>
     *     <li>matice musi byt prazdna</li>
     *     <li>matice musi mit jenom jeden prvek</li>
     *     <li>matice musi mit ctyri prvky a mit tvar [0 X, X 0] nebo [X 0, 0 X]</li>
     * </ul>
     *
     * @param mat matice
     * @return redukovana matice nebo null kdyz matice nesplni podminky pro optimalizovanou redukce
     */
    @SuppressWarnings("EnhancedSwitchMigration") // Ztlumi varovani o tom ze je mozna pouzit 'enhanced switch'
    public static int[][] reduceMatrixOptimized(int[][] mat) {
        // Kdyz matice ma jenom jeden prvek
        if (Matrix.isSq1UnreducibleMatrix(mat)) return mat;

        // Kdyz to muze vubec preskocit tak vrati puvodni matice.
        if (canSkipReduction(mat)) return mat;

        // Jinak kdyz to je 2x2 matice (nebo ne, a proto vrati NON_SQ2_MATRIX), tak udela redukce
        // pomoci jednodussiho algoritmu nez dvojity cyklus.
        else switch (Matrix.isSq2ReducesToSq1Matrix(mat)) {
            // Kdyz je to matice 2x2 a ma nuly na (1,0) a (0,1) tak vrati hodnotu na (1,1)
            // Napr.: matice [2 0
            //                0 3] muze byt redukovana jednodusim metodem, proste si vezmeme
            // hodnotu na (1, 1) protoze jinak bychom odstranili radek 1 sloupec 1 a meli
            // bychom tenhle samy prvek.
            case REDUCIBLE_SQ2_MATRIX_RC_ZERO:
                return new int[][]{{mat[1][1]}};

            // Kdyz je to matice 2x2 a ma nuly na (0,1) a (1,0) tak vrati hodnotu na (1,0)
            // Napr.: matice [0 2
            //                3 0] muze byt redukovana jednodusim metodem, proste si vezmeme
            // hodnotu na (1, 0) protoze jinak bychom odstranili radek 1 sloupec 1 a meli
            // bychom tenhle samy prvek.
            case REDUCIBLE_SQ2_MATRIX_LC_ZERO:
                return new int[][]{{mat[1][0]}};

            // Kdyz nemuze optimalizovane redukovat nebo neni to matice 2x2, tak vrati null.
            case NON_REDUCIBLE_SQ2_MATRIX:
            case NON_SQ2_MATRIX:
            default:
                return null;
        }
    }

    /**
     * Redukuje matici pro vsechny pripady.
     *
     * @param mat matice
     * @return redukovana matice
     */
    public static int[][] reduceMatrix(int[][] mat) {
        // Nova matice, a pro pouziti v cyklu na pocatku provedeni to je reference na puvodni matice.
        // Ale to se nemutuje, pristi to se prepise novou matici s odstreneny radky a sloupce a nijak puvodni matice
        // neovlivne.
        int[][] newMat = mat;

        int[] xy; // Pokud v matici jeste exustuji radky a sloupce s jednym nenulovym prvkem, tak to neni null.
        while ((xy = Matrix.findSingleNonZeroValuePosition(newMat)) != null) {
            newMat = Matrix.popRow(newMat, xy[0]);
            newMat = Matrix.popColumn(newMat, xy[1]);
        }

        // Vrati novou matice.
        return newMat;
    }
}
