/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor.filas;

import gspd.ispd.motor.filas.servidores.CentroServico;
import java.util.List;

/**
 * Elemento 'cliente' do modelo de filas
 * @author denison_usuario
 */
public interface Cliente {
    public double getTamComunicacao();
    public double getTamProcessamento();
    public double getTimeCriacao();
    public CentroServico getOrigem();
    public List<CentroServico> getCaminho();
    public void setCaminho(List<CentroServico> caminho);
}
