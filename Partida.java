/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */
public class Partida {
    
    int saldoA;
    int saldoB;
    
    Partida(int saldoA, int saldoB) {
        this.saldoA = saldoA;
        this.saldoB = saldoB;
    }
    
    Partida(String saldoA, String saldoB) {
        try {
            int a = Integer.parseInt(saldoA);
            int b = Integer.parseInt(saldoB);
            this.saldoA = a;
            this.saldoB = b;
        } catch (Exception e) {
            this.saldoA = 0;
            this.saldoB = 0;
        }
    }
    
    Partida() {
        saldoA = 0;
        saldoB = 0;
    }
    
    public boolean isValida() {
        if ((saldoA >= 0 && saldoA <= 6) &&
            (saldoB >= 0 && saldoB <= 6) &&
            !(saldoA == 0 && saldoB == 0))
            return true;
        else
            return false;
    }
    
}
