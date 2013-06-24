
import enums.EstadoEliminatoria;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */
public class ParticipanteEliminatoria extends Participante {
    
    public EstadoEliminatoria estadoEliminatoria;
    public String grupoOriginario;
    public boolean eraSegundoLugar;
    
    int jogos_elim;
    int saldo_elim;
    int vitorias_elim;

    public ParticipanteEliminatoria(String nome) {
        super(nome);
    }
    
    ParticipanteEliminatoria(Participante p, String grupoOriginario, boolean eraSegundoLugar) {
        this.grupoOriginario = grupoOriginario;
        this.eraSegundoLugar = eraSegundoLugar;
        this.IdNoGrupo = p.IdNoGrupo;
        this.jogos = p.jogos;
        this.nome = p.nome;
        this.saldo = p.saldo;
        this.vitorias = p.vitorias;
        
        resetaDesempenhoElim();
    }
    
    public void resetaDesempenhoElim() {
        jogos_elim = 0;
        saldo_elim = 0;
        vitorias_elim = 0;
    }
    
    public String toString() {
        return "Grupo " + grupoOriginario + (eraSegundoLugar ? " 2º" : " 1º");
    }
    
}