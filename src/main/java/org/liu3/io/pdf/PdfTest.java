package org.liu3.io.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @Author: liutianshuo
 * @Date: 2025/7/1
 */
public class PdfTest {
    public static void main(String[] args) throws Exception {

        String html = "<html><body>123abc白日依山尽</body></html>";

//        try(OutputStream os = new FileOutputStream(new File("c.pdf"))){
//            HtmlConverter.convertToPdf(html, os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String html = "<html><body><p>Hello, World!</p></body></html>";
//        FileOutputStream outputStream = new FileOutputStream("output.pdf");
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
//        outputStream.close();

        OutputStream os = new FileOutputStream("文件路径");
        Document document = new Document(PageSize.A4);

        PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
        //在页面开始或结束的时候做一些动作,比如增加页眉页脚,需要继承PdfPageEventHelper
        pdfWriter.setPageEvent(new PdfPageEventHelper(){
            @Override
            public void onStartPage(PdfWriter writer, Document document) {
                try {
                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("image/ai_health_report_head.jpg");
                    byte[] bs = IOUtils.toByteArray(is);
                    Image img = Image.getInstance(bs);
                    document.add(img);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        document.open();

        //增加内容的操作

        document.close();

        //itextpdf默认字体不支持中文,会打印不出来,需要指定字体,比如宋体
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //12号大小,粗体
        Font font = new Font(baseFont, 12, Font.BOLD);

        //创建一个段落,有多种构造方法
        Paragraph paragraph = new Paragraph("段落内容", font);
        //段落内文字居中
        paragraph.setAlignment(Element.ALIGN_CENTER);
        //上边距?
        paragraph.setPaddingTop(1F);
        //设置首行缩进
        paragraph.setFirstLineIndent(24);
        //左缩进
        paragraph.setIndentationLeft(12);
        //右缩进
        paragraph.setIndentationRight(12);
        //行间距
        paragraph.setLeading(20f);
        //段落上空白
        paragraph.setSpacingBefore(1F);
        //段落下空白
        paragraph.setSpacingAfter(1F);

        //比较基础的组件,比Paragraph要低,被addElement后好像不会换行
        Chunk chunk = new Chunk("组块内容");
        /**
         Chunk.NEWLINE = new Chunk("\n")
         Chunk.NEWPAGE 不知道有什么用
         */

        //表格
        float[] widths = {20f, 35f, 20f, 25f};
        //指定表格列数,并指定各列的相对宽度
        PdfPTable table = new PdfPTable(widths);
        //宽度100%
        table.setWidthPercentage(100);
        //锁定宽度
        table.setLockedWidth(true);

        //解决表格内容太长,跨页时被截断
        //如果为true，则只有当该行是空页中的第一行时才会拆分。默认情况下是真的。只有setSplitRows（true）才有意义。
        table.setSplitLate(false);
        //设置后，页面中不适合的行将被拆分。请注意，处理拆分表行所需的内存至少是处理普通表的两倍。默认情况下为true。
        table.setSplitRows(true);


        //单元格
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase("content", font));
        //边距
        cell.setPadding(5);
        //指定边框宽度
        cell.setBorderWidthTop(1);
        cell.setBorderWidthBottom(1);
        //背景颜色
        cell.setBackgroundColor(new BaseColor(242, 242, 242));
        //边框颜色
        cell.setBorderColor(BaseColor.GRAY);

        //追加单元格内容
        cell.addElement(paragraph);

        //追加单元格,会根据列数自动换行
        table.addCell(cell);

        byte[] bs = null;
        Image image = Image.getInstance(bs);

        image.getPlainWidth();//获取图像的纯宽度
        image.getScaledWidth();//获取图像的缩放宽度。
        image.getWidth();//返回矩形的宽度。
        image.getWidthPercentage();//获取属性宽度百分比。


        Rectangle page = pdfWriter.getPageSize();
        //页面宽度
        float pageWidth = page.getWidth();
        float imageWidth = image.getWidth();
        float rate = pageWidth / imageWidth;
        //调整图片宽度至页面宽度
        image.setWidthPercentage(rate*100);

        document.add(new Paragraph("追加段落"));
        document.add(table);
        document.add(image);
    }
}
