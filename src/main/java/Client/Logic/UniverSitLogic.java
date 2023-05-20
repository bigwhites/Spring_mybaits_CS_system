package Client.Logic;

import Client.ScannerSingleInst;
import Server.example.VoluntaryReporting.entity.UniverSity;
import com.alibaba.fastjson2.JSON;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import utils.HttpConnect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniverSitLogic {

    private UniverSitLogic(){}

    public static boolean save2Xls(String fileName) throws IOException {
        HttpConnect.getInst().addUrlPath("/university/getAll");
        String jsonData = HttpConnect.getInst().GetRequest();
        List<UniverSity> univerSities = JSON.parseArray(jsonData, UniverSity.class);
        final String filePath = "src/main/resources/output/" + fileName;
        File xlsFile = new File(filePath);  //创建文件
        if (xlsFile.exists()) {
            xlsFile.delete();
        }
        WritableWorkbook wb = null;  //表格
        try {
            xlsFile.createNewFile();
            wb = Workbook.createWorkbook(xlsFile); //创建.xls表格
            WritableSheet sheet = wb.createSheet("院校信息记录", 0); //创建表
            Label operLab = null;
            final String[] HEADS = {"院校代码", "院校名称", "专业数量"};
            for (int i = 0; i < HEADS.length; ++i) {  //写表头
                operLab = new Label(i, 0, HEADS[i]);  //新增单元格
                sheet.addCell(operLab); //把新增的数据暂存
            }
            for (int row = 1; row <= univerSities.size(); ++row) {
                String[] rowsArr = new String[3];
                rowsArr[0] = String.valueOf(univerSities.get(row-1).getUId());
                rowsArr[1] = univerSities.get(row-1).getUName();
                rowsArr[2] = String.valueOf(univerSities.get(row-1).getProfessionals().size());
                for (int col = 0; col < 3; ++col) {
                    operLab = new Label(col, row, rowsArr[col]); //把rowsArr【col】的内容写入表的row行col列
                    sheet.addCell(operLab);
                }
                if(row % 20 == 0){
                    wb.write(); //写入
                }
            }
            wb.write(); //写入
        } catch (WriteException e) {
            return false;
        } finally {
            try {
                assert wb != null;
                wb.close();
            } catch (WriteException e) {
                return false;
            }
        }
        return true;
    }

    public static void searhMenu() throws IOException {
        System.out.println("1.通过院校代码查询");
        System.out.println("2.通过院校名查询");
        System.out.print("请输入选择>>>>>>");
        String selStr = ScannerSingleInst.getInst().next();;
        int sel = Integer.parseInt(selStr);
        switch (sel){
            case 1: { //通过学校代码查询
                HttpConnect.getInst().addUrlPath("/university/searchById");
                System.out.print("请输入院校代码>>>>>>");
                String uIdStr = ScannerSingleInst.getInst().next();
                HttpConnect.getInst().addGetParam("uniId", uIdStr);
                String uinJsonStr = HttpConnect.getInst().GetRequest();
                if (uinJsonStr.equals("")) {
                    System.out.println("没有该院校");
                } else {
                    UniverSity uni = JSON.parseObject(uinJsonStr, UniverSity.class);
                    System.out.println(uni.toString());
                }
                break;
            }
            case 2: {//通过院校名查询
                System.out.print("请输入院校名>>>>>>");
                String uName = ScannerSingleInst.getInst().next();
                HttpConnect.getInst().addUrlPath("/university/searchByName");
                HttpConnect.getInst().addGetParam("uName", uName);
                String jsonStr = HttpConnect.getInst().GetRequest();
                if (!jsonStr.equals("")) {
                    System.out.println(JSON.parseObject(jsonStr, UniverSity.class).toString());
                } else {
                    System.out.println("没有该院校");
                }
                break;
            }
            default: {
                System.out.println("无该选项，返回上一级!");
            }
        }
    }

    public static int addFromXls(String filePath) throws RuntimeException{
        Workbook wb = null;
        List<UniverSity> ulist = new ArrayList<>();
        try{
            File file = new File(filePath);
            if(!file.exists()){
                throw new RuntimeException("该文件不存在");
            }
            wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            for(int i = 1 ; i < sheet.getRows();++i){  //第0行为表头舍去
                Cell[] cells = sheet.getRow(i);   //仅有一行
                String uName = cells[0].getContents();
                if(!uName.matches("^[\\u4E00-\\u9FA5]+$")){
                    continue;
                }
                UniverSity u = new UniverSity(0,uName,null);//id为数据库自增主键 无需给出
                ulist.add(u);
            }
            String resJsonStr = JSON.toJSONString(ulist);  //json串
            HttpConnect.getInst().addUrlPath("/university/add");
            String respond = HttpConnect.getInst().PostRequest(resJsonStr);
            wb.close();
            return Integer.parseInt(respond);
        } catch (BiffException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update() throws IOException {
        System.out.print("请输入院校代码>>>>>>");
        String uId = ScannerSingleInst.getInst().next();
        System.out.print("输入新的院校名称>>>>>>");
        String uName = ScannerSingleInst.getInst().next();
        String jsonString = JSON.toJSONString(new UniverSity(Integer.parseInt(uId), uName, null));
        HttpConnect.getInst().addUrlPath("/university/update");
        String respond = HttpConnect.getInst().PostRequest(jsonString);
        if(respond.equals("-1")){
            System.out.println("新院校名不能与其他院校相同");
        }
        else if(respond.equals("1")){
            System.out.println("修改成功！");
        }
        else {
            System.out.println("没有该院校！");
        }
    }
}
