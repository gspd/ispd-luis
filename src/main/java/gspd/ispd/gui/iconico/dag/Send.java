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
 * Send.java
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
 * Bloco que representa elemento de envio de uma tarefa
 * @author denison
 */
public class Send extends Block {

    private static JTextField jsize;

    public static void edit(Component parent, Send icon, ArrayList<Task> tasks) {
        if (!tasks.isEmpty()) {
            if (jsize == null) {
                jsize = new JTextField();
                jsize.setBorder(javax.swing.BorderFactory.createTitledBorder("Size"));
            }
            jsize.setText(icon.size.toString());
            ArrayList<Vertice> lista = icon.elementCanSend();
            lista.addAll(tasks);
            Vertice task = (Vertice) JOptionPane.showInputDialog (
                    parent,
                    jsize,//"Connect to ...", 
                    "Select the destination",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    lista.toArray(),
                    icon.getDestino());
            //Caso ok o send é preenchido
            if (task != null) {
                Double tamanho = icon.size;
                try {
                    tamanho = Double.valueOf(jsize.getText().toString());
                } catch (Exception e) {
                }
                icon.size = tamanho;
                icon.setDestino(task);
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Error - there are no tasks!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private Vertice destino;
    private Double size;

    public Send(Integer x, Integer y) {
        super(x, y);
        size = 100.0;
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            int[] send_x = {getX() - 17, getX(), getX() + 17, getX(), getX() - 17};
            int[] send_y = {getY() - 17, getY() - 17, getY(), getY() + 17, getY() + 17};
            g.setColor(Color.GREEN);
            g.fillPolygon(send_x, send_y, 5);
            g.setColor(Color.BLACK);
            g.drawPolygon(send_x, send_y, 5);
            g.drawString("Send", getX() - 20, getY() - 20);
            if(isSelected()) {
                g.drawString(size.toString(), getX() - 15, getY() - 3);
            } else if(destino!= null) {
                g.drawString("To:", getX() - 15, getY() - 3);
                g.drawString(destino.toString(), getX() - 15, getY() + 13);
            }
        }
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double sizeMin) {
        this.size = sizeMin;
    }

    private ArrayList<Vertice> elementCanSend() {
        ArrayList<Vertice> lista = new ArrayList<Vertice>();
        Block temp = getPrevious();
        while(temp != null) {
            if(temp instanceof Loop || temp instanceof Receive) {
                lista.add(temp);
            }
            temp = temp.getPrevious();
        }
        return lista;
    }
}
