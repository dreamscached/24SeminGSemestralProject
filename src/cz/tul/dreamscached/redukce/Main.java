package cz.tul.dreamscached.redukce;

/**
 * Main je hlavni trida programu ktery redukuje matice.
 * <p>
 * Semestralni prace cislo 24.
 *
 * @author Herman S.
 * @version 1.0.0 20/12/2022
 */
public final class Main {
    private Main() {

    }

    /**
     * Hlavni metoda tridy, entrypoint do programu.
     *
     * @param args argumenty prikazoveho radku, zadne argumenty jsou pouzivane.
     */
    public static void main(String[] args) {
        Input in; // Vzdy nacita vstup, a pokud je "validni" (t.z. ma vhodny rozmer matici) tak to zpracovava.
        while ((in = new Input()).isValid()) {
            int[][] mat = in.getMatrix();
            int[][] newMat = Matrix.reduceMatrixOptimized(mat);
            if (newMat == null) newMat = Matrix.reduceMatrix(mat);
            Matrix.printReducedMatrix(newMat);
        }

        System.out.println("Program se ukonci.");
    }
}
