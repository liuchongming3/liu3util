package org.liu3.algorithm.graph;

import java.util.*;

/**
 * 不用递归遍历对象
 * @Author: liutianshuo
 * @Date: 2022/5/19
 */
public class NoRecursion {

    /**
     * 一：二叉树简介
     *
     * 1.1：二叉树结构
     * 维基百科中对二叉树是这样定义的：(英文名：Binary tree）是每个节点最多只有两个分支（即不存在分支度大于2的节点）的树结构。
     * 通常分支被称作“左子树”或“右子树”,二元树的分支具有左右次序，不能随意颠倒.如下图,是一颗标准的二叉树(其中标明了节点的属性,比如左一、右二等,下面用到的示例均采用该树)
     *
     * 1.2:二叉树的遍历
     *
     * 二叉树的遍历是指按照一定的规则访问二叉树的每一个结点并且查看它的值。
     * 有很多常见的顺序来访问所有的结点，而且每一种都有有用的性质。
     * 一般分为前序遍历(根 左 右),中序遍历(左 根 右),后序遍历(左 右 根)
     *
     * 二：前序遍历(根、左、右)
     * 前序遍历是指按照根左右的顺序依次遍历,使用非递归遍历,一般会用到栈,利用先进后出的特性来达到访问二叉树节点目的。
     * 来看一下
     *
     * 2.1 前序遍历非递归实现思路：
     *
     * ①：首先将根节点放入到stack中存储
     * ②：遍历栈,如果stack不为空,直接弹出根节点
     * ③：如果右节点不为空,将右节点放入到栈中
     * ④：如果左节点不为空,将左节点放入到栈中
     * 解释：因为栈都是后进先出的,所以在遍历子树的时候应该先将右节点放入栈中,再把左节点放入栈中.
     *
     * 三：中序遍历(左、根、右)
     * 中序遍历的非递归实现思路:
     *
     * ①一直递归的遍历左子节点,然后弹出左子节点,直到不为null
     * ② 然后弹出最近的一个左节点,如果它的right节点不为null，将当前的节点置为right
     * ③ 然后依次继续①的步骤 遍历到左子树为null停止
     *
     * 四：后序遍历(左 右 根)
     * 后序遍历需要用到两个栈,可以想一想为什么需要两个栈？
     * stack1负责将节点的值依次存储,stack2负责存储stack1弹出的节点值 ，因为后序遍历的根节点是最后遍历的顺序,
     * 因此需要一个中转的栈首先将根放入,然后再放入右节点,再放入左节点.最后再逆序(pop)出去就是后序遍历的顺序了。
     *
     * ①首先将根节点放入到stack1中，再弹出根节点,放入到stack2,保证stack2存储的第一个节点是根节点
     * ②再将根节点的左右节点依次放入到stack1中,注意这里是先加入左节点,再加入右节点
     * ③最终再将stack2的节点依次pop出去加入到结果集里面
     *
     * 五：层序遍历
     * 实现思路:
     * 层序遍历需要用到Queue(先进先出)，而不是栈,因为它是顺序添加的。
     *
     * ①声明一个队列,首先添加进去根节点
     * ②弹出根节点,再依次加入左节点,再加入右节点
     * ③依次循环,再弹出上一次的左节点,再加入左节点的左节点、左节点的右节点
     *
     */

    /** 一个二叉树类 */
    public class TreeNode {
        TreeNode left;
        TreeNode right;
        Integer val;
    }



    /**前序遍历 (根 左 右)*/
    public List<Integer> preOrderIteration(TreeNode head) {
        if (head == null) {
            return new ArrayList<>();
        }
        // 结果集
        List<Integer> resultList = new ArrayList<>();
        // 栈
        Stack<TreeNode> stack = new Stack<>();
        // 首先放入头节点
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            resultList.add(node.val);
            // 放入右节点
            if (node.right != null) {
                stack.push(node.right);
            }
            // 放入左节点
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return resultList;
    }

    /** 中序遍历 (左 根 右) */
    public static List<Integer> inOrderIteration(TreeNode head) {
        if (head == null) {
            return new ArrayList<>();
        }
        // 结果集
        List<Integer> resultList = new ArrayList<>();
        TreeNode cur = head;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || cur != null) {
            // 一直先把左节点依次放入栈中
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode node = stack.pop();
            resultList.add(node.val);
            if (node.right != null) {
                cur = node.right;
            }
        }
        return resultList;
    }

    /**
     * 后序遍历 (左 右 根) * * * @param head
     */
    public static List<Integer> postOrderIteration(TreeNode head) {
        if (head == null) {
            return new ArrayList();
        }
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(head);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        while (!stack2.isEmpty()) {
            resultList.add(stack2.pop().val);
        }
        return resultList;
    }

    /** 层序遍历  */
    public List<List<Integer>> levelOrder(TreeNode root) {
        // 返回的结果集
        List<List<Integer>> res = new ArrayList<>();
        // 队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                temp.add(poll.val);
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            res.add(temp);
        }
        return res;
    }

}
