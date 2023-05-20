package Client.Logic;

import Client.ScannerSingleInst;
import Server.example.VoluntaryReporting.entity.AdmitResult;
import Server.example.VoluntaryReporting.entity.Student;
import com.alibaba.fastjson.JSON;
import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.codec.digest.DigestUtils;
import utils.HttpConnect;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AdminLogic {
    private AdminLogic() {}

    /*******
     * #Description: 删除账户
     * #Param: [java.lang.String] -> [userId]
     * #return: void
     * #Date: 2023/5/18
     *******/
    public static boolean deleteById(String userId) throws IOException {
        HttpConnect.getInst().addUrlPath("/login/deleteAdmin");
        HttpConnect.getInst().addGetParam("userName",userId);
        return ! HttpConnect.getInst().GetRequest().equals("0");

    }

    /*******
     * #Description: 准备POST请求的参数
     * #Param: [] -> []
     * #return: Map<S,S> userName,pwdMd5
     * #Date: 2023/5/19
     *******/
    public static Map<String,String> prepareAdminData(){
        System.out.print("请输入您的用户名>>>>>>");
        String uesrName = ScannerSingleInst.getInst().next();
        System.out.print("请输入您的密码>>>>>>");
        String pwd = ScannerSingleInst.getInst().next();
        Map<String,String> parms = new HashMap<>();
        parms.put("userName",uesrName);
        parms.put("pwdMd5", DigestUtils.md5Hex(pwd));
        return parms;
    }

    public static void statAdmit() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/startAdmin");
        int respond = Integer.parseInt(HttpConnect.getInst().PostRequset(prepareAdminData()));
        if(respond==-1){
            System.out.println("管理员密码错误，录取失败");
        }
        else {
            System.out.printf("成功录取了%d个学生的信息\n",respond);
        }
    }

    public static void getAllAdmitRes() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/getAllResult");
        String resPond = HttpConnect.getInst().PostRequset(prepareAdminData());
        if(resPond.equals("ERROR PWD")){
            System.out.println("密码错误");
        }
        else if(resPond.equals("")) {
            System.out.println("暂无录取信息");
        }
        List<AdmitResult> admitResults = JSON.parseArray(resPond, AdmitResult.class);
        for (AdmitResult admitResult : admitResults) {
            System.out.println(admitResult.toString());
        }
    }

    public static void queryAdmitStatus() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/queryStatus");
        String res = HttpConnect.getInst().GetRequest();
        if(res.equals("1")){
            System.out.println("录取工作已经结束");
        }
        else {
            System.out.println("录取工作还未开始");
        }
    }

    public static void clearResults() throws IOException {
        HttpConnect.getInst().addUrlPath("/admit/clearAllRes");
        String respond = HttpConnect.getInst().PostRequset(prepareAdminData());
        if(respond.equals("")){
            System.out.println("失败");
        }
        else if(respond.equals("ERROR PWD")){
            System.out.println("管理员账号或密码错误！");
        }
        else {
            System.out.println("成功清除录取结果");
        }
    }

    public static int saveStu2Xls(String filePath) {  
        File optFile = new File(filePath);
        WritableWorkbook wb = null;
        int size = 0;
        if (optFile.exists()) {
            optFile.delete();
        }
        try {
            Label operLab = null;
            optFile.createNewFile();
            wb = Workbook.createWorkbook(optFile);
            WritableSheet sheet = wb.createSheet("student", 0);
            final String[] heads = {"学号", "姓名", "性别", "身份证号码", "选科类别", "总分", "录取学校", "录取专业"};
            for (int i = 0; i < heads.length; i++) {
                operLab = new Label(i, 0, heads[i]);
                sheet.addCell(operLab);
            }
            HttpConnect.getInst().addUrlPath("/student/findAll");
            String respond = HttpConnect.getInst().GetRequest();
            List<Student> students = JSON.parseArray(respond, Student.class);
            if (students == null) {
                return 0;
            } else {
                size = students.size();
            }
            int row = 1;
            for (Student student : students) {
                HttpConnect.getInst().addUrlPath("/admit/getResultBySId");
                HttpConnect.getInst().addGetParam("sId", String.valueOf(student.getSId()));
                String resAdmit = HttpConnect.getInst().GetRequest();
                AdmitResult admitResult = JSON.parseObject(resAdmit, AdmitResult.class);
                String uName = "-", proName = "无被录取的专业";
                if (admitResult.getAdmitPro() != null) {
                    uName = admitResult.getAdmitPro().getUniverSity().getUName();
                    proName = admitResult.getAdmitPro().getProName();
                }
                String[] rowCell = {  //每一行的数据
                        String.valueOf(student.getSId()), student.getSName(), student.getSSex(),
                        student.getIdentyId(), student.getTypeFlag() == 1 ? "物理类" : "历史类",
                        String.valueOf(student.getTotalScore()), uName, proName
                };
                for (int col = 0; col < heads.length; ++col) {
                    operLab = new Label(col, row, rowCell[col]);
                    sheet.addCell(operLab);
                }
                ++row;
            }
            wb.write();
            wb.close();
        } catch (IOException | WriteException e) {
            throw new RuntimeException(e);
        }
        return size;
    }
}
