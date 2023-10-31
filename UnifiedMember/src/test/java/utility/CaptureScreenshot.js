function fnScreenshot() {
	karate.log('After Scenario called in Capture Screenshot function');
	var info = karate.info;
	karate.log('---------Karate Info Message-----------'+karate.info)
		if (info.errorMessage) {
		utils.captureScreenOnFailure();
	    }
		utils.closeBrowser();
}