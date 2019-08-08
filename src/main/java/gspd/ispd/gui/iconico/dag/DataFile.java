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
 * DataFile.java
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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Objeto que representa um arquivo no grafo da aplicação DAG
 * @author denison
 */
public class DataFile extends Vertice implements Identificavel {
    
    private static JPanel painel;
    private static JTextField jId;
    private static JTextField jsizeMin;
    private static JTextField jsizeMax;

    public static void edit(Component parent, DataFile icon) {
        if (painel == null) {
            jId = new JTextField();
            jId.setBorder(javax.swing.BorderFactory.createTitledBorder("Id"));
            jsizeMin = new JTextField();
            jsizeMin.setBorder(javax.swing.BorderFactory.createTitledBorder("Mim Size"));
            jsizeMax = new JTextField();
            jsizeMax.setBorder(javax.swing.BorderFactory.createTitledBorder("Max Size"));
            painel = new JPanel();
            painel.setLayout(new GridLayout(3, 1));
            painel.add(jId);
            painel.add(jsizeMin);
            painel.add(jsizeMax);
        }
        jId.setText(icon.id);
        jsizeMin.setText(icon.sizeMin.toString());
        jsizeMax.setText(icon.sizeMax.toString());
        int opcao = JOptionPane.showConfirmDialog(
                parent,
                painel,
                "Set the size",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        //Caso okgetSize() o bloco é preenchido
        if (opcao == JOptionPane.OK_OPTION) {
            String id = icon.id;
            Double tamanhoMin = icon.sizeMin;
            Double tamanhoMax = icon.sizeMax;
            try {
                id = jId.getText();
                tamanhoMin = Double.valueOf(jsizeMin.getText().toString());
                tamanhoMax = Double.valueOf(jsizeMax.getText().toString());
            } catch (Exception e) {
            }
            icon.id = id;
            icon.sizeMin = tamanhoMin;
            icon.sizeMax = tamanhoMax;
        }
    }

    private Double sizeMin;
    private Double sizeMax;
    private String id;
    private ArrayList<Message> entradas;
    private ArrayList<Message> saidas;

    public DataFile(Integer x, Integer y, Integer id) {
        super(x, y);
        this.id = "File" + id;
        sizeMin = 50.0;
        sizeMax = 50.0;
        entradas = new ArrayList<Message>();
        saidas = new ArrayList<Message>();
    }

    public DataFile(Integer x, Integer y, String id) {
        super(x, y);
        this.id = id;
        sizeMin = 50.0;
        sizeMax = 50.0;
        entradas = new ArrayList<Message>();
        saidas = new ArrayList<Message>();
    }

    @Override
    public void draw(Graphics g) {
        //circulo inferior
        g.setColor(Color.ORANGE);
        g.fillOval(getX() - 19, getY() + 7, 37, 10);
        g.setColor(Color.BLACK);
        g.drawOval(getX() - 19, getY() + 7, 37, 10);
        //Lateral
        g.setColor(Color.ORANGE);
        g.fillRect(getX() - 19, getY() - 12, 37, 24);
        //circulo superior
        g.fillOval(getX() - 19, getY() - 17, 37, 10);
        g.setColor(Color.BLACK);
        g.drawOval(getX() - 19, getY() - 17, 37, 10);
        //Linhas laterais
        g.drawLine(getX() - 19, getY() - 12, getX() - 19, getY() + 12);
        g.drawLine(getX() + 18, getY() - 12, getX() + 18, getY() + 12);
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

    @Override
    public boolean isConectadoIndiretamente(Vertice destino) {
        if(destino.equals(this)) {
            return true;
        }
        for (Message link : saidas) {
            if (link.getDestino() instanceof Task) {
                Task tar = (Task) link.getDestino();
                boolean temp = tar.isConectadoIndiretamente(destino);
                if(temp) {
                    return true;
                }
            } else if(link.getDestino() instanceof DataFile) {
                DataFile dat = (DataFile) link.getDestino();
                System.out.println(dat+" -> "+destino);
                boolean temp = dat.isConectadoIndiretamente(destino);
                if(temp) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Message> getEntradas() {
        return entradas;
    }

    @Override
    public ArrayList<Message> getSaidas() {
        return saidas;
    }

    @Override
    public boolean isConectado(Vertice destino) {
        for (Message message : saidas) {
            if(message.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    public Double getSizeMin() {
        return sizeMin;
    }

    public void setSizeMin(Double sizeMin) {
        this.sizeMin = sizeMin;
    }

    public Double getSizeMax() {
        return sizeMax;
    }

    public void setSizeMax(Double sizeMax) {
        this.sizeMax = sizeMax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}
