package org.liu3.io.markdown;

import com.itextpdf.text.Font;
import lombok.Data;
import org.commonmark.node.Node;
import org.liu3.io.pdf.ItextpdfUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liutianshuo
 * @Date: 2025/7/4
 */
@Data
public class HeadingText {

    protected String title;
    protected Node heading;
    protected TextNode current;
    protected List<TextNode> list;

    public HeadingText(){
        current = new TextNode();
        list = new ArrayList<>();
        list.add(current);
    }

    public StringBuilder append(Object str){
        return current.text.append(str);
    }

    public void newline(){
        current = new TextNode();
        list.add(current);
    }
    public class TextNode {
        StringBuilder text;
        Font font = ItextpdfUtil.FONT_NORMAL;
        public TextNode() {
            this.text = new StringBuilder();
        }
        public StringBuilder getText() {
            return text;
        }
        public void setText(StringBuilder text) {
            this.text = text;
        }
        public Font getFont() {
            return font;
        }
        public void setFont(Font font) {
            this.font = font;
        }
        @Override
        public String toString() {
            return text + ", " + font.getStyle();
        }
    }
}
