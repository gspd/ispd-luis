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
 * Message.java
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

import gspd.ispd.gui.iconico.Aresta;
import gspd.ispd.gui.iconico.Vertice;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Aresta de conexão do grafo aciclico direcionado
 * @author denison
 */
public class Message extends Aresta {

    private static JTextField jsize;

    public static void edit(Component parent, Message icon) {
        if (jsize == null) {
            jsize = new JTextField();
            jsize.setBorder(javax.swing.BorderFactory.createTitledBorder("Size"));
        }
        jsize.setText(icon.size.toString());
        int opcao = JOptionPane.showConfirmDialog(
                parent,
                jsize,
                "Set the size",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        //Caso okgetSize() o bloco é preenchido
        if (opcao == JOptionPane.OK_OPTION) {
            Double tamanhoMax = icon.size;
            try {
                tamanhoMax = Double.valueOf(jsize.getText().toString());
            } catch (Exception e) {
            }
            icon.size = tamanhoMax;
        }
    }
    
    private static BasicStroke stk = new BasicStroke(5);
    private Polygon areaSeta;
    private Double size;

    public Message(Vertice origem, Vertice destino) {
        super(origem, destino);
        this.areaSeta = new Polygon();
        size = 50.0;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        double arrowWidth = 21.0f;
        double theta = 0.623f;
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        double[] vecLine = new double[2];
        double[] vecLeft = new double[2];
        double fLength;
        double th;
        double ta;
        double baseX, baseY;

        xPoints[0] = (int) getX();
        yPoints[0] = (int) getY();

        // build the line vector
        vecLine[0] = (double) xPoints[0] - getOrigem().getX();
        vecLine[1] = (double) yPoints[0] - getOrigem().getY();

        // build the arrow base vector - normal to the line
        vecLeft[0] = -vecLine[1];
        vecLeft[1] = vecLine[0];

        // setup length parameters
        fLength = (double) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
        th = arrowWidth / (2.0f * fLength);
        ta = arrowWidth / (2.0f * ((double) Math.tan(theta) / 2.0f) * fLength);

        // find the base of the arrow
        baseX = ((double) xPoints[0] - ta * vecLine[0]);
        baseY = ((double) yPoints[0] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[1] = (int) (baseX + th * vecLeft[0]);
        yPoints[1] = (int) (baseY + th * vecLeft[1]);
        xPoints[2] = (int) (baseX - th * vecLeft[0]);
        yPoints[2] = (int) (baseY - th * vecLeft[1]);

        areaSeta.reset();
        areaSeta.addPoint(xPoints[0], yPoints[0]);
        areaSeta.addPoint(xPoints[1], yPoints[1]);
        areaSeta.addPoint(xPoints[2], yPoints[2]);

        g.fillPolygon(areaSeta);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(stk);
        g2d.drawLine(getOrigem().getX(), getOrigem().getY(), getDestino().getX(), getDestino().getY());
    }

    @Override
    public Integer getX() {
        return (((((getOrigem().getX() + getDestino().getX()) / 2) + getDestino().getX()) / 2) + getDestino().getX()) / 2;
    }

    @Override
    public Integer getY() {
        return (((((getOrigem().getY() + getDestino().getY()) / 2) + getDestino().getY()) / 2) + getDestino().getY()) / 2;
    }

    @Override
    public boolean contains(int x, int y) {
        return areaSeta.contains(x, y);
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double value) {
        size = value;
    }
}
