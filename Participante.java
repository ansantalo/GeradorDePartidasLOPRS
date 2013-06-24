/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */
public class Participante {
    
    public String nome;
    public String IdNoGrupo;
    public int jogos;
    public int vitorias;
    public int saldo;    
    
    Participante() {
        nome = "";
    }
    
    Participante(String nome, String idNoGrupo) {
        this.nome = nome;
        this.IdNoGrupo = idNoGrupo;
        jogos = Integer.MIN_VALUE;
        vitorias = Integer.MIN_VALUE;
        jogos = Integer.MIN_VALUE;
    }
    
    Participante(String nome) {
        this.nome = nome;
    }
    
    public void resetaDesempenho () {
        jogos = 0;
        vitorias = 0;
        saldo = 0;
    }
    
    public int desempenhoCru() {
        return vitorias*100 + saldo;
    }

    ParticipanteEliminatoria toParticipanteEliminatoria(String id, boolean b) {
        return new ParticipanteEliminatoria(this, id, b);
    }
    
}
