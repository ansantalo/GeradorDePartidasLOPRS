
import enums.TipoConfrontoDireto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */

class ComparadorDeSaldo implements Comparator<Participante> {

    @Override
    public int compare(Participante a, Participante b) {
        return (a.saldo>b.saldo ? -1 : (a.saldo==b.saldo ? 0 : 1));
    }
}

public class Grupo {
 
    Participante ParticipanteA;
    Participante ParticipanteB;
    Participante ParticipanteC;
    Participante ParticipanteD;
    
    Partida AB;
    Partida CD;
    Partida AC;
    Partida BD;
    Partida BC;
    Partida AD;
    
    String id;
    
    Grupo (String id) {
        this.id = id;
        ParticipanteA = new Participante();
        ParticipanteB = new Participante();
        ParticipanteC = new Participante();
        ParticipanteD = new Participante();
        AB = new Partida();
        CD = new Partida();
        AC = new Partida();
        BD = new Partida();
        BC = new Partida();
        AD = new Partida();
    }
    
    public int numeroDeParticipantes() {
        int n = 0;
        if (ParticipanteA.nome != "")
            n++;
        if (ParticipanteB.nome != "")
            n++;
        if (ParticipanteC.nome != "")
            n++;
        if (ParticipanteD.nome != "")
            n++;
        return n;
    }
    
    public void adicionaParticipante(String nome) throws Exception {
        switch (numeroDeParticipantes()) {
            case 0:
                ParticipanteA = new Participante(nome,"A");
                break;
            case 1:
                ParticipanteB = new Participante(nome,"B");
                break;
            case 2:
                ParticipanteC = new Participante(nome,"C");
                break;
            case 3:
                ParticipanteD = new Participante(nome,"D");
                break;
            default:
                throw new Exception("Tentou adicionar participantes em um grupo com " + numeroDeParticipantes() + " participantes.");
        }
    }
    
    public ParticipanteEliminatoria segundoLugar() {
        return participantesOrdenadosPorDesempenho().get(1).toParticipanteEliminatoria(this.id, true);
    }
    
    public ParticipanteEliminatoria primeiroLugar() {
        return participantesOrdenadosPorDesempenho().get(0).toParticipanteEliminatoria(this.id, false);
    }
    
    public ArrayList<Participante> participantesOrdenadosPorDesempenho() {
        ArrayList<Participante> participantesOrdenados = new ArrayList();
        ArrayList<Participante> participantesDesordenados = new ArrayList();
        participantesDesordenados.add(ParticipanteA);
        participantesDesordenados.add(ParticipanteB);
        participantesDesordenados.add(ParticipanteC);
        if (numeroDeParticipantes() > 3)
            participantesDesordenados.add(ParticipanteD);

        // Ordena por vitórias
        ArrayList<Participante> participantesCom3V = new ArrayList();
        ArrayList<Participante> participantesCom2V = new ArrayList();
        ArrayList<Participante> participantesCom1V = new ArrayList();
        ArrayList<Participante> participantesCom0V = new ArrayList();
        for (Participante p : participantesDesordenados)
            switch (p.vitorias) {
                case 0:
                    participantesCom0V.add(p);
                    break;
                case 1:
                    participantesCom1V.add(p);
                    break;
                case 2:
                    participantesCom2V.add(p);
                    break;
                case 3:
                    participantesCom3V.add(p);
                    break;
            }
        
        // Ordena por Confronto Direto e Saldo
        if (participantesCom3V.size() == 1)
            participantesOrdenados.add(participantesCom3V.get(0));
        
        if (participantesCom2V.size() == 1)
            participantesOrdenados.add(participantesCom2V.get(0));
        else {
            if ((participantesCom2V.size() == 2) && (houveConfronto(participantesCom2V.get(0).IdNoGrupo, participantesCom2V.get(1).IdNoGrupo))) {
                participantesOrdenados.add(confrontoDireto(participantesCom2V.get(0).IdNoGrupo, participantesCom2V.get(1).IdNoGrupo, TipoConfrontoDireto.GANHADOR));
                participantesOrdenados.add(confrontoDireto(participantesCom2V.get(0).IdNoGrupo, participantesCom2V.get(1).IdNoGrupo, TipoConfrontoDireto.PERDEDOR));
            }
            else {
                Collections.sort(participantesCom2V, new ComparadorDeSaldo());
                for (Participante p: participantesCom2V)
                    participantesOrdenados.add(p);
            }
        }
        
        if (participantesCom1V.size() == 1)
            participantesOrdenados.add(participantesCom1V.get(0));
        else {
            if ((participantesCom1V.size() == 2) && (houveConfronto(participantesCom1V.get(0).IdNoGrupo, participantesCom1V.get(1).IdNoGrupo))) {
                participantesOrdenados.add(confrontoDireto(participantesCom1V.get(0).IdNoGrupo, participantesCom1V.get(1).IdNoGrupo, TipoConfrontoDireto.GANHADOR));
                participantesOrdenados.add(confrontoDireto(participantesCom1V.get(0).IdNoGrupo, participantesCom1V.get(1).IdNoGrupo, TipoConfrontoDireto.PERDEDOR));
            }
            else {
                Collections.sort(participantesCom1V, new ComparadorDeSaldo());
                for (Participante p: participantesCom1V)
                    participantesOrdenados.add(p);
            }
        }
        
        if (participantesCom0V.size() == 1)
            participantesOrdenados.add(participantesCom0V.get(0));
        else if (participantesCom0V.size() > 1) {
            Collections.sort(participantesCom0V, new ComparadorDeSaldo());
            for (Participante p: participantesCom0V)
                participantesOrdenados.add(p);
        }
        
        return participantesOrdenados;
    }
    
    public boolean houveConfronto(String participanteA, String participanteB) {
        switch (participanteA) {
            case "A":
                switch (participanteB) {
                    case "B":
                        if (AB.saldoA != AB.saldoB)
                            return true;
                        break;
                    case "C":
                        if (AC.saldoA != AC.saldoB)
                            return true;
                        break;
                    case "D":
                        if (AD.saldoA != AD.saldoB)
                            return true;
                        break;
                }
                break;  
            case "B":
                switch (participanteB) {
                    case "A":
                        if (AB.saldoA != AB.saldoB)
                            return true;
                        break;
                    case "C":
                        if (BC.saldoA != BC.saldoB)
                            return true;
                        break;
                    case "D":
                        if (BD.saldoA != BD.saldoB)
                            return true;
                        break;
                }
                break;
            case "C":
                switch (participanteB) {
                    case "A":
                        if (AC.saldoA != AC.saldoB)
                            return true;
                        break;
                    case "B":
                        if (BC.saldoA != BC.saldoB)
                            return true;
                        break;
                    case "D":
                        if (CD.saldoA != CD.saldoB)
                            return true;
                        break;
                }
                break;
            case "D":
                switch (participanteB) {
                    case "A":
                        if (AD.saldoA != AD.saldoB)
                            return true;
                        break;
                    case "B":
                        if (BD.saldoA != BD.saldoB)
                            return true;
                        break;
                    case "C":
                        if (CD.saldoA != CD.saldoB)
                            return true;
                        break;
                }
                break;
        }
        return false;
    }
    
    public Participante confrontoDireto(String participanteA, String participanteB, TipoConfrontoDireto tipoConfrontoDireto) {
        Participante retornoEsperado = new Participante();
        switch (participanteA) {
            case "A":
                switch (participanteB) {
                    case "B":
                        if (AB.saldoA > AB.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteB;
                        else if (AB.saldoB > AB.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteA;
                        break;
                    case "C":
                        if (AC.saldoA > AC.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteC;
                        else if (AC.saldoB > AC.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteA;
                        break;
                    case "D":
                        if (AD.saldoA > AD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteD;
                        else if (AD.saldoB > AD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteA;
                        break;
                }
                break;  
            case "B":
                switch (participanteB) {
                    case "A":
                        if (AB.saldoA > AB.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteB;
                        else if (AB.saldoB > AB.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteA;
                        break;
                    case "C":
                        if (BC.saldoA > BC.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteC;
                        else if (BC.saldoB > BC.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteB;
                        break;
                    case "D":
                        if (BD.saldoA > BD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteD;
                        else if (BD.saldoB > BD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteB;
                        break;
                }
                break;
            case "C":
                switch (participanteB) {
                    case "A":
                        if (AC.saldoA > AC.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteC;
                        else if (AC.saldoB > AC.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteA;
                        break;
                    case "B":
                        if (BC.saldoA > BC.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteC;
                        else if (BC.saldoB > BC.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteB;
                        break;
                    case "D":
                        if (CD.saldoA > CD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteD;
                        else if (CD.saldoB > CD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteC;
                        break;
                }
                break;
            case "D":
                switch (participanteB) {
                    case "A":
                        if (AD.saldoA > AD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteA : ParticipanteD;
                        else if (AD.saldoB > AD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteA;
                        break;
                    case "B":
                        if (BD.saldoA > BD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteB : ParticipanteD;
                        else if (BD.saldoB > BD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteB;
                        break;
                    case "C":
                        if (CD.saldoA > CD.saldoB)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteC : ParticipanteD;
                        else if (CD.saldoB > CD.saldoA)
                            retornoEsperado = tipoConfrontoDireto == TipoConfrontoDireto.GANHADOR ? ParticipanteD : ParticipanteC;
                        break;
                }
                break;
        }
        return retornoEsperado;
    }
    
    public String toString() {
        String grupo = "Grupo " + id + "\n";
        switch (numeroDeParticipantes()) {
            case 0:
                return "";
            case 3:
                grupo += ParticipanteA.nome + " " + AB.saldoA + " X " + AB.saldoB + " " + ParticipanteB.nome + "\n" +
                ParticipanteA.nome + " " + AC.saldoA + " X " + AC.saldoB + " " + ParticipanteC.nome + "\n" +
                ParticipanteB.nome + " " + BC.saldoA + " X " + BC.saldoB + " " + ParticipanteC.nome + "\n";
                break;
            case 4:
                grupo += ParticipanteA.nome + " " + AB.saldoA + " X " + AB.saldoB + " " + ParticipanteB.nome + "\n" +
                ParticipanteC.nome + " " + CD.saldoA + " X " + CD.saldoB + " " + ParticipanteD.nome + "\n" +
                ParticipanteA.nome + " " + AC.saldoA + " X " + AC.saldoB + " " + ParticipanteC.nome + "\n" +
                ParticipanteB.nome + " " + BD.saldoA + " X " + BD.saldoB + " " + ParticipanteD.nome + "\n" +
                ParticipanteB.nome + " " + BC.saldoA + " X " + BC.saldoB + " " + ParticipanteC.nome + "\n" +
                ParticipanteA.nome + " " + AD.saldoA + " X " + AD.saldoB + " " + ParticipanteD.nome + "\n";
                break;
        }
        return grupo;
    }
    
}
