package org.liu3.io.markdown;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.liu3.io.pdf.ItextpdfUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Node是一个二维链表
 * @Author: liutianshuo
 * @Date: 2025/7/4
 */
public class CommonMarkUtil {

    public Map<String, HeadingText> textMap;


    public static void main(String[] args) {
        CommonMarkUtil rs = new CommonMarkUtil();
        rs.init(getMarkdownStr());
    }

    public void init(String content){

        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        Node node = document.getFirstChild();

        //遍历全出node节点
        Tree top = new Tree();
        add(node, top);
        System.out.println(JSON.toJSONString(top.getChild()));

        textMap = new LinkedHashMap<>();
        while (node != null) {
            if(node instanceof Heading){
                Heading heading = (Heading) node;
                HeadingText headingText = getHeadingData(heading);
                headingText.setCurrent(null);
                textMap.put(headingText.getTitle(), headingText);
            }
            node = node.getNext();
        }
        for(Map.Entry<String, HeadingText> en : textMap.entrySet()){
            System.out.println("-----------------");
            for(HeadingText.TextNode sb : en.getValue().getList()){
                System.out.println(sb);
            }
        }
    }

    /**
     * 用Tree获取Node名下的所有节点信息
     * commonmark的Node其实是一个二维链表
     * @param child 上级node的子node
     * @param top 上一级的node封装的tree
     *  String txt;
     *  String clsName;
     *  List<Tree> child;
     */
    public void add(Node child, Tree top){
        while(child != null){
            Tree subTree = new Tree(child);
            top.add(subTree);

            add(child.getFirstChild(), subTree);
            child = child.getNext();
        }
    }

    private HeadingText getHeadingData(Node heading){

        HeadingText headingText = new HeadingText();
        String title = getText(heading);
        headingText.setTitle(title);

        Node node = heading.getNext();

        if(title.equals("综合提示建议")){
            tipsNode(node, headingText);
        }else{
            commonNode(node, headingText);
        }

        for(HeadingText.TextNode sb : headingText.getList()){
//System.out.println(sb);
        }
        return headingText;
    }

    public void commonNode(Node node, HeadingText headingText){
        while(node != null){
            if(node instanceof Heading ){
                break;
            }
            getText(node, headingText);
            node = node.getNext();
        }
    }

    public void tipsNode(Node node, HeadingText headingText){

        while(node != null){
            if(node.getClass() == Heading.class ){
                break;
            }else if(node.getClass() == Paragraph.class){
                Node end = node.getFirstChild();
                if(end != null && end.getClass() == Text.class){
                    break;
                }
            }

            //具体问题的标题加粗
            Node child = node.getFirstChild();
            getText(child, headingText);
            try {
                headingText.current.setFont(ItextpdfUtil.FONT_BOLD);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            //问题标题下面的内容
            child = child.getNext();

            while(child != null){
                getText(child, headingText);
                child = child.getNext();
            }

            //针对性建议下面的有序列表
            node = node.getNext();
            headingText.newline();
        }
    }

    //通用的获取node名下所有文本
    private static String getText(Node node) {
        StringBuilder sb = new StringBuilder();
        node.accept(new AbstractVisitor() {
            @Override
            public void visit(Text text) {
                sb.append(text.getLiteral());
            }
        });
        return sb.toString().trim();
    }

    //
    private static void getText(Node node, HeadingText headingText) {
        node.accept(new AbstractVisitor() {
            @Override
            public void visit(Text text) {
                headingText.append(text.getLiteral());
            }
            @Override
            public void visit(Paragraph paragraph) {
                headingText.newline();
                super.visit(paragraph);
            }

            @Override
            public void visit(SoftLineBreak softLineBreak) {
                headingText.newline();
                super.visit(softLineBreak);
            }
        });
    }

    public static String getMarkdownStr(){
        return "# 养老社区长者监测与报警数据分析报告\n" +
                "\n" +
                "## 1.报警事件概览\n" +
                "\n" +
                "**总报警事件数量**：共 466 条报警记录。\n" +
                "\n" +
                "**主要报警类型分布**：\n" +
                "- 拉绳报警：占比约 34.55%（161 条），集中在日常活动时段。\n" +
                "- 用户上床：占比约 31.97%（149 条），集中在夜间休息时段。\n" +
                "- 用户离床：占比约 31.97%（149 条），集中在早晨起床时段。\n" +
                "- 翻身护理提醒：体动过多：占比约 0.86%（4 条），集中在夜间时段。\n" +
                "- 有人跌倒：占比约 0.43%（2 条），集中在活动时段。\n" +
                "- 呼吸过速：占比约 0.21%（1 条），集中在特定时段。\n" +
                "\n" +
                "## 2.单日报警数量高峰分析\n" +
                "\n" +
                "**2025-06-12**：单日报警峰值\n" +
                "总报警数：61 条，占全部记录的 13.09%。\n" +
                "高频报警类型：\n" +
                "- 用户上床：30 条（49.18%），集中在夜间时段。\n" +
                "- 用户离床：30 条（49.18%），集中在早晨时段。\n" +
                "可能原因：可能是系统对上下床动作的敏感度设置较高，或长者作息规律性较强。\n" +
                "\n" +
                "**2025-06-08**：次高峰\n" +
                "总报警数：50 条。\n" +
                "高频时段：全天，拉绳报警事件密集（29条）。\n" +
                "\n" +
                "## 3.时间分布特征\n" +
                "\n" +
                "**用户上床/离床事件**：\n" +
                "高峰时段：夜间上床（约21:00-22:00），早晨离床（约6:00-7:00）。\n" +
                "单次最长连续触发：2025-06-12 夜间时段，共触发30次用户上床事件。\n" +
                "\n" +
                "**拉绳报警事件**：\n" +
                "集中时段：全天分布，可能与长者日常活动需求相关。\n" +
                "\n" +
                "**翻身护理提醒事件**：\n" +
                "高发时段：夜间，需关注长者睡眠质量。\n" +
                "\n" +
                "## 4.关键发现\n" +
                "\n" +
                "**问题1**：\n" +
                "拉绳报警占比最高（34.55%），表明长者对紧急呼叫服务的需求较高。\n" +
                "\n" +
                "**问题2**：\n" +
                "用户上床/离床报警占比高（各31.97%），可能反映系统对床状态监测过于敏感。\n" +
                "\n" +
                "**问题3**：\n" +
                "跌倒报警和呼吸异常报警数量较少但风险较高，需要特别关注。\n" +
                "\n" +
                "## 5.建议\n" +
                "\n" +
                "**建议1**：\n" +
                "优化拉绳报警系统，分析高频使用原因，考虑是否需要增加辅助服务。\n" +
                "\n" +
                "**建议2**：\n" +
                "调整床状态监测灵敏度设置，减少不必要的报警。\n" +
                "\n" +
                "**建议3**：\n" +
                "加强对高风险报警类型（如跌倒、呼吸异常）的响应机制和后续跟踪。\n" +
                "\n" +
                "## 单日报警数据Top3统计表\n" +
                "\n" +
                "| 日期       | 总报警数 | 用户上床 | 用户离床 | 拉绳报警 | 翻身护理提醒 |\n" +
                "|------------|----------|----------|----------|----------|--------------|\n" +
                "| 2025-06-12 | 61       | 30       | 30       | 0        | 1            |\n" +
                "| 2025-06-08 | 50       | 11       | 10       | 29       | 0            |\n" +
                "| 2025-06-14 | 49       | 24       | 24       | 0        | 1            |\n" +
                "\n" +
                "## 综合提示建议\n" +
                "\n" +
                "**拉绳报警高频问题**\n" +
                "综合数据分析：\n" +
                "关联指标：日常活动需求、紧急求助频率。\n" +
                "潜在原因：可能反映长者对辅助服务的实际需求，或存在误触情况。\n" +
                "针对性建议：\n" +
                "1. 调查拉绳报警的具体原因，区分真实需求与误报\n" +
                "2. 考虑增加辅助服务人员或优化服务响应流程\n" +
                "3. 对长者进行拉绳使用指导，减少误触\n" +
                "\n" +
                "**床状态监测敏感问题**\n" +
                "综合数据分析：\n" +
                "关联指标：作息规律、系统灵敏度设置。\n" +
                "潜在原因：系统可能对轻微动作过于敏感，或长者确实有频繁上下床情况。\n" +
                "针对性建议：\n" +
                "1. 评估并调整床状态监测的灵敏度参数\n" +
                "2. 对频繁上下床的长者进行健康评估\n" +
                "3. 考虑增加二次确认机制减少误报\n" +
                "\n" +
                "**高风险低频率报警问题**\n" +
                "综合数据分析：\n" +
                "关联指标：跌倒风险、呼吸异常。\n" +
                "潜在原因：虽然数量少但风险高，可能存在漏报或未被及时发现的情况。\n" +
                "针对性建议：\n" +
                "1. 加强高风险报警的优先级设置和响应机制\n" +
                "2. 对相关长者进行专项健康评估\n" +
                "3. 增加高风险时段的监测频率和人工巡查\n" +
                "\n" +
                "感谢团队的高效响应与细致观察！您的快速行动是长者安全的第一道防线！.";
    }
    @Data
    class Tree {
        private String txt;
        private String clsName;
        private List<Tree> child;

        public Tree() {
        }
        public Tree(Node node) {
            if(node.getClass() == Text.class){
                txt = ((Text)node).getLiteral();
            }
            clsName = node.getClass().getSimpleName();
        }
        public void add(Tree sub){
            if(child == null){
                child = new ArrayList<>();
            }
            child.add(sub);
        }
    }

}
