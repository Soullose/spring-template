package com.w2.springtemplate.framework.io.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class PdfBoxUtils {

    /**
     * 插入一张图片
     * @param document
     * @param contentStream 输出流
     * @param imgFile 图片文件
     * @param xStart x主标
     * @param yStart y主标
     * @param width 图片宽
     * @param hight 图片高
     * @throws IOException
     */
    public static void drawImage(PDDocument document, PDPageContentStream contentStream, File imgFile, float xStart, float yStart, float width, float hight) throws IOException {
        PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imgFile, document);
        contentStream.drawImage(pdImage, xStart, yStart, width, hight );
    }


    /**
     * 画一条线
     * @param contentStream
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @throws IOException
     */
    public static void drawLine(PDPageContentStream contentStream, float xStart, float yStart, float xEnd, float yEnd) throws IOException {
        contentStream.moveTo(xStart, yStart);
        contentStream.lineTo(xEnd, yEnd);
        contentStream.stroke();
    }

    /**
     * 定义文本输出流开始
     * @param contentStream
     * @param leading 行距
     * @param offSetX 第一行的x坐标间距
     * @param offSetY 第一行的y主表间距
     * @throws IOException
     */
    public static void beginTextSteam(PDPageContentStream contentStream, Float leading, Float offSetX, Float offSetY) throws IOException {
        // 输出流开始
        contentStream.beginText();
        // 行间距
        contentStream.setLeading(leading);
        // 书写行定位
        contentStream.newLineAtOffset(offSetX, offSetY);
    }

    /**
     * 定义文本输出流结束
     * @param contentStream
     * @throws IOException
     */
    public static void endTextSteam(PDPageContentStream contentStream) throws IOException {
        contentStream.endText();
    }

    /**
     * 创建一定数量的空行
     * @param contentStream
     * @param emptyNum
     * @throws IOException
     */
    public static void createEmptyParagraph(PDPageContentStream contentStream, Integer emptyNum) throws IOException {
        for (int i = 0; i < emptyNum; i++) {
            contentStream.newLine();
        }
    }


    /**
     * 写一个段落
     * @param contentStream
     * @param text
     * @throws IOException
     */
    public static void drawParagraph(PDPageContentStream contentStream, String text) throws IOException {
        contentStream.showText(text);
        contentStream.newLine();
    }

    /**
     * 写一个段落并设定字体
     * @param contentStream
     * @param text
     * @throws IOException
     */
    public static void drawParagraph(PDPageContentStream contentStream, String text, PDFont font, Integer fontSize) throws IOException {
        contentStream.setFont(font, fontSize);
        contentStream.showText(text);
        contentStream.newLine();
    }
}
