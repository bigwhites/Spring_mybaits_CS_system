package Server.example.VoluntaryReporting.mapper;

import Server.example.VoluntaryReporting.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdministratorMapper {
    @Select("""
            select * from administrator where aName = #{userName}
            """)
    Administrator findByUserName(@Param("userName") String userName);

}
