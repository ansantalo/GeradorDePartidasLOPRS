
import enums.EstadoEliminatoria;
import enums.PartidasEliminatorias;
import enums.TipoEliminatorias;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */
public class Eliminatoria {
    
    TipoEliminatorias tipo;
    
    ParticipanteEliminatoria A;
    ParticipanteEliminatoria B;
    ParticipanteEliminatoria C;
    ParticipanteEliminatoria D;
    ParticipanteEliminatoria E;
    ParticipanteEliminatoria F;
    ParticipanteEliminatoria G;
    ParticipanteEliminatoria H;
    ParticipanteEliminatoria Lider;
    
    Partida QuartasAxB;
    Partida QuartasCxD;
    Partida QuartasExF;
    Partida QuartasGxH;    
    Partida SemiAxB;
    Partida SemiCxD;    
    Partida Final;    
    Partida Desafio;
    
    Eliminatoria() {
        this.tipo = TipoEliminatorias.NAO_GERADA;
    }
    
    Eliminatoria(TipoEliminatorias tipo, ArrayList<ParticipanteEliminatoria> participantes, ParticipanteEliminatoria Lider) {
        this.Lider = Lider;
        
        this.tipo = tipo;
        
        if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES && participantes.size() != 4)
            return;
        if (tipo == TipoEliminatorias.OITO_PARTICIPANTES && participantes.size() != 8)
            return;
        
        do {
            //System.out.println("Tentativa");
            //try {
            //    System.in.read();
            //} catch (IOException ex) {
            //    Logger.getLogger(Eliminatoria.class.getName()).log(Level.SEVERE, null, ex);
            //}
            embaralhaParticipantes(participantes);
            A = participantes.get(0);
            B = participantes.get(1);
            C = participantes.get(2);
            D = participantes.get(3);
            if (tipo == TipoEliminatorias.OITO_PARTICIPANTES) {
                E = participantes.get(4);
                F = participantes.get(5);
                G = participantes.get(6);
                H = participantes.get(7);
            }
            
            //System.out.println(A.toString() + "\n"
            //        + B.toString() + "\n\n"
            //        + C.toString() + "\n"
            //        + D.toString() + "\n\n"
            //        + E.toString() + "\n"
            //        + F.toString() + "\n\n"
            //        + G.toString() + "\n"
            //        + H.toString() + "\n---");
            
        } while (!valida());
        
        if (tipo == TipoEliminatorias.OITO_PARTICIPANTES) {
            A.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            B.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            C.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            D.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            E.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            F.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            G.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            H.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
        } else if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES) {
            A.estadoEliminatoria = EstadoEliminatoria.SEMI;
            B.estadoEliminatoria = EstadoEliminatoria.SEMI;
            C.estadoEliminatoria = EstadoEliminatoria.SEMI;
            D.estadoEliminatoria = EstadoEliminatoria.SEMI;
        }
               
    }
    
    private boolean valida() {
        switch (tipo) {
            case OITO_PARTICIPANTES:
                if ((A.grupoOriginario == B.grupoOriginario) ||
                    (A.grupoOriginario == C.grupoOriginario) ||
                    (A.grupoOriginario == D.grupoOriginario) ||
                    (B.grupoOriginario == C.grupoOriginario) ||
                    (B.grupoOriginario == D.grupoOriginario) ||
                    (C.grupoOriginario == D.grupoOriginario)) {
                    //System.out.println("Quebrou pois tinha dois jogadores do mesmo grupo do mesmo lado (LADO 1).");
                    return false;
                }
                if ((E.grupoOriginario == F.grupoOriginario) ||
                    (E.grupoOriginario == G.grupoOriginario) ||
                    (E.grupoOriginario == H.grupoOriginario) ||
                    (F.grupoOriginario == G.grupoOriginario) ||
                    (F.grupoOriginario == H.grupoOriginario) ||
                    (G.grupoOriginario == H.grupoOriginario)) {
                    //System.out.println("Quebrou pois tinha dois jogadores do mesmo grupo do mesmo lado (LADO 2).");
                    return false;
                }
                if ((A.eraSegundoLugar && B.eraSegundoLugar) ||
                    (C.eraSegundoLugar && D.eraSegundoLugar) ||
                    (E.eraSegundoLugar && F.eraSegundoLugar) ||
                    (G.eraSegundoLugar && H.eraSegundoLugar)) {
                    //System.out.println("Quebrou pois tinha DOIS SEGUNDOS LUGARES lugares se enfrentando.");
                    return false;
                }
                break;
            case QUATRO_PARTICIPANTES:
                if ((A.grupoOriginario == B.grupoOriginario))
                    return false;
                if ((C.grupoOriginario == D.grupoOriginario))
                    return false;
                if ((A.eraSegundoLugar && B.eraSegundoLugar) ||
                    (C.eraSegundoLugar && D.eraSegundoLugar))
                    return false;
                break;
        }
        return true;
    }
    
    private void embaralhaParticipantes(ArrayList<ParticipanteEliminatoria> p) {
        long seed = System.nanoTime();
        Collections.shuffle(p, new Random(seed));
    }
    
    void atualizaDesempenhoPorPartida(Partida p, ParticipanteEliminatoria A, ParticipanteEliminatoria B) {
        if (p.isValida()) {
            A.jogos_elim++;
            B.jogos_elim++;
            if (p.saldoA > p.saldoB) {
                A.vitorias_elim++;
                A.saldo_elim += p.saldoA;
                B.saldo_elim -= p.saldoA;
                A.estadoEliminatoria = EstadoEliminatoria.SEMI;
            } else {
                B.vitorias_elim++;
                A.saldo_elim -= p.saldoB;
                B.saldo_elim += p.saldoB;
                B.estadoEliminatoria = EstadoEliminatoria.SEMI;
            }
        }
    }

    void atualizaDesempenhoParticipantes() {
        A.resetaDesempenhoElim();
        B.resetaDesempenhoElim();
        C.resetaDesempenhoElim();
        D.resetaDesempenhoElim();
        if (tipo == TipoEliminatorias.OITO_PARTICIPANTES) {
            E.resetaDesempenhoElim();
            F.resetaDesempenhoElim();
            G.resetaDesempenhoElim();
            H.resetaDesempenhoElim();
        }
        
        if (tipo == TipoEliminatorias.OITO_PARTICIPANTES) {
            A.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            B.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            C.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            D.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            E.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            F.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            G.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            H.estadoEliminatoria = EstadoEliminatoria.QUARTAS;
            atualizaDesempenhoPorPartida(QuartasAxB,A,B);
            atualizaDesempenhoPorPartida(QuartasCxD,C,D);
            atualizaDesempenhoPorPartida(QuartasExF,E,F);
            atualizaDesempenhoPorPartida(QuartasGxH,G,H);
            atualizaDesempenhoPorPartida(SemiAxB,vencedorPartida(PartidasEliminatorias.QUARTAS_AXB),vencedorPartida(PartidasEliminatorias.QUARTAS_CXD));
            atualizaDesempenhoPorPartida(SemiCxD,vencedorPartida(PartidasEliminatorias.QUARTAS_EXF),vencedorPartida(PartidasEliminatorias.QUARTAS_GXH));
            atualizaDesempenhoPorPartida(Final,vencedorPartida(PartidasEliminatorias.SEMI_AXB),vencedorPartida(PartidasEliminatorias.SEMI_CXD));
            atualizaDesempenhoPorPartida(Desafio,vencedorPartida(PartidasEliminatorias.FINAL),Lider);
        } else if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES) {
            A.estadoEliminatoria = EstadoEliminatoria.SEMI;
            B.estadoEliminatoria = EstadoEliminatoria.SEMI;
            C.estadoEliminatoria = EstadoEliminatoria.SEMI;
            D.estadoEliminatoria = EstadoEliminatoria.SEMI;
            atualizaDesempenhoPorPartida(SemiAxB,A,B);
            atualizaDesempenhoPorPartida(SemiCxD,C,D);
            atualizaDesempenhoPorPartida(Final,vencedorPartida(PartidasEliminatorias.SEMI_AXB),vencedorPartida(PartidasEliminatorias.SEMI_CXD));
            atualizaDesempenhoPorPartida(Desafio,vencedorPartida(PartidasEliminatorias.FINAL),Lider);
        }  
    }
    
    public ParticipanteEliminatoria vencedorPartida(PartidasEliminatorias partida) {
        switch (partida) {
            case QUARTAS_AXB:
                if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES)
                    return A;
                if (QuartasAxB.isValida())
                    if (QuartasAxB.saldoA > QuartasAxB.saldoB)
                       return A;
                    else
                       return B;
                break;
            case QUARTAS_CXD:
                if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES)
                    return B;
                if (QuartasCxD.isValida())
                    if (QuartasCxD.saldoA > QuartasCxD.saldoB)
                       return C;
                    else
                       return D;
                break;
            case QUARTAS_EXF:
                if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES)
                    return C;
                if (QuartasExF.isValida())
                    if (QuartasExF.saldoA > QuartasExF.saldoB)
                       return E;
                    else
                       return F;
                break;
            case QUARTAS_GXH:
                if (tipo == TipoEliminatorias.QUATRO_PARTICIPANTES)
                    return D;
                if (QuartasGxH.isValida())
                    if (QuartasGxH.saldoA > QuartasGxH.saldoB)
                       return G;
                    else
                       return H;
                break;
            case SEMI_AXB:
                if (SemiAxB.isValida())
                    if (SemiAxB.saldoA > SemiAxB.saldoB)
                       return vencedorPartida(PartidasEliminatorias.QUARTAS_AXB);
                    else
                       return vencedorPartida(PartidasEliminatorias.QUARTAS_CXD);;
                break;
            case SEMI_CXD:
                if (SemiCxD.isValida())
                    if (SemiCxD.saldoA > SemiCxD.saldoB)
                       return vencedorPartida(PartidasEliminatorias.QUARTAS_EXF);
                    else
                       return vencedorPartida(PartidasEliminatorias.QUARTAS_GXH);;
                break;
            case FINAL:
                if (Final.isValida())
                    if (Final.saldoA > Final.saldoB)
                       return vencedorPartida(PartidasEliminatorias.SEMI_AXB);
                    else
                       return vencedorPartida(PartidasEliminatorias.SEMI_CXD);;
                break;
        }
        return new ParticipanteEliminatoria("__________");
    }
    
    public String toString() {
        String eliminatorias = "Eliminatorias:\n";
        switch (tipo) {
            case NAO_GERADA:
                eliminatorias += "Não aconteceu.";
                break;
            case OITO_PARTICIPANTES:
                eliminatorias += "Quartas-de-final:\n"
                    + A.nome + " " + QuartasAxB.saldoA + "x" + QuartasAxB.saldoB + " " + B.nome + "\n"
                    + C.nome + " " + QuartasCxD.saldoA + "x" + QuartasCxD.saldoB + " " + D.nome + "\n"
                    + E.nome + " " + QuartasExF.saldoA + "x" + QuartasExF.saldoB + " " + F.nome + "\n"
                    + G.nome + " " + QuartasGxH.saldoA + "x" + QuartasGxH.saldoB + " " + H.nome + "\n";
            case QUATRO_PARTICIPANTES:
                eliminatorias += "Semi-final:\n"
                    + vencedorPartida(PartidasEliminatorias.QUARTAS_AXB).nome + " " + SemiAxB.saldoA + "x" + SemiAxB.saldoB + " " + vencedorPartida(PartidasEliminatorias.QUARTAS_CXD).nome + "\n"
                    + vencedorPartida(PartidasEliminatorias.QUARTAS_EXF).nome + " " + SemiCxD.saldoA + "x" + SemiCxD.saldoB + " " + vencedorPartida(PartidasEliminatorias.QUARTAS_GXH).nome + "\n";
                eliminatorias += "Final:\n"
                    + vencedorPartida(PartidasEliminatorias.SEMI_AXB).nome + " " + Final.saldoA + "x" + Final.saldoB + " " + vencedorPartida(PartidasEliminatorias.SEMI_CXD).nome + "\n";
                eliminatorias += "Desafio:\n"
                    + vencedorPartida(PartidasEliminatorias.FINAL).nome + " " + Desafio.saldoA + "x" + Desafio.saldoB + " " + Lider.nome;
        }
        return eliminatorias;
    }
    
}
