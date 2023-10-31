package utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.karate.RuntimeHook;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.RuntimeHookFactory;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.core.Step;

public class HookFactory implements RuntimeHookFactory {

	class FeatureData {
		String featureFileName;
		List<ScenariosData> scenarios = new ArrayList<ScenariosData>();

		FeatureData(String featureFileName) {
			this.featureFileName = featureFileName;
		}
	}

	class ScenariosData {
		public int stepCount = 0;
	}

	private static List<FeatureData> calls = new ArrayList<FeatureData>();

	@Override
	public RuntimeHook create() {
		return new RuntimeHook() {
			private final Logger LOGGER = LoggerFactory.getLogger(RuntimeHook.class);

			public boolean beforeFeature(FeatureRuntime fr) {
				FeatureData fd = new FeatureData(fr.featureCall.feature.getResource().getRelativePath());
				calls.add(fd);
				return true;
			}

			public void afterFeature(FeatureRuntime fr) {
				calls.remove(calls.size() - 1);
			}

			public boolean beforeScenario(ScenarioRuntime sr) {
				FeatureData fd = calls.get(calls.size() - 1);
				ScenariosData scenariosData = new ScenariosData();
				fd.scenarios.add(scenariosData);

				LOGGER.info("Feature : " + fd.featureFileName + " #Scenario STARTED : "
						+ sr.getScenarioInfo().get("scenarioName"));
				return true;
			}

			public void afterScenario(ScenarioRuntime sr) {
				FeatureData fd = calls.get(calls.size() - 1);
				LOGGER.info("Feature : " + fd.featureFileName + " #Scenario FINISHED : "
						+ sr.getScenarioInfo().get("scenarioName"));
				fd.scenarios.remove(fd.scenarios.size() - 1);
			}

			public boolean beforeStep(Step step, ScenarioRuntime sr) {
				FeatureData fd = calls.get(calls.size() - 1);
				ScenariosData scenariosData = fd.scenarios.get(fd.scenarios.size() - 1);
				int scenarioNumber = fd.scenarios.size() - 1;
				String str = step.isBackground() ? "BACKGROUND STEP" : "STEP";
				//LOGGER.info("Feature : " + fd.featureFileName + " Scenario-" + scenarioNumber + " : " + str + " "
					//	+ ++scenariosData.stepCount + " : " + step);
				LOGGER.info("Step "+ ++scenariosData.stepCount+ " : "+step +" Feature : " + fd.featureFileName + " Scenario-" + scenarioNumber  );
				return true;
			}
		};
	}

}
