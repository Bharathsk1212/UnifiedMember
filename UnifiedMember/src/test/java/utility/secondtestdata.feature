Feature: Contains all test data for all possible payment types

@Delivery
  Scenario Outline: Voucher wesell order data creation for processing ecom refunds(processRefund & fullRefund) for Delivery
    * def dbUtils = Java.type('utility.DbUtils')
    * def constant =  Java.type('utility.Constants')
    * def instance = Java.type('utility.CommonUtils')
    * def config = {}
    * def db1 = new dbUtils(config,CeXdbName)
    * def webOrder = instance.getObjects(<webOrder>)
    * print webOrder
    * def sellP = instance.getObjects("SP_VoucherWebOrder")
    * def totalCost = instance.getObjects("CeXVoucherValue")
    * def delivryCharge = instance.getObjects("DC_voucherWebOrder")
    * def callCron = Java.type('utility.DbUtils')
    * def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    * def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    * def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    * def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    * def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    * def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    * def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    Given url oauth1 +'.'+ <country> + oauth2
    And path '/token'
    And form field client_id = 'dash'
    And form field client_secret = 'cexsecret'
    And form field grant_type = 'client_credentials'
    When method post
    Then status 200
    And def authToken = response.access_token
    Given url url +'.'+ <country> + url1
    And path '/wesellorders/' + webOrder + '/import'
    And params {accessToken: '#(authToken)',branchKey:'WZ'}
    When method get
    Then status 200

    Examples: 
      | webOrder                               | country                   |
      | "CWRDesktopPayPalOrderDelivery"        | constant.countryUnderTest |
      | "CWRDesktopMixedPayPalOrderDelivery"   | constant.countryUnderTest |
      | "CWRDesktopVoucherOrderDelivery"       | constant.countryUnderTest |
      | "CWRDesktopWorldPayOrderDelivery"      | constant.countryUnderTest |
      | "CWRDesktopMixedWorldPayOrderDelivery" | constant.countryUnderTest |

  @CNC
  Scenario Outline: Voucher wesell order data creation for processing ecom refunds(processRefund & fullRefund) for Click&Collect
    * def dbUtils = Java.type('utility.DbUtils')
    * def constant =  Java.type('utility.Constants')
    * def instance = Java.type('utility.CommonUtils')
    * def config = {}
    * def db1 = new dbUtils(config,CeXdbName)
    * def webOrder = instance.getObjects(<webOrder>)
    * print webOrder
    * def sellP = instance.getObjects("SP_VoucherWebOrder")
    * def totalCost = instance.getObjects("CeXVoucherValue")
    * def delivryCharge = instance.getObjects("DC_voucherWebOrder")
    * def callCron = Java.type('utility.DbUtils')
    * def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    * def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    * def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    * def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    * def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    * def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    * def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    Given url oauth1 +'.'+ <country> + oauth2
    And path '/token'
    And form field client_id = 'dash'
    And form field client_secret = 'cexsecret'
    And form field grant_type = 'client_credentials'
    When method post
    Then status 200
    And def authToken = response.access_token
    Given url url +'.'+ <country> + url1
    And path '/wesellorders/' + webOrder + '/import'
    And params {accessToken: '#(authToken)',branchKey:'WZ'}
    When method get
    Then status 200

    Examples: 
      | webOrder                          | country                   |
      | "CWRDesktopPayPalOrderCNC"        | constant.countryUnderTest |
      | "CWRDesktopMixedPayPalOrderCNC"   | constant.countryUnderTest |
      | "CWRDesktopVoucherOrderCNC"       | constant.countryUnderTest |
      | "CWRDesktopWorldPayOrderCNC"      | constant.countryUnderTest |
      | "CWRDesktopMixedWorldPayOrderCNC" | constant.countryUnderTest |
@CNCDelivery
  Scenario Outline: Voucher wesell order data creation for processing ecom refunds(processRefund & fullRefund) for Delivery & Click&Collect
    * def dbUtils = Java.type('utility.DbUtils')
    * def constant =  Java.type('utility.Constants')
    * def instance = Java.type('utility.CommonUtils')
    * def config = {}
    * def db1 = new dbUtils(config,CeXdbName)
    * def webOrder = instance.getObjects(<webOrder>)
    * print webOrder
    * def sellP = instance.getObjects("SP_VoucherWebOrder")
    * def totalCost = instance.getObjects("CeXVoucherValue")
    * def delivryCharge = instance.getObjects("DC_voucherWebOrder")
    * def callCron = Java.type('utility.DbUtils')
    * def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    * def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    * def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    * def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    * def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    * def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    * def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    * def sleep =
      """
      function(seconds){
      for(i = 0; i <= seconds; i++)
      {
      java.lang.Thread.sleep(1*500);
      karate.log(i);
      }
      }
      """
    * def temp = sleep(100)
    * def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    Given url oauth1 +'.'+ <country> + oauth2
    And path '/token'
    And form field client_id = 'dash'
    And form field client_secret = 'cexsecret'
    And form field grant_type = 'client_credentials'
    When method post
    Then status 200
    And def authToken = response.access_token
    Given url url +'.'+ <country> + url1
    And path '/wesellorders/' + webOrder + '/import'
    And params {accessToken: '#(authToken)',branchKey:'WZ'}
    When method get
    Then status 200

    Examples: 
      | webOrder                                   | country                   |
      | "CWRDesktopMixedPayPalOrderCNC&Delivery"   | constant.countryUnderTest |
      | "CWRDesktopMixedPayPalOrderCNC&Delivery"   | constant.countryUnderTest |
      | "CWRDesktopVoucherOrderCNC&Delivery"       | constant.countryUnderTest |
      | "CWRDesktopWorldPayOrderCNC&Delivery"      | constant.countryUnderTest |
      | "CWRDesktopMixedWorldPayOrderCNC&Delivery" | constant.countryUnderTest |
  #Scenario Outline: WorldPay wesell order data creation for processing ecom refunds(processRefund & fullRefund)
    #* def dbUtils = Java.type('utility.DbUtils')
    #* def constant =  Java.type('utility.Constants')
    #* def instance = Java.type('utility.CommonUtils')
    #* def config = {}
    #* def db1 = new dbUtils(config,CeXdbName)
    #* def response = call read('classpath:CeX/orders/UK/Voucher.feature@getVoucherWebOrder')
    #* def webOrder = dbUtils.getvalueFrompropFile("voucherWesellOrder")
    #* def webOrder = instance.getObjects("WorldPayWeSellOrder")
    #* print webOrder
    #* def sellP = dbUtils.getvalueFrompropFile("SP_VoucherWebOrder")
    #* def sellP = instance.getObjects("SP_WorldPayWebOrder")
    #* def totalCost = instance.getObjects("worldPayCost")
    #* def delivryCharge = instance.getObjects("DC_WorldPayWebOrder")
    #* def callCron = Java.type('utility.DbUtils')
    #* def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    #* def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    #* def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    #* def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    #* def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    #* def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    #* def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    #Given url oauth1 +'.'+ <country> + oauth2
    #And path '/token'
    #And form field client_id = 'dash'
    #And form field client_secret = 'cexsecret'
    #And form field grant_type = 'client_credentials'
    #When method post
    #Then status 200
    #And def authToken = response.access_token
    #Given url url +'.'+ <country> + url1
    #And path '/wesellorders/' + webOrder + '/import'
    #And params {accessToken: '#(authToken)',branchKey:'WZ'}
    #When method get
    #Then status 200
#
    #Examples: 
      #| country                   |
      #| constant.countryUnderTest |
#
  ###########################################################################################
  #Scenario Outline: Mixed (Voucher+WorldPay) wesell order data creation for processing ecom refunds(processRefund & fullRefund)
    #* def dbUtils = Java.type('utility.DbUtils')
    #* def constant =  Java.type('utility.Constants')
    #* def instance = Java.type('utility.CommonUtils')
    #* def config = {}
    #* def db1 = new dbUtils(config,CeXdbName)
    #* def webOrder = instance.getObjects("CeX_Voucher_WorldPayOrder")
    #* print webOrder
    #* def sellP = instance.getObjects("SP_Voucher_WorldPayWebOrder")
    #* def totalCost = instance.getObjects("CeXVoucherWorldpayValue")
    #* def delivryCharge = instance.getObjects("DC_Voucher_WorldPayWebOrder")
    #* def callCron = Java.type('utility.DbUtils')
    #* def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    #* def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    #* def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    #* def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    #* def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    #* def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    #* def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    #Given url oauth1 +'.'+ <country> + oauth2
    #And path '/token'
    #And form field client_id = 'dash'
    #And form field client_secret = 'cexsecret'
    #And form field grant_type = 'client_credentials'
    #When method post
    #Then status 200
    #And def authToken = response.access_token
    #Given url url +'.'+ <country> + url1
    #And path '/wesellorders/' + webOrder + '/import'
    #And params {accessToken: '#(authToken)',branchKey:'WZ'}
    #When method get
    #Then status 200
#
    #Examples: 
      #| country                   |
      #| constant.countryUnderTest |
#
  #############################################################################################################################
  #Scenario Outline: PayPal wesell order data creation for processing ecom refunds(processRefund & fullRefund)
    #* def dbUtils = Java.type('utility.DbUtils')
    #* def constant =  Java.type('utility.Constants')
    #* def instance = Java.type('utility.CommonUtils')
    #* def config = {}
    #* def db1 = new dbUtils(config,CeXdbName)
    #* def webOrder = instance.getObjects(<webOrder>)
    #* print webOrder
    #* def sellP = instance.getObjects("SP_PayPalWebOrder")
    #* def totalCost = instance.getObjects("PayPalCost")
    #* def delivryCharge = instance.getObjects("DC_PayPalWebOrder")
    #* def callCron = Java.type('utility.DbUtils')
    #* def run = callCron.callCronjobs('create_picklist.php',' uk ','cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatepicklist = db1.update('update picklist set Picked = 1, PickDateTime=(select now()),PickStarted=1 where WebOrderNumber='+ webOrder,'cex')
    #* def pick = db1.readValue('select picklistid from picklist where WebOrderNumber = '+webOrder+' order by webordernumber desc limit 1')
    #* def updatepicklistDtals = db1.update('update picklistdetails set found = 1, notfoundreason= NULL,reassigned= 0 where picklistid ='+ pick,'cex')
    #* def WZimportOrder = db1.update('update picklist set PickingBranchID=162 where WebOrderNumber='+ webOrder ,'cex')
    #* def run2 = callCron.callCronjobs('advance_picklist.php',' uk ','cex')
    #* def orderStatus = db1.update('update weborder set orderstatus=9 where WebOrderNumber='+ webOrder ,'cex')
    #* def updatePayment = db1.update('update weborderpayment set SettlementAmount = '+totalCost+', SettlementDatetime = (select now()) where WebOrderNumber='+ webOrder ,'cex')
    #* def sleep =
      #"""
      #function(seconds){
      #for(i = 0; i <= seconds; i++)
      #{
      #java.lang.Thread.sleep(1*500);
      #karate.log(i);
      #}
      #}
      #"""
    #* def temp = sleep(100)
    #* def updatePicklist = db1.update('update picklistdetails set AmountSettled = '+sellP+', DeliveryChargeSettled ='+delivryCharge+'  where picklistid='+ pick ,'cex')
    #Given url oauth1 +'.'+ <country> + oauth2
    #And path '/token'
    #And form field client_id = 'dash'
    #And form field client_secret = 'cexsecret'
    #And form field grant_type = 'client_credentials'
    #When method post
    #Then status 200
    #And def authToken = response.access_token
    #Given url url +'.'+ <country> + url1
    #And path '/wesellorders/' + webOrder + '/import'
    #And params {accessToken: '#(authToken)',branchKey:'WZ'}
    #When method get
    #Then status 200
#
    #Examples: 
      #| webOrder                | country                   |
      #| "CWRDesktopPayPalOrder" | constant.countryUnderTest |
      #| "CWRMobilePayPalOrder"  | constant.countryUnderTest |
