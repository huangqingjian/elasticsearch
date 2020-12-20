package com.hqj;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

    public static void main(String[] args) {
//        int[] temp = {-10,-3,0,5,9,10,25,33};
//        TreeNode root = sortedArrayToBST(temp);
//        if(root != null) {
//            print(Lists.newArrayList(root));
//        }
//        new AtomicInteger(0).compareAndSet(0, 1);
//        generateParenthesis(4);
//        Arrays.sort();
//        Math.p
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        ListNode node = reverseKGroup(node1, 2);
//        while(node != null) {
//            System.out.println(node.val);
//            node = node.next;
//        }
        System.out.println(divide(-2147483648, -1));
    }

    public static int divide(int dividend, int divisor) {
        long beichushu = dividend;
        long chushu = divisor;
        if(divisor == 1) {
            return dividend;
        }
        if(divisor == -1) {
            if(dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            }
        }
        boolean flag = false;
        if(beichushu < 0 && chushu < 0) {
            beichushu = 0 - beichushu;
            chushu = 0 - chushu;
        } else if (beichushu > 0 && chushu < 0) {
            chushu = 0 - chushu;
            flag = true;
        } else if (beichushu < 0 && divisor > 0) {
            beichushu = 0 - beichushu;
            flag = true;
        }
        // 先考虑被除数和除数都为正数
        if(beichushu < chushu) {
            return 0;
        }
        int shang = 0;
        while(beichushu >= chushu) {
            shang += 1;
            beichushu = beichushu - chushu;
        }
        if(flag) {
            return 0 - shang;
        }
        return shang;
    }

    public static String multiply(String num1, String num2) {
        long result = 0;
        for(int i = num1.length() - 1; i >= 0; i--) {
            long temp = 0;
            int ys = 0;
            int shang = 0;
            for(int j = num2.length() - 1; j >= 0; j--) {
                int sj = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                ys = (sj + shang) % 10;
                if(j > 0) {
                    temp += ys * Math.pow(10, num2.length() -j - 1);
                } else {
                    temp += (sj + shang) * Math.pow(10, num2.length() -j - 1);
                }
                shang = (sj + shang) / 10;
            }
            result = result + temp * (int) Math.pow(10, num1.length() -i - 1);
        }
        return result + "";
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        boolean reverse = false;
        int index = 0;
        ListNode current = head;
        while(current != null) {
            index++;
            if(index == k) {
                reverse = true;
                break;
            }
            current = current.next;
        }
        if(reverse) {
            ListNode origin = head;
            ListNode prev = null;
            ListNode end = current.next;
            while(head != null) {
                ListNode next = head.next;
                head.next = prev;
                prev = head;
                if(next == end) {
                    break;
                }
                head = next;
            }
            origin.next = reverseKGroup(end, k);
        }
        return head;
    }

    public static ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;

        int temp = head.val;
        head.val = next.val;
        next.val = temp;

        ListNode nextnext = next.next;
        next.next = head;
        head.next = swapPairs(nextnext);

        return next;
    }

    public static List<String> generateParenthesis(int n) {
        List<String> combinations = new ArrayList();
        generateAll(new char[2 * n], 0, combinations);
        return combinations;
    }

    public static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current))
                result.add(new String(current));
        } else {
            current[pos] = '(';
            generateAll(current, pos+1, result);
            current[pos] = ')';
            generateAll(current, pos+1, result);
        }
    }

    public static boolean valid(char[] current) {
        int balance = 0;
        for (char c: current) {
            if (c == '(') balance++;
            else balance--;
            if (balance < 0) return false;
        }
        return (balance == 0);
    }

//    public static void print(List<TreeNode> nodes) {
//        if(!CollectionUtils.isEmpty(nodes)) {
//            List<TreeNode> childNodes = new ArrayList<>();
//            for(TreeNode node : nodes) {
//                System.out.print("******");
//                System.out.print(node.val);
//                System.out.print("******");
//                if(node.left != null) {
//                    childNodes.add(node.left);
//                }
//                if(node.right != null) {
//                    childNodes.add(node.right);
//                }
//            }
//            System.out.println();
//            print(childNodes);
//        }
//
//    }
//
//    public static TreeNode sortedArrayToBST(int[] nums) {
//        if(nums == null || nums.length == 0) {
//            return null;
//        }
//        int start = 0;
//        int end = nums.length - 1;
//        int mid = (end - start) / 2 + start;
//        // 根节点
//        TreeNode root = new TreeNode(nums[mid]);
//        digui(root, nums, start, mid, end);
//        return root;
//    }
//
//    public static void digui(TreeNode node, int[] nums, int start, int mid, int end) {
//        if(mid > start) {
//            int leftMid = (mid - 1 - start) / 2 + start;
//            node.left = new TreeNode(nums[leftMid]);
//            digui(node.left, nums, start, leftMid, mid - 1);
//        }
//        if(end > mid) {
//            int rightMid = (end - mid - 1) / 2 +  mid + 1;
//            node.right = new TreeNode(nums[rightMid]);
//            digui(node.right, nums, mid + 1, rightMid, end);
//        }
//    }

    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int x) { val = x; }
    }
}
