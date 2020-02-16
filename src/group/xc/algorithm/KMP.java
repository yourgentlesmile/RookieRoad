package group.xc.algorithm;

public class KMP {
    public void calcPrefixTable() {
        String s = "abcabd";
        char[] chars = s.toCharArray();
        int[] prefix = new int[chars.length];
        prefix[0] = 0;
        int len = 0;
        int i = 1;
        // len表示已经匹配上的最大公共长度
        while (i < chars.length) {
            if(chars[i] == chars[len]) {
                len++;
                prefix[i] = len;
                i++;
            }else {
                if(len > 0) len = prefix[len - 1];
                else {
                    prefix[i] = len;
                    i++;
                }
            }
        }
        //将prefixTable整体向右移动一位，第一位填充-1
        System.arraycopy(prefix, 0, prefix, 1, prefix.length - 2 + 1);
        prefix[0] = -1;
    }
    public void calcKMP(String rawtext, String rawpattern) {
        char[] text = rawtext.toCharArray();
        char[] pattern = rawpattern.toCharArray();
        int[] prefixTable = new int[10];//后面用上面的函数替换
        int m = rawtext.length();
        int n = rawpattern.length();
        int i = 0, j = 0;
        while (i < m) {
            if(j == n - 1 && pattern[j] == text[i]) {
                System.out.println("Found at" + (i - j));
                j = prefixTable[j];
            }
            if(text[i] == pattern[j]) {
                i++;
                j++;
            }else {
                j = prefixTable[j];
                if(j == -1) {
                    i++;
                    j++;
                }
            }
        }
    }
    public static void main(String[] args) {

    }
}
