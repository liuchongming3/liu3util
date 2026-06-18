package org.liu3ext.ds;

public class DeepSeeker {

    public static void main(String[] args) {

        // 从环境变量读取 API Key（推荐）
        String apiKey = "sk-";
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("请设置环境变量 DEEPSEEK_API_KEY");
            return;
        }

        DeepSeekService service = new DeepSeekService(apiKey);

        try {
            // 简单对话
            String reply = service.ask("用 Java 写一个快速排序算法");
            System.out.println("AI 回复：\n" + reply);

            // 带系统提示词
//            String techReply = service.askWithSystem(
//                    "你是资深 Java 技术专家，请简洁回答",
//                    "解释 Java 的垃圾回收机制"
//            );
//            System.out.println("\n技术回答：\n" + techReply);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
