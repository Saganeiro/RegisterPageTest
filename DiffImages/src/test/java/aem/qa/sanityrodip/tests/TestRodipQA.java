package aem.qa.sanityrodip.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

import com.cognifide.qa.commons.CommonUtils;
import com.cognifide.qa.commons.Global;
import com.cognifide.qa.commons.Parameters;
import com.cognifide.qa.commons.template.TestTemplate;

@RunWith(Parameterized.class)
public class TestRodipQA extends TestTemplate {

	Parameters param;
	static int count = 0;
	
	@Parameterized.Parameters
	public static List<Parameters[]> primeNumbers() {
		List<Parameters> urlList = CommonUtils.readLinksFromFileIntoObject("src/test/resources/" + TestRodipQA.class.getSimpleName() + ".txt");
		
		Parameters[][] urlArray = new Parameters[urlList.size()][];
		
		for (int i=0; i<urlList.size(); i++)
		{
			urlArray[i] = new Parameters[] {urlList.get(i)};
		}

		return Arrays.asList(urlArray);
   }
	
	public TestRodipQA(Parameters param)
	{
		this.param = param;
	}
	
	@Test
	public void TestCase(){
		count++;

		long start = System.currentTimeMillis();		
		
		if (Global.SELENIUM_PREPARATION_MODE)
		{
			CommonUtils.cleanDirectory(patternPath);	
			preparePatterns(param, patternPath, count);
		}
		else
			performTest(param, count);
		
		long stop = System.currentTimeMillis();
		
		testRunTime = reporter.formatTestRunTime(stop-start);
		testNumber = count;
	}
}
