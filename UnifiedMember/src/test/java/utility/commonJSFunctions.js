function fn(functionName,paramList) {

	/*This function checks if an element is populated in dropdown or not
	functionName = getOptionPath
	paramList[0] = locator name from OR file 
	paramList[1] = dropDown value 
	*/
	
	if(functionName=='sleep')
	{
      for(i = 0; i <= paramList; i++)
      {
      java.lang.Thread.sleep(1*1000);
      karate.log(i);
      }
     }
}
