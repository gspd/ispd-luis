/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.gui.auxiliar;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Implementa FileFilter que permite uma ou mais extensões para um mesmo tipo de arquivo.
 * @author denison_usuario
 */
public class FiltroDeArquivos extends FileFilter {

    private String descricao;
    private String[] extensao; //conjunto de extensões aceitas
    private boolean permitirDiretorio;

    public FiltroDeArquivos(String descricao, String[] extensao, boolean permitirDiretorio) {
        this.descricao = descricao;
        this.extensao = extensao;
        this.permitirDiretorio = permitirDiretorio;
    }

    public FiltroDeArquivos(String descricao, String extensao, boolean permitirDiretorio) {
        this(descricao, new String[]{extensao}, permitirDiretorio);
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory() && permitirDiretorio) {
            return true;
        }
        for (String ext : extensao) {
            if (file.getName().toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setExtensao(String[] extensao) {
        this.extensao = extensao;
    }

    public void setExtensao(String string) {
        setExtensao(new String[] {string});
    }
}
