package qqlogin;

public class HashCode {
    public static String gethashCode(long selfUin, String ptwebqq) {
        int [] n = new int[4];
        for(int t = 0; t < ptwebqq.length(); t++) {
            n[t % 4] = n[t % 4] ^ (int)ptwebqq.charAt(t);
        }
        String [] u = new String [] {"EC", "OK"};
        int [] v = new int [4];
        v[0] = (int)(selfUin >> 24 & 255 ^ (int) u[0].charAt(0));
        v[1] = (int)(selfUin >> 16 & 255 ^ (int) u[0].charAt(1));
        v[2] = (int)(selfUin >> 8 & 255 ^ (int) u[1].charAt(0));
        v[3] = (int)(selfUin & 255 ^ (int) u[1].charAt(1));
        
        int [] ui = new int [8];
        for( int t = 0; t < 8; t++) ui[t] = t % 2 == 0? n[t >> 1] : v[t >> 1];
        char[] hex = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String hash = "";
        for( int t = 0; t < ui.length; t++) {
            hash += hex[ui[t] >> 4 & 15] + "";
            hash += hex[ui[t] & 15] + "";
        }
        return hash;
    }
    
    public static void main(String ...strings) {
        long uin = 404339376;
        String ptwebqq = "1f7ea8996420720ade6cd170107164a3877752e6fb1419152cd89e3945db0f38";
        System.out.println(HashCode.gethashCode(uin, ptwebqq));
    }
    
}
