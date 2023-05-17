package Client.Logic;

import Client.ScannerSingleInst;
import Server.example.VoluntaryReporting.entity.Professional;
import com.alibaba.fastjson.JSON;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utils.HttpConnect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalLogic {
    private ProfessionalLogic(){}


    public static void searchMenu() throws IOException {
        System.out.println("1.按照专业名称查询");
        System.out.println("2.按照院校名称查询");
        System.out.print("请选择>>>>>>");
        final String in = ScannerSingleInst.getInst().next();
        int sel = Integer.parseInt(in);
        switch (sel){
            case 1: {
                final String pName = ScannerSingleInst.getInst().next();
                HttpConnect.getInst().addUrlPath("/professional/searchByPName");
                HttpConnect.getInst().addGetParam("proName", pName);
                String respond = HttpConnect.getInst().GetRequest();
                if (respond .equals("")) {
                    System.out.println("没有该专业！");
                } else {
                    List<Professional> professionals = JSON.parseArray(respond, Professional.class);
                    System.out.printf("共有%d条结果：\n", professionals.size());
                    professionals.forEach(professional -> System.out.println(professional.toString()));
                }
                break;
            }
            case 2: {
                final String uniName = ScannerSingleInst.getInst().next();
                HttpConnect.getInst().addUrlPath("/professional/searchByUName");
                HttpConnect.getInst().addGetParam("uniName", uniName);
                String respond = HttpConnect.getInst().GetRequest();
                if(respond.equals("")){
                    System.out.println("没有该院校或该暂无该院校的专业信息！");
                }
                else {
                    System.out.println("该院校专业如下:");
                    JSON.parseArray(respond,Professional.class).forEach(professional
                            -> System.out.println(professional.toString()));
                }
                break;
            }
            default:
                System.out.println("输入有误，回到上一级");
        }
    }


    /*******
     * #Description: 从给定绝对路径的xls文件中批量新增专业信息
     * #Param: [java.lang.String] -> [filePath] 输入的xls文件的绝对路径
     * #return: int  成功添加的专业个数
     * #Date: 2023/5/16
     *******/
    public static int addProFromXls(String filePath) throws RuntimeException{
        Workbook wb = null;
        List<Professional> professionalList = new ArrayList<>();
        try {
            File file = new File(filePath);
            if(!file.exists()){
                throw new RuntimeException("该文件不存在");
            }
            wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            for(int row = 1 ; row<sheet.getRows();++row ){
                Cell[] cells = sheet.getRow(row);
                for(int i = 0 ;i<cells.length;++i){
                    int forecastScore = Integer.parseInt(cells[3].getContents());
                    int maxCnt = Integer.parseInt(cells[2].getContents());
                    int uId = Integer.parseInt(cells[1].getContents());
                    if(forecastScore < 750 || maxCnt > 1 || uId < 200000){ //防止脏数据进入数据库
                        final Professional p = new Professional(0,cells[0].getContents(),
                                forecastScore,maxCnt,0,null,uId,null);
                        //部分数据在新增时无需使用
                        professionalList.add(p);
                    }
                }
            }
            String resJsonStr = JSON.toJSONString(professionalList);
            HttpConnect.getInst().addUrlPath("/professional/add");
            wb.close();
            String respond = HttpConnect.getInst().PostRequest(resJsonStr);
            return Integer.parseInt(respond);

        }catch (BiffException  | IOException e){
            throw  new RuntimeException(e);
        }

    }


}
