package cz.tul.dreamscached;

import cz.tul.dreamscached.redukce.ReductionMain;
import cz.tul.dreamscached.vanocni.ChristmasMain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Co bys chtel(a) zpustit?");
            System.out.println("1. Vanocni prace");
            System.out.println("2. Semestralni prace");

            if (!s.hasNextInt()) {
                System.out.println("Spatny vstup!");
                continue;
            }

            int o = s.nextInt();
            if (o == 1) {
                ChristmasMain.main(new String[0]);
                break;
            } else if (o == 2) {
                ReductionMain.main(new String[0]);
                break;
            } else {
                System.out.println("Neznamy vyber!");
            }
        }
    }
}
