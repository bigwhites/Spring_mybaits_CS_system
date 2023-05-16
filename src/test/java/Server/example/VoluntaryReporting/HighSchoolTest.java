package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.service.Impl.HighSchoolSerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HighSchoolTest {

	@Autowired
	HighSchoolSerImpl m;

	@Test
	void testFBId() {

		HighSchool school = m.findById(1001);

		System.out.println(school.getSchName());

	}

}
