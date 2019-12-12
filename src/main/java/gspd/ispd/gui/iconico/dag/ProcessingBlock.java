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
 * ProcessingBlock.java
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Representa um bloco de processamento
 * @author denison
 */
public class ProcessingBlock extends Block {

    private static JPanel painel;
    private static JTextField jsizeMin;
    private static JTextField jsizeMax;

    public static void edit(Component parent, ProcessingBlock icon) {
        if (painel == null) {
            jsizeMax = new JTextField();
            jsizeMax.setBorder(javax.swing.BorderFactory.createTitledBorder("Max Size"));
            jsizeMin = new JTextField();
            jsizeMin.setBorder(javax.swing.BorderFactory.createTitledBorder("Mim Size"));
            painel = new JPanel();
            painel.setLayout(new GridLayout(2, 1));
            painel.add(jsizeMax);
            painel.add(jsizeMin);
        }
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
            Double tamanhoMin = icon.sizeMin;
            Double tamanhoMax = icon.sizeMax;
            try {
                tamanhoMin = Double.valueOf(jsizeMin.getText().toString());
                tamanhoMax = Double.valueOf(jsizeMax.getText().toString());
            } catch (Exception e) {
            }
            icon.sizeMin = tamanhoMin;
            icon.sizeMax = tamanhoMax;
        }
    }
    private Double sizeMin;
    private Double sizeMax;

    public ProcessingBlock(Integer x, Integer y) {
        super(x, y);
        sizeMin = 50.0;
        sizeMax = 50.0;
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.CYAN);
            g.fillRect(getX() - 17, getY() - 17, 34, 34);
            g.setColor(Color.BLACK);
            g.drawRect(getX() - 17, getY() - 17, 34, 34);
            g.drawString("Process", getX() - 25, getY() - 20);
            g.drawString(sizeMin.toString(), getX() - 15, getY() - 3);
            g.drawString(sizeMax.toString(), getX() - 15, getY() + 13);
        }
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
}
