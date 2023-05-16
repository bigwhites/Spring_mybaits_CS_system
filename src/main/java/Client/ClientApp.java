package Client;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import utils.HttpConnect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ClientApp {

    static String userId;
    static int userType = 1;

    private static boolean logIn() throws IOException {
        System.out.print("请选择用户身份(1.管理员 2.学生)>>>>>>>>");
        int sel1 = ScannerSingleInst.getInst().nextInt();
        System.out.print("请输入用户名>>>>>>>>");
        userId = ScannerSingleInst.getInst().next();
        System.out.print("请输入密码>>>>>>>>");
        String passWord = ScannerSingleInst.getInst().next();
        String md5PassWord = DigestUtils.md5Hex(passWord);
        HttpConnect connect = HttpConnect.getInst();
        connect.addUrlPath("/login");
        if (sel1 == 1) { //管理员
            userType = 1;
            connect.addUrlPath(String.format("/admin/?userName=%s", userId));
        } else {
            userType = 2;
            connect.addUrlPath(String.format("/student/?userName=%s", userId));
        }
        String rightPwdMd5 = connect.GetRequest();
        if(rightPwdMd5.equals("")){
            System.out.println("无该用户，请先注册！");
            System.exit(0);
        }
        int cnt = 1;
        final int MAX_CNT = 5;
        while (!rightPwdMd5.equals(md5PassWord) && cnt <= MAX_CNT) {
            System.out.printf("剩余尝试次数：%d\n",MAX_CNT-cnt);
            System.out.print("密码输入错误，请重新输入>>>>>>>>");
            passWord = ScannerSingleInst.getInst().next();
            md5PassWord = DigestUtils.md5Hex(passWord);

            ++cnt;
        }
        return cnt <= 5;
    }


    private static void printMenu(int userType) throws IOException {
        String filePath = userType==1?"src/main/resources/static/ClientMenuMan.txt":"src/main/resources/static/ClientMenuStudent.txt";
        String line = null;
        try(BufferedReader bf = new BufferedReader(new FileReader(filePath))){
            while ((line = bf.readLine())!=null){
                System.out.println(line);
            }
        }
    }

    public static void run(){
        try {
            boolean isLog = logIn();
            if(!isLog)
            {
                System.out.println("密码输入错误次数超限，系统退出！");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
                try{
                    printMenu(userType);
                    System.out.print("请输入您的选择：");
                    String  selStr= ScannerSingleInst.getInst().next();

                }
                catch (IOException e){   //让程序崩溃
                    e.printStackTrace();
                    break;
                }
                catch (RuntimeException e){  //恢复运行
                    e.printStackTrace();
                }
        }
    }


}
