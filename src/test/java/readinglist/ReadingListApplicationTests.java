package readinglist;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)

@SpringBootTest(classes = Application.class)

//@WebAppConfiguration
//@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ReadingListApplicationTests {

	@Test
	@Ignore
	public void contextLoads() {
	}

}
