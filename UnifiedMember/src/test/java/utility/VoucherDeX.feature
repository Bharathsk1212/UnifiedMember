Feature: Create voucher for multiple purposes in WSS for DeX.

  Scenario Outline: Create Voucher with different voucherType
    #Set up database connection string
    * configure report = { showLog: true, showAllSteps: false }
    * def config = { username: '#(DeXDBUsername)', password: '#(DeXDBPassword)', url: '#(NatDBURL)', driverClassName: '#(DBDriverClassName)'}
    * def DbUtils = Java.type('utility.DbUtils')
    * def getUtil = Java.type('utility.CommonUtils')
    * def constant =  Java.type('utility.Constants')
    * def db = new DbUtils(config)
    * def orderResponse = db.readRow(constant.getStoreOrders)
    * print "orderResponse db : ",orderResponse
    * def BK = orderResponse.BranchKey
    * def orderid = orderResponse.order_id
    * def orderidresponse = BK + orderid
    * print "orderidresponse is ",orderidresponse
    * def getDAT = Java.type('utility.DbUtils')
    * def getVaucherVal = getDAT.getvalueFrompropFile("DeXVoucherValue")
    * print getVaucherVal
    * string storeCartCost = DbUtils.storevalueInpropFile("DeXVoucherValue",getVaucherVal)
    * def response = call read('classpath:cexUtility/reuseTemplate/oauthtoken.feature')  {grant_type:'#(GrantTypeAsPassword)',username:'#(KiwiUsername)',password:'#(KiwiPassword)',client_id:'#(ClientId)',client_secret:'#(ClientSecret)',scope:'vouchers'}
    * def token = response.response.access_token
    Given url service
    Given path 'voucherservice/v1/Vouchers'
    * header Authorization = 'Bearer ' + token
    * request {orderNumber: <orderNumber>,voucherValue: <voucherValue>,voucherType: 1}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    * def instance = Java.type('utility.CommonUtils')
    * def wait = instance.hardWait(2)
    * def storeOrderid = DbUtils.storeCommonValueInpropFile("unusedOrder",orderid)
    * def getOrder = getDAT.getvalueFrompropFile("unusedOrder")
    * print getOrder
    * string storeVoucher = DbUtils.storevalueInpropFile("UnusedVoucher",orderidresponse)
    * def getVaucher = getDAT.getvalueFrompropFile("UnusedVoucher")
    * print getVaucher

    Examples: 
      | orderNumber          | voucherValue     | status |
      | '#(orderidresponse)' | #(getVaucherVal) |    200 |
