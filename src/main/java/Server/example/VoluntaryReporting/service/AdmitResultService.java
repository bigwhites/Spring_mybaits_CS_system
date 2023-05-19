package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.AdmitResult;

import java.util.List;

public interface AdmitResultService {

    /*******
     * #Description:  分组查询指定排名范围的录取结果
     * #Param: [java.lang.Integer, java.lang.Integer] -> [begin, end] 查询的范围
     * #return: java.util.List<Server.example.VoluntaryReporting.entity.AdmitResult> 返回的结果集
     * #Date: 2023/5/19
     *******/
    List<AdmitResult> admitByRange(Integer pageSize , Integer begin);

    /*******
     * #Description: 录取并将结果写入数据库中
     * #Param: [] -> [] 无参数
     * #return: int 成功写入数据库的录取记录数量
     * #Date: 2023/5/19
     ******/
    int admitAndLog() ;

    int deleteAllResults();

    int resultsCount();

    List<AdmitResult> findAll();

    AdmitResult findBySid(Integer sId);



}
