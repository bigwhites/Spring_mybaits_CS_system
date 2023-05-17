package Client.Logic;

import Client.ScannerSingleInst;
import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.entity.SchoolChoose;
import Server.example.VoluntaryReporting.entity.Student;
import com.alibaba.fastjson2.JSON;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import utils.HttpConnect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StudentLogic {
    private StudentLogic() {
    }

    private static final HashMap<String, Integer> subjectName2Id;
    private static final String REGEX_2_IDCRD =   //匹配身份证的正则
            "^[1-9]\\\\d{5}(19|20)\\\\d{2}(0\\\\d|10|11|12)" +
                    "([0-2]\\\\d|30|31)\\\\d{3}[0-9xX]$";


    static {
        int i = 0;
        subjectName2Id = new HashMap<>();
        String[] names = {"生物", "地理", "化学", "政治"};
        for (String name : names) {
            subjectName2Id.put(name, ++i);
        }

    }

    /*******
     * #Description: 从xls批量新增学生
     * #Param: [java.lang.String] -> [filePath] 表格文件绝对路径
     * #return: int 成功添加的考生个数
     * #Date: 2023/5/17
     *******/
    public static int addFormxls(String filePath) {
        File xlsFile = new File(filePath);
        if (!xlsFile.exists()) {
            throw new RuntimeException("没有该文件!");
        }
        Workbook wb = null;

        try {
            List<Student> students = new LinkedList<>();
            wb = Workbook.getWorkbook(xlsFile);
            Sheet sheet = wb.getSheet(0);
            for (int row = 1; row < sheet.getRows(); ++row) {
                Cell[] rows = sheet.getRow(row);
                if (rows[0] == null || rows[0].getContents().equals("")) {
                    break;
                }
                String sex = rows[1].getContents();
                int[] mainScores = {Integer.parseInt(rows[3].getContents()), Integer.parseInt(rows[4].getContents()),
                        Integer.parseInt(rows[5].getContents())};  //语数英成绩
                int[] branchScore = {Integer.parseInt(rows[7].getContents()), Integer.parseInt(rows[9].getContents())
                        , Integer.parseInt(rows[11].getContents())};
                String identityCardId = rows[14].getContents();
                if (!identityCardId.matches(REGEX_2_IDCRD)) {  //验证是否是身份证号
                    continue;
                }
                String highSchoolName = rows[13].getContents();  //高中的名字
                HttpConnect.getInst().addUrlPath("/student/findSchoolByName");
                HttpConnect.getInst().addGetParam("schName", highSchoolName);
                String respond = HttpConnect.getInst().GetRequest();
                int highSchoolId = JSON.parseObject(respond, HighSchool.class).getSchId();

                if (respond.equals("")) {  //没有该学校
                    continue;
                }
                int totalScore = 0;
                boolean uselessRow = false;
                for (int score : mainScores) {
                    if (!(score >= 0 && score <= 150)) {
                        uselessRow = true;
                        break;
                    }
                    totalScore += score;
                }
                for (int score : branchScore) {
                    if (!(score >= 0 && score <= 100)) {
                        uselessRow = true;
                        break;
                    }
                    totalScore += score;
                }
                if (!(sex.equals("男") || sex.equals("女")) || uselessRow) {
                    continue;
                }

                students.add(new Student(
                        0, rows[0].getContents(), identityCardId, sex, DigestUtils.md5Hex(rows[2].getContents()),
                        mainScores[0], mainScores[1], mainScores[2],
                        rows[6].getContents().equals("物理") ? 1 : 0, branchScore[0],
                        null, subjectName2Id.get(rows[8].getContents()), branchScore[1],
                        null, subjectName2Id.get(rows[10].getContents()), branchScore[2],
                        totalScore, highSchoolId, null
                ));
            }
            HttpConnect.getInst().addUrlPath("/student/add");
            return Integer.parseInt(  //返回添加成功的个数
                    HttpConnect.getInst().
                            PostRequest(JSON.toJSONString(students)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /*******
     * #Description: 查询已经填报的专业数量
     * #Param: [java.lang.String] -> [userId]
     * #return: int
     * #Date: 2023/5/17
     *******/
    public static int getChosenCnt(String userId) throws IOException {
        HttpConnect.getInst().addUrlPath("/student/getChooseCnt");
        HttpConnect.getInst().addGetParam("sId", userId);
        return Integer.parseInt(HttpConnect.getInst().GetRequest());
    }

    /*******
     * #Description:  填报志愿 保证不超最大限度时 可分多次调用
     * #Param: [int] -> [userId]
     * #return: void
     * #Date: 2023/5/17
     *******/

    @SneakyThrows
    public static void addSchoolChoose(int userId) throws IOException, RuntimeException{
        int oriCnt = getChosenCnt(String.valueOf(userId));
        if( oriCnt == SchoolChoose.MAX_CNT){
            System.out.printf("当前填报专业已达上限%d个，无法填报",SchoolChoose.MAX_CNT);
            return;
        }
        ArrayList<SchoolChoose> schoolChooses = new ArrayList<>(SchoolChoose.MAX_CNT - oriCnt);
        for(int i = 1 ; i + oriCnt <= SchoolChoose.MAX_CNT ; ++i){
            System.out.print("请输入填报的院校的 院校id,志愿排位,是否接受调剂(y/n) 和所选的专业的专业号(1-4个) ,中间用,隔开\n");
            System.out.print("请输入>>>>");
            ScannerSingleInst.getInst().nextLine();
            String input = ScannerSingleInst.getInst().nextLine();
            String[] tokens = input.split(",");
            if(tokens.length < 4  || tokens.length > 4+3){  //验证是否为1-4个专业
                throw new RuntimeException("输入格式错误！");
            }
            Integer[] proIds = new Integer[4];
            SchoolChoose schoolChoose = new SchoolChoose();
            schoolChoose.setSId(userId);
            schoolChoose.setUniId(Integer.parseInt(tokens[0]));
            schoolChoose.setOrder(Integer.parseInt(tokens[1]));
            schoolChoose.setAcceptAdjust(tokens[2].equals("y") ? 1 : 0);
            int proCnt = 0;
            for(int j = 3 ; j < tokens.length ; ++j ){
                proIds[proCnt++] = Integer.valueOf(tokens[j]);
            }
            for (int j = 0 ; j < proIds.length ; ++j) {
                Method m  = SchoolChoose.class.getMethod(
                        "setProId" + String.valueOf(j+1), Integer.class);
                m.invoke(schoolChoose,proIds[j]);
            }
            System.out.println(schoolChoose);
            schoolChooses.add(schoolChoose);
            System.out.print("是否继续填报？（0/1）>>>>>>");
            String sel  = ScannerSingleInst.getInst().next();
            if(sel.equals("0")){
                break;
            }
        }
        HttpConnect.getInst().addUrlPath("/student/addSchoolChoose");
        System.out.printf("共填报了%s个专业\n",
                HttpConnect.getInst().PostRequest(JSON.toJSONString(schoolChooses)));
    }
}
