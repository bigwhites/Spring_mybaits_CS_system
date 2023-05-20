package Client;


import Client.Logic.AdminLogic;
import Client.Logic.ProfessionalLogic;
import Client.Logic.StudentLogic;
import Client.Logic.UniverSitLogic;
import org.apache.commons.codec.digest.DigestUtils;
import utils.HttpConnect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ClientApplicatiion {

    static String userId = String.valueOf(210115);   //考生号 或 管理员用户名   "testUser"
    static int userType =2;
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
            connect.addUrlPath(String.format("/student/?uId=%s", userId));
        }
        String rightPwdMd5 = connect.GetRequest();
        if(rightPwdMd5.equals("")) {
            if (userType == 1) {
                System.out.println("无该学生");
                System.exit(-1);
            } else {
                System.out.println("无该用户，请先注册!");
                signUp();
                return true;
            }
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
        return cnt < 5;
    }
    private static void signUp() throws IOException {
        while (true) {
            System.out.print("请输入您的用户名:");
            String userName = ScannerSingleInst.getInst().next();
            System.out.print("请输入您的密码:");
            String pwd = ScannerSingleInst.getInst().next();
            String md5Pwd = DigestUtils.md5Hex(pwd);
            HttpConnect.getInst().addUrlPath("/login/signUp");
            HttpConnect.getInst().addGetParam("userName", userName);
            HttpConnect.getInst().addGetParam("md5Pwd", md5Pwd);
            String res = HttpConnect.getInst().GetRequest();
            if (res.equals("user exist")) {
                System.out.println("您输入的用户名已被注册，请重新输入！");
            } else {
                System.out.println("注册成功");
                break;
            }
        }
    }
    private static void printMenu(int userType) throws IOException {
        String filePath = userType==1?"src/main/resources/static/ClientMenuMan.txt":"src/main/resources/static/ClientMenuStu.txt";
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
                try {
                    printMenu(userType);
                    System.out.print("请输入您的选择>>>>>>");
                    String selStr = ScannerSingleInst.getInst().next();
                    if(userType==1) {
                        handleMannager(selStr);
                    }
                    else
                    {
                        handleStudent(selStr);
                    }
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

    private static void handleStudent(String selStr) throws IOException {
        switch (Integer.parseInt(selStr)) {
            case 0 -> {
                System.exit(0);
            }
            case 1 -> {  //查询个人信息
                StudentLogic.searchSelf(userId);
            }
            case 2 -> {
                StudentLogic.upDate(userId);
            }
            case 3 -> {  //修改密码
                StudentLogic.upDatePwd(userId);
            }
            case 4 -> {  //填报志愿
                StudentLogic.addSchoolChoose(Integer.parseInt(userId));
            }
            case 5 -> { //查询所填报的专业
                StudentLogic.showCurChoose(userId);
            }
            case 6 -> {  //查询录取结果
                StudentLogic.showAdmitRes(userId);
            }
            case 7 -> { //展示推荐院校
                StudentLogic.showRecommend(userId);
            }
            case 8 -> { //按照专业名称寻找专业
                ProfessionalLogic.searchByName();
            }
            default -> {
                System.out.println("输入错误");
            }
        }
    }

    private static void handleMannager(String selStr) throws IOException {
        switch (Integer.parseInt(selStr)) {
            case 0 -> {  //退出
                System.exit(0);
            }
            case 1 -> { //导出院校信息
                System.out.print("请输入输出的文件名(*.xls)>>>>>>");
                final String fileName = ScannerSingleInst.getInst().next();
                boolean ok = UniverSitLogic.save2Xls(fileName);
                System.out.println(ok ? "导出成功！" : "导出失败！");
            }
            case 2 -> {  //注销当前用户
                System.out.println(
                        AdminLogic.deleteById(userId) ? "成功" : "失败");
                System.exit(0);
            }
            case 3 -> { //批量新增院校
                System.out.print("输入表格文件的绝对路径>>>>>");
                String filePath = ScannerSingleInst.getInst().next();
                System.out.printf("成功新增了%d个院校的信息！"
                        , UniverSitLogic.addFromXls(filePath));
            }
            case 4->{ //修改院校信息
            UniverSitLogic.update();
            }
            case 5 -> {  //查询院校
                UniverSitLogic.searhMenu();
            }
            case 6 ->{  //按照名称查找专业
                ProfessionalLogic.searchByName();
            }
            case 7 -> {   //查询专业
                ProfessionalLogic.searchMenu();
            }
            case 8 -> {  //批量新增院校专业
                System.out.print("输入表格文件的绝对路径>>>>>");
                String filePath = ScannerSingleInst.getInst().next();
                ProfessionalLogic.addProFromXls(filePath);
            }
            case 9 ->{  //删除专业信息
                ProfessionalLogic.deletePro();
            }
            case 10->{ //修改院校专业
                ProfessionalLogic.updatePro();
            }
            case 11 -> {  //修改学生密码
                System.out.println("请输入学生的学号>>>>>>");
                final String sId = ScannerSingleInst.getInst().next();
                StudentLogic.upDatePwd(sId);
            }
            case 12 -> { //批量添加学生（表格读入）
                System.out.print("输入表格文件的绝对路径>>>>>");
                String filePath = ScannerSingleInst.getInst().next();
                System.out.printf("成功导入了%d个学生\n", StudentLogic.addFormxls(filePath));
            }
            case 13 ->{  //导出学生信息到表格中
                System.out.print("请输入输出路径>>>>>>");
                String filePath = ScannerSingleInst.getInst().next();
                System.out.printf("成功导出了%d个学生的信息",AdminLogic.saveStu2Xls(filePath));
            }
            case 14 -> { //修改学生信息
                System.out.println("请输入学生的学号>>>>>>");
                final String sId = ScannerSingleInst.getInst().next();
                if (StudentLogic.stuExist(sId)) {
                    StudentLogic.upDate(sId);
                } else {
                    System.out.println("无该学生！");
                }
            }
            case 15 -> { //查询学生信息
                System.out.print("请输入考生号>>>>>>");
                String uId = ScannerSingleInst.getInst().next();
                if (StudentLogic.stuExist(uId)) {
                    StudentLogic.searchSelf(uId);
                } else {
                    System.out.println("无该考生");
                }
            }
            case 16 -> { //查询某学生的录取结果
                System.out.print("输入考生号>>>>>>");
                String sId = ScannerSingleInst.getInst().next();
                StudentLogic.showAdmitRes(sId);
            }
            case 17 -> { //开始录取
                AdminLogic.statAdmit();
            }
            case 18 -> {
                AdminLogic.getAllAdmitRes();
            }
            case 19 -> {
                AdminLogic.clearResults();
            }
            case 20 -> {
                AdminLogic.queryAdmitStatus();
            }
            default -> {
                System.out.println("输入有误！");
            }
        }
    }


}
