package Server.example.VoluntaryReporting.service.Impl;

import Server.example.VoluntaryReporting.entity.*;
import Server.example.VoluntaryReporting.mapper.AdmitResultMapper;
import Server.example.VoluntaryReporting.service.AdmitResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AdmitResultImpl implements AdmitResultService{

    Logger logger = LoggerFactory.getLogger(AdmitResultImpl.class);
    @Autowired
    AdmitResultMapper admitResultMapper;
    @Autowired
    SchoolChooseImpl schoolChooseImpl ;
    @Autowired
    StudentImpl studentImpl;
    @Autowired
    UniverSityImpl univerSityImpl;
    @Autowired
    ProfessionalImpl professionalImpl;
    @Override
    public List<AdmitResult> admitByRange(Integer PageSzie, Integer begin) {
        List<Student> students = studentImpl.findByRange(PageSzie, begin);
        LinkedList<AdmitResult> admitResults = new LinkedList<>();
        boolean nextStudent;
        for (Student student : students) { // 遍历该页的学生
            nextStudent = false;
            logger.info(String.valueOf(student.getSId()));
            List<SchoolChoose> schoolChooses = schoolChooseImpl.findBySId(student.getSId());
            if(schoolChooses==null ||schoolChooses.size()==0){  //没有填报志愿的不录取
                logger.info("continue");
                admitResults.add(new AdmitResult(student.getSId(),student.getScoreIndex(),null,student.getTotalScore()
                ,null));
                continue;
            }
            logger.info("志愿数量：{}",schoolChooses.size());
            AdmitResult admitResult =
                    new AdmitResult(student.getSId(),
                            student.getScoreIndex(), null,
                            student.getTotalScore(), null);
            for (SchoolChoose schoolChoose : schoolChooses) { //遍历所填报的每一个志愿
                Professional[] professionals = schoolChoose.getProfessionals();
                logger.info(schoolChoose.toString());
                for (Professional professional : professionals) {

                    if (professional != null) {
                        if (admit(admitResult, professional)) {
                            nextStudent = true;
                            break;
                        }
                    }
                    else if (schoolChoose.getAcceptAdjust() == 1) { //接受调剂
                        UniverSity univerSity = univerSityImpl.findById(schoolChoose.getUniId());
                        List<Professional> allPros = univerSity.getProfessionals();

                        for (Professional pro : allPros) { //遍历该大学的每一个专业
                            if(admit(admitResult,pro)){
                                nextStudent = true;
                                break;
                            }
                        }
                    }
                    else {
                        nextStudent = true; //不服从调剂则退档
                        break;
                    }
                }
                if (nextStudent) { //退档 或 录取成功
                    break;
                }
            }
            admitResults.addLast(admitResult);
        }
        return admitResults;
    }

    /*******
     * #Description: 判断专业是否可以继续录取成功并录取
     * #Param: [AdmitResult,Professional] -> [admitResult, professional]
     * #return: boolean
     * #Date: 2023/5/19
     *******/
    private  boolean admit(AdmitResult admitResult, Professional professional) {
        if(professional.getCurCnt() + 1 <= professional.getMaxCnt()) { //判断是否可以继续录取
            admitResult.setAdmitProId(professional.getProId());
            admitResult.setAdmitPro(professional);
            professional.setCurCnt(professional.getCurCnt() + 1);
            if (professional.getMinScore()==null||professional.getMinScore() > admitResult.getTotalScore()) {
                professional.setMinScore(admitResult.getTotalScore());
            }
            logger.info("录取成功！\n{}",professional.getProId());
            professionalImpl.update(professional);
            return true;
        }
        return false;
    }

    @Override
    public int admitAndLog() {
        int cnt = 0;
        final int PAGE_SIZE = 2;
        cleanRecord();
        logger.info("开始录取");
        int sum = studentImpl.getNum();
        for(int begin = 0 ; begin < sum  ; begin += PAGE_SIZE ){  //分页录取
            List<AdmitResult> admitResults = admitByRange(PAGE_SIZE,begin);
            logger.info("{},{} size={}",begin,begin+PAGE_SIZE,admitResults.size());
            for (AdmitResult admitResult : admitResults) {
                cnt += admitResultMapper.insertResult(admitResult);
            }
        }
        logger.info("共录取了{}个学生",cnt);
        return cnt;
    }

    /*******
     * #Description: 清除院校表中的记录并且更新预测分数
     * #Param: [] -> []
     * #return: void
     * #Date: 2023/5/20
     *******/
    public void cleanRecord() {
        for(Professional professional : professionalImpl.findAll()){ //清空之前的录取分数
            professional.setMinScore(null);
            professional.setCurCnt(0);
            if(professional.getMinScore() !=null){
                professional.setForecastScore(professional.getMinScore());
            }
            //logger.info("oriData:{}",professional.toString());
            professionalImpl.update(professional);
        }
    }

    @Override
    public int deleteAllResults() {
        return admitResultMapper.deleteAllResults();
    }

    @Override
    public int resultsCount() {
        return admitResultMapper.resultsCount();
    }

    @Override
    public List<AdmitResult> findAll() {
        List<AdmitResult> admitResults = admitResultMapper.findAll();
        for (AdmitResult admitResult : admitResults) {
            admitResult.setTotalScore(studentImpl.findById(admitResult.getSId()).getTotalScore());
            if(admitResult.getAdmitProId()!=null) {
                admitResult.setAdmitPro(professionalImpl.findById(admitResult.getAdmitProId()));
            }
        }
        return admitResults;
    }

    @Override
    public AdmitResult findBySid(Integer sId) {
        AdmitResult admitResult = admitResultMapper.findBySId(sId);
        admitResult.setTotalScore(studentImpl.findById(admitResult.getSId()).getTotalScore());
            if(admitResult.getAdmitProId()!=null) {
                admitResult.setAdmitPro(professionalImpl.findById(admitResult.getAdmitProId()));
            }
        return admitResult;
    }
}
