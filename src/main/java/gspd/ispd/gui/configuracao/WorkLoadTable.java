/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.gui.configuracao;

import gspd.ispd.motor.carga.CargaDAG;
import gspd.ispd.motor.carga.CargaForNode;
import gspd.ispd.motor.carga.CargaList;
import gspd.ispd.motor.carga.GerarCarga;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class WorkLoadTable extends AbstractTableModel {

    private CargaList cargaList;
    private final String[] COLUNAS = {"Type", "Application", "User", "Scheduler", "Tasks", "Data"};
    private final int TYPE = 0;
    private final int APPLICATION = 1;
    private final int USER = 2;
    private final int SCHEDULER = 3;
    private final int TASKS = 4;
    private final int DATA = 5;

    public WorkLoadTable() {
        cargaList = new CargaList(new ArrayList<GerarCarga>(), GerarCarga.FORNODE);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == APPLICATION;
    }
    
    @Override
    public int getRowCount() {
        if (cargaList != null) {
            return cargaList.getList().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int column) {
        if (column >= 0 && column < COLUNAS.length) {
            return COLUNAS[column];
        } else {
            return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GerarCarga item = cargaList.getList().get(rowIndex);
        switch (item.getTipo()) {
            case GerarCarga.DAG:
                switch (columnIndex) {
                    case TYPE:
                        return "DAG";
                    case APPLICATION:
                        return ((CargaDAG) item).getAplicacao();
                    case USER:
                        return ((CargaDAG) item).getProprietario();
                    case SCHEDULER:
                        return ((CargaDAG) item).getEscalonador();
                    case TASKS:
                        return ((CargaDAG) item).getNumeroTarefas();
                    case DATA:
                        return ((CargaDAG) item).getArquivo();
                }
                break;
            case GerarCarga.FORNODE:
                switch (columnIndex) {
                    case TYPE:
                        return "BoT";
                    case APPLICATION:
                        return ((CargaForNode) item).getAplicacao();
                    case USER:
                        return ((CargaForNode) item).getProprietario();
                    case SCHEDULER:
                        return ((CargaForNode) item).getEscalonador();
                    case TASKS:
                        return ((CargaForNode) item).getNumeroTarefas();
                    case DATA:
                        CargaForNode carga = (CargaForNode) item;
                        String data = "COMP( Max:"+carga.getMaxComputacao()+"/Min:"+carga.getMinComputacao()+" ) "+
                                      "COMU( Max:"+carga.getMaxComunicacao()+"/Min:"+carga.getMinComunicacao()+" ) ";
                        return data;
                }
                break;
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex == APPLICATION) {
            GerarCarga item = cargaList.getList().get(rowIndex);
            if(item instanceof CargaForNode) {
                ((CargaForNode)item).setAplicacao(aValue.toString());
            } else if(item instanceof CargaDAG) {
                ((CargaDAG)item).setAplicacao(aValue.toString());
            }
        }
    }

    public CargaList getCargaList() {
        return cargaList;
    }

    public void setCargaList(CargaList cargalist) {
        this.cargaList = cargalist;
    }

}
