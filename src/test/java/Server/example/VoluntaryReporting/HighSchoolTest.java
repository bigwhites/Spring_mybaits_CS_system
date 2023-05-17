package Server.example.VoluntaryReporting;

import Server.example.VoluntaryReporting.entity.HighSchool;
import Server.example.VoluntaryReporting.service.Impl.HighSchoolSerImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HighSchoolTest {

	@Autowired
	HighSchoolSerImpl m;

	@Test
	void testFBId() {

//		HighSchool school = m.findById(1001);
		HighSchool school = m.findByName("深圳市第七高级中学");

		System.out.println(school.getSchName());

	}

	@Test
	void seeMd5()
	{
		/*
			root 123456  e10adc3949ba59abbe56e057f20f883e
		 */
		String s = "123456";
		System.out.println(DigestUtils.md5Hex(s));
	}

}
