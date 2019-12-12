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
 * Task.java
 * ---------------
 * (C) Copyright 2014, by Grupo de pesquisas em Sistemas Paralelos e Distribuídos da Unesp (GSPD).
 *
 * Original Author:  Denison Menezes (for GSPD);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 
 * 14-Out-2014 : Version 2.0.2;
 *
 */
package gspd.ispd.gui.iconico.dag;

import gspd.ispd.gui.iconico.Vertice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Representa uma tarefa DAG
 * @author denison
 */
public class Task extends Vertice implements Identificavel {

    private static Color INICIAL = new Color(150, 250, 150);
    private static Color FINAL = new Color(250, 150, 150);
    private static Color INTERMEDIARIO = Color.LIGHT_GRAY;
    private static Color UNICO = Color.GRAY;
    private static Color COMPLEXTASK = new Color(230, 230, 230);
    private static JPanel painel;
    private static JTextField jId;
    private static JTextField jsizeComu;
    private static JTextField jsizeProc;

    public static void edit(Component parent, Task icon) {
        icon = (Task) icon.line.getOrigem();
        if (painel == null) {
            jId = new JTextField();
            jId.setBorder(javax.swing.BorderFactory.createTitledBorder("Rank"));
            jsizeProc = new JTextField();
            jsizeProc.setBorder(javax.swing.BorderFactory.createTitledBorder("Computing"));
            jsizeComu = new JTextField();
            jsizeComu.setBorder(javax.swing.BorderFactory.createTitledBorder("Communication"));
            painel = new JPanel();
            painel.setLayout(new GridLayout(3, 1));
            painel.add(jId);
            painel.add(jsizeProc);
            painel.add(jsizeComu);
        }
        jId.setText(icon.rank.toString());
        jsizeComu.setText(icon.communicationSize.toString());
        jsizeProc.setText(icon.computingSize.toString());
        int opcao = JOptionPane.showConfirmDialog(
                parent,
                painel,
                "Set the size",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        //Caso okgetSize() o bloco é preenchido
        if (opcao == JOptionPane.OK_OPTION) {
            Integer taskRank = icon.rank;
            Double tamComu = icon.communicationSize;
            Double tamProc = icon.computingSize;
            try {
                taskRank = Integer.valueOf(jId.getText());
                tamComu = Double.valueOf(jsizeComu.getText());
                tamProc = Double.valueOf(jsizeProc.getText());
            } catch (Exception e) {
            }
            icon.communicationSize = tamComu;
            icon.computingSize = tamProc;
            icon.rank = taskRank;
        }
    }
    private Line line;
    private Integer rank;
    private ArrayList<Message> conectados;
    private Boolean visible;
    private Boolean dag;
    private Integer baseX;
    private Integer baseY;
    private Double computingSize = 50.0;
    private Double communicationSize = 50.0;

    public Task(int x, int y, int id) {
        super(x, y);
        this.rank = id;
        dag = true;
        visible = true;
        baseX = x;
        baseY = y;
        conectados = new ArrayList<Message>();
    }

    public Task(int x, int y, Integer id) {
        super(x, y);
        this.rank = id;
        dag = true;
        visible = true;
        baseX = x;
        baseY = y;
        conectados = new ArrayList<Message>();
    }

    @Override
    public String getExclusiveItem() {
        if (isDAG()) {
            return null;
        }
        if (line != null && line.isVisible()) {
            return "Compact";
        } else {
            return "Expandir";
        }
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            Color cor = INICIAL;
            if (line != null) {
                if (!((Task) line.getOrigem()).isDAG()) {
                    cor = COMPLEXTASK;
                } else if (((Task) line.getDestino()).conectados.isEmpty() && ((Task) line.getOrigem()).conectados.isEmpty()) {
                    cor = UNICO;
                } else if (((Task) line.getDestino()).conectados.isEmpty()) {
                    cor = FINAL;
                } else if(!((Task) line.getOrigem()).conectados.isEmpty()) {
                    cor = INTERMEDIARIO;
                }
            }
            /*if (line != null && !line.isVisible()) {
             g.setColor(Color.WHITE);
             g.drawString("█", getX() + 15, getY() - 15);
             g.setColor(Color.BLACK);
             g.drawString("+", getX() + 15, getY() - 15);
             g.fillOval(getX() - 19, getY() - 10, 37, 34);
             }*/
            g.setColor(cor);
            g.fillOval(getX() - 19, getY() - 17, 37, 34);
            g.setColor(Color.BLACK);
            g.drawOval(getX() - 19, getY() - 17, 37, 34);
            if (isSelected()) {
                g.drawString("Rank", getX() - 16, getY() + 10);
                String Rank = rank.toString();
                int x = getX() - (10 * Rank.length() / 2);
                g.drawString(Rank, x + 2, getY() - 2);
            } else {
                if (((Task) line.getOrigem()).isDAG()) {
                    if (cor.equals(UNICO)) {
                        g.drawString("BoT", getX() - 12, getY() + 5);
                    } else {
                        g.drawString("DAG", getX() - 12, getY() + 5);
                    }
                } else {
                    if (!line.isVisible()) {
                        g.drawString("Task", getX() - 15, getY() + 5);
                        g.drawString("+", getX() - 5, getY() + 15);
                    } else if(line.getOrigem().equals(this)) {
                        g.drawString("Begin", getX() - 16, getY() + 5);
                    } else {
                        g.drawString("End", getX() - 12, getY() + 5);
                    }
                }
                //g.drawString("size", getX() - 10, getY() - 5);
                //String sizeTemp = this.line.getFirst().getSizeMax().toString();
                //int x = getX() - 10 * (sizeTemp.length() / 2);
                //g.drawString(sizeTemp, x, getY() + 5);
            }
        }
    }

    @Override
    public boolean contains(int x, int y) {
        if (x < getX() + 17 && x > getX() - 17) {
            if (y < getY() + 17 && y > getY() - 17) {
                return true;
            }
        }
        return false;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public void exclusiveItemActionPerformed(ActionEvent evt) {
        if (line != null) {
            int difX = baseX - getX();
            int difY = baseY - getY();
            line.setVisible(!line.isVisible(), difX, difY);
            //se for o final do bloco de tarefas seta visible como false;
            Task end = (Task) line.getDestino();
            end.visible = line.isVisible();
            if (end.visible) {
                end.setPosition(end.getX() - difX, end.getY() - difY);
            }
            if (!line.isVisible()) {
                baseX = line.getOrigem().getX();
                baseY = line.getOrigem().getY();
            }
        }
    }

    @Override
    public Integer getX() {
        if (visible) {
            return super.getX();
        } else {
            return line.getOrigem().getX();
        }
    }

    @Override
    public Integer getY() {
        if (visible) {
            return super.getY();
        } else {
            return line.getOrigem().getY();
        }
    }

    @Override
    public void setPosition(Integer x, Integer y) {
        if (visible) {
            super.setPosition(x, y);
        }
    }

    @Override
    public String toString() {
        return "rank_" + rank;
    }

    boolean isVisible() {
        return visible;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public boolean isConectadoIndiretamente(Vertice destino) {
        Task begin = (Task) line.getOrigem();
        //verifica se destino e origem são iguais
        if (destino instanceof Task && ((Task) destino).line.getOrigem().equals(begin)) {
            return true;
        }
        for (Message link : getSaidas()) {
            if (link.getDestino() instanceof Task) {
                Task tar = (Task) link.getDestino();
                boolean temp = tar.isConectadoIndiretamente(destino);
                if (temp) {
                    return true;
                }
            } else if (link.getDestino() instanceof DataFile) {
                DataFile dat = (DataFile) link.getDestino();
                boolean temp = dat.isConectadoIndiretamente(destino);
                if (temp) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Message> getEntradas() {
        Task begin = (Task) line.getOrigem();
        return begin.conectados;
    }

    @Override
    public ArrayList<Message> getSaidas() {
        Task end = (Task) line.getDestino();
        return end.conectados;
    }

    @Override
    public boolean isConectado(Vertice destino) {
        for (Message message : getSaidas()) {
            if (message.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    public Double getComputingSize() {
        return computingSize;
    }

    public void setComputingSize(Double mimComputingSize) {
        this.computingSize = mimComputingSize;
    }

    public Double getCommunicationSize() {
        return communicationSize;
    }

    public void setCommunicationSize(Double mimCommunicationSize) {
        this.communicationSize = mimCommunicationSize;
    }

    public boolean isDAG() {
        return dag;
    }

    public void setDag(Boolean dag) {
        this.dag = dag;
    }

    @Override
    public String getIdentificador() {
        return rank.toString();
    }
}