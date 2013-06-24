
import util.Util;
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

class ComparadorSegundosLugares implements Comparator<Participante> {    
    @Override
    public int compare(Participante a, Participante b) {
        return (a.desempenhoCru()>b.desempenhoCru() ? 1 : (a.desempenhoCru()==b.desempenhoCru() ? 0 : -1));
    }
}

public class Torneio {
    
    int nDeParticipantes; //não inclui o líder
    int nDeGrupos;
    
    Grupo A;
    Grupo B;
    Grupo C;
    Grupo D;
    Grupo E;
    Grupo F;
    Participante Lider;
    
    Eliminatoria Eliminatorias;
    
    Torneio() {
        Eliminatorias = new Eliminatoria();
    }
    
    private int determinaNumeroDeGrupos(int participantes) throws Exception {
        int nDeGrupos = -1;
        
        if (participantes >= 6 && participantes <= 8)
            nDeGrupos = 2;
        else if (participantes >= 9 && participantes <= 11)
            nDeGrupos = 3;
        else if (participantes >= 12 && participantes <= 16)
            nDeGrupos = 4;
        else if (participantes >= 17 && participantes <= 20)
            nDeGrupos = 5;
        else if (participantes >= 21 && participantes <= 24)
            nDeGrupos = 6;
        
        if (nDeGrupos < 0) {
            throw new Exception("Número inválido de participantes.");
        }
        
        return nDeGrupos;        
    }
    
    public void geraGrupos(ArrayList<String> nomeDosParticipantes) throws Exception {
        A = new Grupo("A");
        B = new Grupo("B");
        C = new Grupo("C");
        D = new Grupo("D");
        E = new Grupo("E");
        F = new Grupo("F");
         
        nDeParticipantes = nomeDosParticipantes.size();
        nDeGrupos = determinaNumeroDeGrupos(nDeParticipantes);
        
        int grupoATerParticipanteSorteado = 0;
        for (int i = 0; i < nDeParticipantes; i++) {
            int participanteEscolhido = Util.random(0, nomeDosParticipantes.size() - 1);
            switch (grupoATerParticipanteSorteado) {
                case 0:
                    A.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
                case 1:
                    B.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
                case 2:
                    C.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
                case 3:
                    D.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
                case 4:
                    E.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
                case 5:
                    F.adicionaParticipante(nomeDosParticipantes.get(participanteEscolhido));
                    break;
            }
            nomeDosParticipantes.remove(participanteEscolhido);
            grupoATerParticipanteSorteado++;
            if (grupoATerParticipanteSorteado == nDeGrupos)
                grupoATerParticipanteSorteado = 0;
        }
        
    }
    
    public ArrayList<ParticipanteEliminatoria> passaramParaSegundaFase() {
        ArrayList<ParticipanteEliminatoria> passaram = new ArrayList<>();
        int index = 0;
        
        // adiciona primeiros lugares
        switch (nDeGrupos) {
            case 6:
                passaram.add(F.primeiroLugar());
            case 5:
                passaram.add(E.primeiroLugar());
            case 4:
                passaram.add(D.primeiroLugar());
            case 3:
                passaram.add(C.primeiroLugar());
            case 2:       
                passaram.add(A.primeiroLugar());    
                passaram.add(B.primeiroLugar());
        }
        
        // adiciona segundos lugares
        switch (nDeGrupos) {
            case 6:
                passaram.addAll(melhoresSegundosLugares(2));
                break;
            case 5:
                passaram.addAll(melhoresSegundosLugares(3));
                break;
            case 4:
                passaram.addAll(melhoresSegundosLugares(4));
                break;
            case 3:
                passaram.addAll(melhoresSegundosLugares(1));
                break;
            case 2:
                passaram.addAll(melhoresSegundosLugares(2));
                break;
        }
                
        return passaram;
    }
    
    public ArrayList<ParticipanteEliminatoria> melhoresSegundosLugares(int quantos) {
        ArrayList<Participante> todosSegundosLugares = new ArrayList<>();
        int index = 0;
        switch (nDeGrupos) {
            case 6:
                todosSegundosLugares.add(F.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "F";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
            case 5:
                todosSegundosLugares.add(E.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "E";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
            case 4:
                todosSegundosLugares.add(D.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "D";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
            case 3:
                todosSegundosLugares.add(C.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "C";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
            case 2:
                todosSegundosLugares.add(B.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "B";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
                todosSegundosLugares.add(A.segundoLugar());
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).grupoOriginario = "A";
                ((ParticipanteEliminatoria)todosSegundosLugares.get(index)).eraSegundoLugar = true;
                index++;
        }
        Collections.sort(todosSegundosLugares, new ComparadorSegundosLugares());        
        return new ArrayList(todosSegundosLugares.subList(0, quantos));
    }
    
    public void resetaDesempenhoParticipantes() {
        A.ParticipanteA.resetaDesempenho();
        A.ParticipanteB.resetaDesempenho();
        A.ParticipanteC.resetaDesempenho();
        A.ParticipanteD.resetaDesempenho();
        B.ParticipanteA.resetaDesempenho();
        B.ParticipanteB.resetaDesempenho();
        B.ParticipanteC.resetaDesempenho();
        B.ParticipanteD.resetaDesempenho();
        C.ParticipanteA.resetaDesempenho();
        C.ParticipanteB.resetaDesempenho();
        C.ParticipanteC.resetaDesempenho();
        C.ParticipanteD.resetaDesempenho();        
        D.ParticipanteA.resetaDesempenho();
        D.ParticipanteB.resetaDesempenho();
        D.ParticipanteC.resetaDesempenho();
        D.ParticipanteD.resetaDesempenho();
        E.ParticipanteA.resetaDesempenho();
        E.ParticipanteB.resetaDesempenho();
        E.ParticipanteC.resetaDesempenho();
        E.ParticipanteD.resetaDesempenho();
        F.ParticipanteA.resetaDesempenho();
        F.ParticipanteB.resetaDesempenho();
        F.ParticipanteC.resetaDesempenho();
        F.ParticipanteD.resetaDesempenho();
    }
    
    public String toString() {
        return "Lider: " + Lider.nome + "\n" +
                A.toString() + 
                B.toString() + 
                C.toString() + 
                D.toString() + 
                E.toString() + 
                F.toString() +
                Eliminatorias.toString();
    }
    
}
