package Server.example.VoluntaryReporting.mapper;

import Server.example.VoluntaryReporting.entity.AdmitResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdmitResultMapper {

   int insertResult(AdmitResult result);

   int deleteAllResults();

   int resultsCount();

   List<AdmitResult> findAll();

   AdmitResult findBySId(Integer sId);
}
