/* ==========================================================
 * iSPD : iconic Simulator of Parallel and Distributed System
 * ==========================================================
 *
 * (C) Copyright 2010-2014, by Grupo de pesquisas em Sistemas Paralelos e Distribuídos da Unesp (GSPD).
 *
 * Project Info:  http://gspd.dcce.ibilce.unesp.br/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------
 * JSimulacaoGrafica.java
 * ---------------
 * (C) Copyright 2014, by Grupo de pesquisas em Sistemas Paralelos e Distribuídos da Unesp (GSPD).
 *
 * Original Author:  Denison Menezes (for GSPD);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 
 * 14-Out-2014 : Version 2.0.1;
 *
 */
package gspd.ispd.gui.iconico.simulacao;

import gspd.ispd.arquivo.xml.ConfiguracaoISPD;
import gspd.ispd.motor.SimulacaoGrafica;
import gspd.ispd.arquivo.xml.IconicoXML;
import gspd.ispd.gui.JResultados;
import gspd.ispd.gui.JSimulacao;
import gspd.ispd.motor.SimulationProgress;
import gspd.ispd.motor.filas.RedeDeFilas;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.metricas.Metricas;
import java.awt.Color;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.w3c.dom.Document;

/**
 *
 * @author denison_usuario
 */
public class JSimulacaoGrafica extends javax.swing.JDialog implements Runnable {

    private Document modelo;
    private ResourceBundle palavras;
    private SimulationProgress progrSim;
    private SimpleAttributeSet configuraCor = new SimpleAttributeSet();
    private Thread threadSim;
    private SimulacaoGrafica sim = null;
    JResultados janelaResultados = null;
    private boolean error;
    private ConfiguracaoISPD configuracao;

    /**
     * Creates new form JSimulacaoGrafica
     */
    public JSimulacaoGrafica(javax.swing.JFrame parent, Document modelo, ResourceBundle palavs, ConfiguracaoISPD configuracao) {
        super(parent, true);
        this.error = false;
        this.modelo = modelo;
        this.palavras = palavs;
        this.configuracao = configuracao;
        this.progrSim = new SimulationProgress() {
            @Override
            public void incProgresso(int n) {
            }

            @Override
            public void print(String text, Color cor) {
                javax.swing.text.Document doc = jTextPaneNotifica.getDocument();
                try {
                    if (cor != null) {
                        StyleConstants.setForeground(configuraCor, cor);
                    } else {
                        StyleConstants.setForeground(configuraCor, Color.black);
                    }
                    if (palavras.containsKey(text)) {
                        doc.insertString(doc.getLength(), palavras.getString(text), configuraCor);
                    } else {
                        doc.insertString(doc.getLength(), text, configuraCor);
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(JSimulacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (sim != null) {
                    sim.setFinalizar(true);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTime = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSliderVelocidade = new javax.swing.JSlider();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneNotifica = new javax.swing.JTextPane();
        jLabelSpeed = new javax.swing.JLabel();
        jButtonPlay = new javax.swing.JButton();
        jButtonResults = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelTime.setText("Time: 0.0 s");

        jScrollPane1.setAutoscrolls(true);

        jSliderVelocidade.setMinimum(1);
        jSliderVelocidade.setValue(1);
        jSliderVelocidade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderVelocidadeStateChanged(evt);
            }
        });

        jTextPaneNotifica.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(jTextPaneNotifica);

        jLabelSpeed.setText("Speed");

        jButtonPlay.setText("Pause ▮▮");
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });

        jButtonResults.setText("Results");
        jButtonResults.setEnabled(false);
        jButtonResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResultsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonResults)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPlay)
                        .addGap(52, 52, 52)
                        .addComponent(jLabelSpeed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSliderVelocidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSpeed)
                        .addComponent(jButtonPlay)
                        .addComponent(jButtonResults))
                    .addComponent(jSliderVelocidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSliderVelocidadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderVelocidadeStateChanged
        if (sim != null) {
            double velocidade = jSliderVelocidade.getValue() / 10;
            sim.setIncremento(velocidade);
        }
    }//GEN-LAST:event_jSliderVelocidadeStateChanged

    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayActionPerformed
        if (sim != null && !sim.isParar()) {
            sim.setParar(true);

            jButtonPlay.setText("Play ▶");
        } else {
            sim.setParar(false);
            jButtonPlay.setText("Pause ▮▮");
        }
    }//GEN-LAST:event_jButtonPlayActionPerformed

    private void jButtonResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResultsActionPerformed
        if (janelaResultados != null) {
            janelaResultados.setVisible(true);
        }
    }//GEN-LAST:event_jButtonResultsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonResults;
    private javax.swing.JLabel jLabelSpeed;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSliderVelocidade;
    private javax.swing.JTextPane jTextPaneNotifica;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        progrSim.println("Simulation Initiated.");
        try {
            progrSim.print("Opening iconic model.");
            progrSim.print(" -> ");
            progrSim.println("OK", Color.green);
            //Verifica se foi construido modelo na area de desenho
            progrSim.validarInicioSimulacao(modelo);
            double t1 = System.currentTimeMillis();
            progrSim.println("* Simulation *");
            //criar grade
            progrSim.print("  Mounting network queue.");
            progrSim.print(" -> ");
            RedeDeFilas redeDeFilas = IconicoXML.newRedeDeFilas(modelo);
            progrSim.println("OK", Color.green);
            progrSim.print("  Creating tasks.");
            progrSim.print(" -> ");
            List<Tarefa> tarefas = IconicoXML.newGerarCarga(modelo).toTarefaList(redeDeFilas);
            progrSim.print("OK\n  ", Color.green);
            //Verifica recursos do modelo e define roteamento
            sim = new SimulacaoGrafica(progrSim, jLabelTime, redeDeFilas, tarefas, 0.1);//[30%] --> 40 %
            sim.criarRoteamento();
            //Realiza asimulação
            progrSim.println("  Simulating.");
            //recebe instante de tempo em milissegundos ao iniciar a simulação
            sim.simular();
            if (!sim.isFinalizar()) {
                Metricas metrica = sim.getMetricas();
                //Recebe instante de tempo em milissegundos ao fim da execução da simulação
                double t2 = System.currentTimeMillis();
                //Calcula tempo de simulação em segundos
                progrSim.println("  Simulation Execution Time = " + ((t2 - t1) / 1000) + "seconds");
                progrSim.print("Showing results.");
                progrSim.print(" -> ");
                // janelaResultados = new JResultados(this, metrica, redeDeFilas, tarefas, configuracao);
                janelaResultados = new JResultados(metrica, redeDeFilas, (List<Tarefa>) tarefas);
                progrSim.println("OK", Color.green);
                janelaResultados.setLocationRelativeTo(this);
                jButtonResults.setEnabled(true);
            }
        } catch (IllegalArgumentException erro) {
            this.error = true;
            progrSim.println(erro.getMessage() + " !", Color.red);
            progrSim.print("Simulation Aborted", Color.red);
            progrSim.println("!", Color.red);
        }
    }

    public void iniciarSimulacao() {
        threadSim = new Thread(this);
        threadSim.start();
        try {
            while (sim == null && !error) {
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(JSimulacaoGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!error) {
            DesenhoSimulacao ds = new DesenhoSimulacao(sim);
            jScrollPane1.setViewportView(ds);
            jScrollPane1.setAutoscrolls(true);
            jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        }
    }
}
