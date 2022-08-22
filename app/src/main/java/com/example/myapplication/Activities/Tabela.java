package com.example.myapplication.Activities;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.DateFormat;

public class Tabela {

    private Workbook wb;
    private Data data;
    private CellStyle cellStyleVermelho;
    private HSSFCellStyle cellStyleResto;
    private Font font;


    Sheet sheet;
    Cell cell;
    Row row;

    //Cores
    HSSFCellStyle cellStyleVerde;

    //Estilos
    HSSFCellStyle cellStyleAlinhamento;

    public Tabela() {
        data = new Data();

    }

    public Workbook criarTabela(DateFormat dataMes, DateFormat dataCompleta) {


        //Criação da tabela
        wb = new HSSFWorkbook();
        sheet = wb.createSheet(data.getDataMesEmString());
        sheet.setDefaultColumnWidth(12);

        //Criando os estilos das celulas na cor verde (usado nos valores)
        HSSFPalette paletteVerde = ((HSSFWorkbook) wb).getCustomPalette();
        paletteVerde.setColorAtIndex(HSSFColor.LIME.index, (byte) 146, (byte) 208, (byte) 80);

        cellStyleVerde = (HSSFCellStyle) wb.createCellStyle();
        cellStyleVerde.setFillForegroundColor(HSSFColor.LIME.index);
        cellStyleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleVerde.setBorderBottom(BorderStyle.THIN);
        cellStyleVerde.setBorderTop(BorderStyle.THIN);
        cellStyleVerde.setBorderLeft(BorderStyle.THIN);
        cellStyleVerde.setBorderRight(BorderStyle.THIN);


        //Criando os estilos das celulas na cor vermelha (usado nos valores)
        HSSFPalette paletteVermelho = ((HSSFWorkbook) wb).getCustomPalette();
        paletteVermelho.setColorAtIndex(HSSFColor.RED.index, (byte) 255, (byte) 80, (byte) 80);

        getCellStyleVermelha();

        //Crinado estilo de alinhamento (usado somente no mês)
        cellStyleAlinhamento = (HSSFCellStyle) wb.createCellStyle();
        cellStyleAlinhamento.setAlignment(HorizontalAlignment.CENTER);

        //Criando estilo para o resto das celula
        getCellStyleResto();


        //Fonte das celulas de valores
        getFont();

        //linha dos Valores (Não pode tirar esse aq pq se não a tabela n consegue saber onde criar kkkk)
        Row rowValores = sheet.createRow(0);
        Row rowDataAtual = sheet.createRow(1);

        //celula da mês do ano
        Cell cellMes = rowValores.createCell(0);
        cellStyleVerde.setFont(font);
        cellMes.setCellStyle(cellStyleAlinhamento);
        cellMes.setCellStyle(cellStyleResto);
        cellMes.setCellValue(data.getDataMesEmString());

        //celula do dia atual
        Cell celldata = rowDataAtual.createCell(0);
        celldata.setCellValue(data.getDataCompletaEmString());
        cellMes.setCellStyle(cellStyleResto);
        cellStyleAlinhamento.setFont(font);


        cell = rowValores.createCell(1);
        cell.setCellValue("Dinheiro");
        cell.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        cell = rowValores.createCell(2);
        cell.setCellValue("Cartão");
        cell.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        cell = rowValores.createCell(3);
        cell.setCellValue("Pix");
        cell.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        cell = rowValores.createCell(4);
        cell.setCellValue("Aluguel");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        cell = rowValores.createCell(5);
        cell.setCellValue("Compras");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        cell = rowValores.createCell(6);
        cell.setCellValue("Energia");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        cell = rowValores.createCell(7);
        cell.setCellValue("Água");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        cell = rowValores.createCell(8);
        cell.setCellValue("Internet");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        cell = rowValores.createCell(9);
        cell.setCellValue("Trasporte");
        cell.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        return wb;
    }
    public Font getFont (){
        font = wb.createFont();
        font.setBold(true);
        return font;
    }

    public void getCellStyleResto(){
        cellStyleResto = (HSSFCellStyle) wb.createCellStyle();
        cellStyleResto.setBorderBottom(BorderStyle.THIN);
        cellStyleResto.setBorderTop(BorderStyle.THIN);
        cellStyleResto.setBorderLeft(BorderStyle.THIN);
        cellStyleResto.setBorderRight(BorderStyle.THIN);
    }

    public CellStyle getCellStyleVermelha(){

        cellStyleVermelho = wb.createCellStyle();
        cellStyleVermelho.setFillForegroundColor(HSSFColor.RED.index);
        cellStyleVermelho.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleVermelho.setBorderBottom(BorderStyle.THIN);
        cellStyleVermelho.setBorderTop(BorderStyle.THIN);
        cellStyleVermelho.setBorderLeft(BorderStyle.THIN);
        cellStyleVermelho.setBorderRight(BorderStyle.THIN);

        return cellStyleVermelho;
    }


    public void adcionaValor(double valor, int posicao, String categoria, Workbook wb) {


        Sheet sheet = wb.getSheetAt(0);
        int ultimaLinha = sheet.getLastRowNum();
        Row row = sheet.getRow(ultimaLinha);

        row.createCell(posicao).setCellValue(valor);
        row.getCell(posicao).setCellStyle(cellStyleResto);

        String dataCell = row.getCell(0).getStringCellValue();

        //Verifica se a data de hoje é a mesma da ultima linha criada, caso seja adicionar o valor do edit text na mesma linha

        if (!data.getDataCompletaEmString().equals(dataCell)) {
            Row rowData = sheet.createRow(++ultimaLinha);
            rowData.createCell(0).setCellValue(data.getDataCompletaEmString());
        }
        //Cria uma coluna nova com a categoria caso não exista
        Row rowColuna = sheet.getRow(0);
        rowColuna.createCell(posicao + 4).setCellValue(categoria);
        rowColuna.getCell(posicao + 4).setCellStyle(getCellStyleVermelha());
        getCellStyleVermelha().setFont(getFont());

        //Adiciona o valor embaixo da coluna
        Row rowValor = sheet.getRow(ultimaLinha);
        Cell cellValue = rowValor.getCell(posicao + 4, Row.RETURN_BLANK_AS_NULL);
        if (cellValue != null) {
            double valorCelula = cellValue.getNumericCellValue();
            row.createCell(posicao + 4).setCellValue(valor + valorCelula);
        } else {
            row.createCell(posicao + 4).setCellValue(valor);
        }
        row.getCell(posicao + 4).setCellStyle(getCellStyleVermelha());
    }


    public Workbook getWb() {
        return wb;
    }


}

