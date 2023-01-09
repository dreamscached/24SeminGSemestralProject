package cz.tul.dreamscached.redukce;

import java.util.Arrays;

/**
 * Trida Test je testovaci trida pro kontrolu spravne praci kodu tohoto programu.
 *
 * @author Herman S.
 * @version 1.0.0 20/12/2022
 */
public final class Test {
    private Test() {

    }

    /**
     * Hlavni metoda (entrypoint) kterou se zacina program s testy.
     * @param args command-line argumenty (zadne nejsou pouzivane.)
     */
    public static void main(String[] args) {
        try {
            testOptimizationUtils();
            testFindNonZeroValue();
            testPopMethods();
            testOptimizedMatrixReduction();
            testGeneralMatrixReduction();
        } catch (Exception e) {
            failWithStacktrace(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void testOptimizationUtils() {
        beginTestSuite("testOptimizationUtils");

        /* Testovani isSq1UnreducibleMatrix */
        beginTest("isSq1UnreducibleMatrix");
        doTest(!Matrix.isSq1UnreducibleMatrix(new int[][]{}));
        doTest(Matrix.isSq1UnreducibleMatrix(new int[][]{{1}}));
        doTest(!Matrix.isSq1UnreducibleMatrix(new int[][]{{1, 2}, {3, 4}}));
        endTest();

        /* Testovani isSq2ReducesToSq1Matrix */
        beginTest("isSq2ReducesToSq1Matrix");
        doTest(Matrix.isSq2ReducesToSq1Matrix(new int[][]{{0}}) == Matrix.NON_SQ2_MATRIX);
        doTest(Matrix.isSq2ReducesToSq1Matrix(new int[][]{{0, 2}, {3, 0}}) == Matrix.REDUCIBLE_SQ2_MATRIX_LC_ZERO);
        doTest(Matrix.isSq2ReducesToSq1Matrix(new int[][]{{2, 0}, {0, 3}}) == Matrix.REDUCIBLE_SQ2_MATRIX_RC_ZERO);
        doTest(Matrix.isSq2ReducesToSq1Matrix(new int[][]{{2, 1}, {4, 3}}) == Matrix.NON_REDUCIBLE_SQ2_MATRIX);
        endTestSuite();
    }

    private static void testFindNonZeroValue() {
        beginTestSuite("testFindNonZeroValue");

        /* Testovani findSingleNonZeroValuePosition */
        beginTest("findSingleNonZeroValuePosition");
        doTest(Arrays.equals(Matrix.findSingleNonZeroValuePosition(new int[][]{{1, 0, 0}, {0, 1, 1}, {0, 1, 1}}), new int[]{0, 0}));
        doTest(Arrays.equals(Matrix.findSingleNonZeroValuePosition(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}), new int[]{2, 2}));
        doTest(Arrays.equals(Matrix.findSingleNonZeroValuePosition(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}), null));
        endTestSuite();
    }

    private static void testPopMethods() {
        beginTestSuite("testPopMethods");

        /* Testovani popRow */
        beginTest("popRow");
        int[][] mat = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        doTest(Arrays.deepEquals(Matrix.popRow(mat, 0), new int[][]{{4, 5, 6}, {7, 8, 9}}));
        doTest(Arrays.deepEquals(Matrix.popRow(mat, 1), new int[][]{{1, 2, 3}, {7, 8, 9}}));
        doTest(Arrays.deepEquals(Matrix.popRow(mat, 2), new int[][]{{1, 2, 3}, {4, 5, 6}}));
        endTest();

        /* Testovani popColumn */
        beginTest("popColumn");
        mat = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        doTest(Arrays.deepEquals(Matrix.popColumn(mat, 0), new int[][]{{2, 3}, {5, 6}, {8, 9}}));
        doTest(Arrays.deepEquals(Matrix.popColumn(mat, 1), new int[][]{{1, 3}, {4, 6}, {7, 9}}));
        doTest(Arrays.deepEquals(Matrix.popColumn(mat, 2), new int[][]{{1, 2}, {4, 5}, {7, 8}}));
        endTestSuite();
    }

    private static void testOptimizedMatrixReduction() {
        beginTestSuite("testOptimizedMatrixReduction");

        /* Testovani reduceMatrixOptimized */
        beginTest("reduceMatrixOptimized");

        // Tady je skalar, matice z jednoho prvku, a vrati stejnou matice.
        doTest(Arrays.deepEquals(Matrix.reduceMatrixOptimized(new int[][]{{1}}), new int[][]{{1}}));

        // Tady je 2x2 matice ve tvaru [X 0, 0 Y] a vysledkem bude Y z praveho dolniho rohu.
        doTest(Arrays.deepEquals(Matrix.reduceMatrixOptimized(new int[][]{{2, 0}, {0, 1}}), new int[][]{{1}}));

        // Tady je 2x2 matice ve tvaru [0 X, Y 0] a vysledkem bude Y z leveho dolniho rohu.
        doTest(Arrays.deepEquals(Matrix.reduceMatrixOptimized(new int[][]{{0, 2}, {3, 0}}), new int[][]{{3}}));

        // Tady je matice ktera na rychly pohled nema zadne radky a sloupce ktery mohou byt odstraneny,
        // a reduceMatrixOptimized musi vratit stejnou matice.
        doTest(Arrays.deepEquals(Matrix.reduceMatrixOptimized(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}),
                new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}));

        // Tady je matice ktera na rychly pohled muze mit radky a sloupce ktere lze odstranit
        // ale zadny optimalizovany zpusob muze byt aplikovan, tak to musi vratit null.
        doTest(Matrix.reduceMatrixOptimized(new int[][]{{1, 0, 1}, {1, 1, 1}, {1, 1, 1}}) == null);

        endTestSuite();
    }

    private static void testGeneralMatrixReduction() {
        beginTestSuite("testGeneralMatrixReduction");

        /* Testovani reduceMatrix */
        beginTest("reduceMatrix");

        // [1 0 0        [1 2
        //  0 1 2   =>    3 4]
        //  0 3 4]
        doTest(Arrays.deepEquals(Matrix.reduceMatrix(new int[][]{{1, 0, 0}, {0, 1, 2}, {0, 3, 4}}),
                new int[][]{{1, 2}, {3, 4}}));

        // [1 0 3        [1 3
        //  0 1 0   =>    1 2]
        //  1 0 2]
        doTest(Arrays.deepEquals(Matrix.reduceMatrix(new int[][]{{1, 0, 3}, {0, 1, 0}, {1, 0, 2}}),
                new int[][]{{1, 3}, {1, 2}}));

        // [1 0 3 4        [1 3 4
        //  0 1 0 0   =>    1 2 5]
        //  1 0 2 5]
        doTest(Arrays.deepEquals(Matrix.reduceMatrix(new int[][]{{1, 0, 3, 4}, {0, 1, 0, 0}, {1, 0, 2, 5}}),
                new int[][]{{1, 3, 4}, {1, 2, 5}}));

        // [1 0 0 5        [1 0 5        [1 5
        //  0 1 0 0   =>    0 1 0   =>    7 1]
        //  0 0 1 0         7 0 1]
        //  7 0 0 1]
        doTest(Arrays.deepEquals(Matrix.reduceMatrix(
                        new int[][]{{1, 0, 0, 5}, {0, 1, 0, 0}, {0, 0, 1, 0}, {7, 0, 0, 1}}),
                new int[][]{{1, 5}, {7, 1}}
        ));

        // [17  0  9  0 18       [17  9  0 18
        //  17  0 19  0 14        17 19  0 14        [17  9 18
        //   0 12  0  0  0   =>    0  0 16  0   =>    17 19 14
        //   0  0  0 16  0        20  9  0 16]        20  9 16]
        //  20  0  9  0 16]
        doTest(Arrays.deepEquals(Matrix.reduceMatrix(
                        new int[][]{{17, 0, 9, 0, 18}, {17, 0, 19, 0, 14}, {0, 12, 0, 0, 0}, {0, 0, 0, 16, 0}, {20, 0, 9, 0, 16}}),
                new int[][]{{17, 9, 18}, {17, 19, 14}, {20, 9, 16}}
        ));

        endTestSuite();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vytiska zpravu ze nejaky test je spusten.
     *
     * @param method jmeno testujici metody.
     */
    private static void beginTest(String method) {
        System.out.printf("Probiha testovani %s... ", method);
    }

    /**
     * Vytiska zpravu ze nejaka skupina testu je spustena.
     *
     * @param name jmeno skupiny testu.
     */
    private static void beginTestSuite(String name) {
        System.out.printf("Probihaji testy z %s...\n", name);
    }

    /**
     * Vytiska OK a oznami ze test probehl uspesne.
     */
    private static void endTest() {
        System.out.println("OK.");
    }

    /**
     * Vytiska OK a oznami ze skupina testu probehl uspesne a vytiska navic jeden line break.
     */
    private static void endTestSuite() {
        System.out.println("OK.\n");
    }

    /**
     * Vytiska zpravu a vyvola vyjimky kdyz zadana hodnota neni pravda (true).
     *
     * @param expr hodnota kterou se potrebuje zkontrolovat
     */
    private static void doTest(boolean expr) {
        if (expr) return;
        System.out.println("FAIL.\n");
        throw new AssertionError();
    }

    /**
     * Vytiska zpravu ze doslo k vyjimce.
     *
     * @param e Exception pro ktere se potrebuje vypsat stacktrace.
     */
    private static void failWithStacktrace(Exception e) {
        System.out.println("FAIL.\n");
        e.printStackTrace();
    }
}
