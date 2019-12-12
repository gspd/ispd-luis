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
 * Receive.java
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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Bloco que representa elemento de recebimento de mensagem
 * @author denison
 */
public class Receive extends Block implements Identificavel {

    private static JTextField jid;

    public static void edit(Component parent, Receive icon) {
        if (jid == null) {
            jid = new JTextField();
            jid.setBorder(javax.swing.BorderFactory.createTitledBorder("Identifier"));
        }
        jid.setText(icon.id);
        int opcao = JOptionPane.showConfirmDialog(
                parent,
                jid,
                "Set the port",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        //Caso ok o receive é preenchido
        if (opcao == JOptionPane.OK_OPTION) {
            icon.setId(jid.getText());
        }
    }
    
    private String id;

    public Receive(int x, int y) {
        super(x, y);
        id = "receive_i";
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            int[] receive_x = {getX() + 17, getX() - 17, getX(), getX() - 17, getX() + 17};
            int[] receive_y = {getY() + 17, getY() + 17, getY(), getY() - 17, getY() - 17};
            g.setColor(Color.RED);
            g.fillPolygon(receive_x, receive_y, 5);
            g.setColor(Color.BLACK);
            g.drawPolygon(receive_x, receive_y, 5);
            g.drawString("Receive", getX() - 25, getY() - 20);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
    
    @Override
    public String getIdentificador() {
        return id;
    }

    @Override
    public ArrayList<Message> getEntradas() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Message> getSaidas() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConectadoIndiretamente(Vertice Origem) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean isConectado(Vertice Destino) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
