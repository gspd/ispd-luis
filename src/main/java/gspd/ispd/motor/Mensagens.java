/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gspd.ispd.motor;

import gspd.ispd.motor.filas.Mensagem;

/**
 *
 * @author denison
 */
public interface Mensagens {

    public static final int CANCELAR = 1;
    public static final int PARAR = 2;
    public static final int DEVOLVER = 3;
    public static final int DEVOLVER_COM_PREEMPCAO = 4;
    public static final int ATUALIZAR = 5;
    public static final int RESULTADO_ATUALIZAR = 6;
    public static final int FALHAR = 7;
    public static final int ALOCAR_ACK = 8;
    public static final int DESLIGAR = 9; 
    public static final int DAG_PROGRAM = 10;
    public static final int DAG_ACK = 11;

    public void atenderCancelamento(Simulation simulacao, Mensagem mensagem);
    public void atenderParada(Simulation simulacao, Mensagem mensagem);
    public void atenderDevolucao(Simulation simulacao, Mensagem mensagem);
    public void atenderDevolucaoPreemptiva(Simulation simulacao, Mensagem mensagem);
    public void atenderAtualizacao(Simulation simulacao, Mensagem mensagem);
    public void atenderRetornoAtualizacao(Simulation simulacao, Mensagem mensagem);
    public void atenderFalha(Simulation simulacao, Mensagem mensagem);
    public void atenderAckAlocacao(Simulation simulacao, Mensagem mensagem);
    public void atenderDesligamento(Simulation simulacao, Mensagem mensagem);
}