Feature: Create voucher for multiple purposes in WSS

  @getVoucherNumber
  Scenario Outline: Create Voucher for Voucher wesell weborder
    * configure report = { showLog: true, showAllSteps: false }
    * def config = { username: '#(DBUsername)', password: '#(DBPassword)', url: '#(NatDBURL)', driverClassName: '#(DBDriverClassName)' }
    * def DbUtils = Java.type('utility.DbUtils')
    * def getUtil = Java.type('utility.CommonUtils')
    * def constant =  Java.type('utility.Constants')
    * def db = new DbUtils(config)
    * def orderResponse = db.readRow(constant.getStoreOrders)
    * def BK = orderResponse.BranchKey
    * def orderid = orderResponse.order_id
    * def orderidresponse = BK + orderid
    * print orderidresponse
    * def getDAT = Java.type('utility.DbUtils')
    * def instance = Java.type('utility.CommonUtils')
    * def getVaucherVal = instance.getObjects("CeXVoucherValue")
    * print '-------getVaucherVal: ',getVaucherVal
    * string storeCartCost = instance.storeObjects("CeXVoucherValue",getVaucherVal)
    * def response = call read('classpath:cexUtility/reuseTemplate/oauthtoken.feature')  {grant_type:'#(GrantTypeAsPassword)',username:'#(KiwiUsername)',password:'#(KiwiPassword)',client_id:'#(ClientId)',client_secret:'#(ClientSecret)',scope:'vouchers'}
    * def token = response.response.access_token
    Given url service
    Given path 'voucherservice/v1/Vouchers/headoffice'
    * header Authorization = 'Bearer ' + token
    * request {recipientDetails: <recipientDetails>,value: <voucherValue>,note: <note>,platformId:<platformId>,workstation:<workstation>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    * def resVoucherNumber = response.response.data.voucherNumber
    Then print 'Response Voucher Number is : ',  resVoucherNumber
    * def instance = Java.type('utility.CommonUtils')
    * def wait = instance.hardWait(2)
    * string storeOrderid = instance.storeObjects("unusedOrder",orderid)
    * def getOrder = instance.getObjects("unusedOrder")
    * print getOrder
    * string storeVoucher = instance.storeObjects("UnusedVoucher",resVoucherNumber)
    * def getVaucher = instance.getObjects("UnusedVoucher")
    * print getVaucher
    #activating the above Voucher
    Given url service
    Given path 'voucherservice/v1/Vouchers/'+resVoucherNumber+'/activate'
    * header Authorization = 'Bearer ' + token
    * request {voucherNoteTypeId: <voucherNoteTypeId>,text: <note>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    #asserting the above voucher if it is activated or not
    Given url service
    Given path 'voucherservice/v1/Vouchers/'
    * param voucherNumbers = resVoucherNumber
    * header Authorization = 'Bearer ' + token
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method GET
    * def redeemed = response.response.data.vouchers[0].isRedeemed
    * def cancelled = response.response.data.vouchers[0].isCancelled
    * print redeemed
    * print cancelled
    * print '-------------------------Voucher is now Activated, Unredemed and not Cancelled'

    Examples: 
      | orderNumber          | voucherValue     | status | recipientDetails       | note     | platformId | workstation | voucherNoteTypeId | voucherStatus |
      | '#(orderidresponse)' | #(getVaucherVal) |    200 | 'Test%0ACeX%0AVoucher' | 'Winner' |         10 | 'Portal'    |                 8 | false         |

  ###########################################################################################################
  @getMixedWorldpayOrder
  Scenario Outline: Create Voucher for mixed ( Voucher + Worldpay)
    * configure report = { showLog: true, showAllSteps: false }
    * configure retry = { count: 1, interval: 3000 }
    * def config = { username: '#(DBUsername)', password: '#(DBPassword)', url: '#(NatDBURL)', driverClassName: '#(DBDriverClassName)' }
    * def DbUtils = Java.type('utility.DbUtils')
    * def getUtil = Java.type('utility.CommonUtils')
    * def constant =  Java.type('utility.Constants')
    * def db = new DbUtils(config)
    * def instance = Java.type('utility.CommonUtils')
    * def orderResponse = db.readRow(constant.getStoreOrders)
    * def BK = orderResponse.BranchKey
    * def orderid = orderResponse.order_id
    * def orderidresponse = BK + orderid
    * print orderidresponse
    * def getDAT = Java.type('utility.DbUtils')
    * def getVaucherVal = instance.getObjects("Voucher_WorldpayValue")
    * print getVaucherVal
    * def storeCartCost = instance.storeObjects("Voucher_WorldpayValue",getVaucherVal)
    * def response = call read('classpath:cexUtility/reuseTemplate/oauthtoken.feature')  {grant_type:'#(GrantTypeAsPassword)',username:'#(KiwiUsername)',password:'#(KiwiPassword)',client_id:'#(ClientId)',client_secret:'#(ClientSecret)',scope:'vouchers'}
    * def token = response.response.access_token
    Given url service
    Given path 'voucherservice/v1/Vouchers/headoffice'
    * header Authorization = 'Bearer ' + token
    * request {recipientDetails: <recipientDetails>,value: <voucherValue>,note: <note>,platformId:<platformId>,workstation:<workstation>}
    #* request {orderNumber: <orderNumber>,voucherValue: <voucherValue>,voucherType: 1}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    * def resVoucherNumber = response.response.data.voucherNumber
    Then print 'Response Voucher Number is : ',  resVoucherNumber
    * def instance = Java.type('utility.CommonUtils')
    * def wait = instance.hardWait(2)
    * string storeOrderid = instance.storeObjects("unusedMixedWorldpayVoucherOrder",orderid)
    * def getOrder = instance.getObjects("unusedMixedWorldpayVoucherOrder")
    * print getOrder
    * string storeVoucher = instance.storeObjects("UnusedMixedWorldpayVoucher",resVoucherNumber)
    * def getVaucher = instance.getObjects("UnusedMixedWorldpayVoucher")
    * print getVaucher
    * string storeNewVoucher = DbUtils.storevalueInpropFile("storeNewVoucher",getVaucher)
    #activating the above Voucher
    Given url service
    Given path 'voucherservice/v1/Vouchers/'+resVoucherNumber+'/activate'
    * header Authorization = 'Bearer ' + token
    * request {voucherNoteTypeId: <voucherNoteTypeId>,text: <note>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    #asserting the above voucher if it is activated or not
    Given url service
    Given path 'voucherservice/v1/Vouchers/'
    * param voucherNumbers = resVoucherNumber
    * header Authorization = 'Bearer ' + token
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method GET
    * print response.response.data.vouchers[0].isRedeemed
    * print response.response.data.vouchers[0].isCancelled
    * print '-------------------------Voucher is now Activated, Unredemed and not Cancelled'

    Examples: 
      | orderNumber          | voucherValue     | status | recipientDetails       | note     | platformId | workstation | voucherNoteTypeId | voucherStatus |
      | '#(orderidresponse)' | #(getVaucherVal) |    200 | 'Test%0ACeX%0AVoucher' | 'Winner' |         10 | 'Portal'    |                 8 | false         |

  #######################################################################################################
  @getMixedPayPalOrder
  Scenario Outline: Create Voucher for mixed ( Voucher + PayPal)
    * configure report = { showLog: true, showAllSteps: false }
    * configure retry = { count: 1, interval: 3000 }
    * def config = { username: '#(DBUsername)', password: '#(DBPassword)', url: '#(NatDBURL)', driverClassName: '#(DBDriverClassName)' }
    * def DbUtils = Java.type('utility.DbUtils')
    * def getUtil = Java.type('utility.CommonUtils')
    * def constant =  Java.type('utility.Constants')
    * def db = new DbUtils(config)
    * def orderResponse = db.readRow(constant.getStoreOrders)
    * def BK = orderResponse.BranchKey
    * def orderid = orderResponse.order_id
    * def orderidresponse = BK + orderid
    * print orderidresponse
    * def getDAT = Java.type('utility.DbUtils')
    * def instance = Java.type('utility.CommonUtils')
    * def getVaucherVal = instance.getObjects("CeXVoucherPaypalValue")
    * print getVaucherVal
    * string storeCartCost = instance.storeObjects("CeXVoucherPaypalValue",getVaucherVal)
    * def response = call read('classpath:cexUtility/reuseTemplate/oauthtoken.feature')  {grant_type:'#(GrantTypeAsPassword)',username:'#(KiwiUsername)',password:'#(KiwiPassword)',client_id:'#(ClientId)',client_secret:'#(ClientSecret)',scope:'vouchers'}
    * def token = response.response.access_token
    Given url service
    Given path 'voucherservice/v1/Vouchers/headoffice'
    * header Authorization = 'Bearer ' + token
    * request {recipientDetails: <recipientDetails>,value: <voucherValue>,note: <note>,platformId:<platformId>,workstation:<workstation>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    * def resVoucherNumber = response.response.data.voucherNumber
    Then print 'Response Voucher Number is : ',  resVoucherNumber
    * def instance = Java.type('utility.CommonUtils')
    * def wait = instance.hardWait(2)
    * string storeOrderid = instance.storeObjects("unusedMixedPayPalOrder",orderid)
    * def getOrder = instance.getObjects("unusedMixedPayPalOrder")
    * print getOrder
    * string storeVoucher = instance.storeObjects("UnusedMixedPayPalVoucher",resVoucherNumber)
    * def getVaucher = instance.getObjects("UnusedMixedPayPalVoucher")
    * print getVaucher
    #activating the above Voucher
    Given url service
    Given path 'voucherservice/v1/Vouchers/'+resVoucherNumber+'/activate'
    * header Authorization = 'Bearer ' + token
    * request {voucherNoteTypeId: <voucherNoteTypeId>,text: <note>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    #asserting the above voucher if it is activated or not
    Given url service
    Given path 'voucherservice/v1/Vouchers/'
    * param voucherNumbers = resVoucherNumber
    * header Authorization = 'Bearer ' + token
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method GET
    * print response.response.data.vouchers[0].isRedeemed
    * print response.response.data.vouchers[0].isCancelled
    * print '-------------------------Voucher is now Activated, Unredemed and not Cancelled'

    Examples: 
      | orderNumber          | voucherValue     | status | recipientDetails       | note     | platformId | workstation | voucherNoteTypeId | voucherStatus |
      | '#(orderidresponse)' | #(getVaucherVal) |    200 | 'Test%0ACeX%0AVoucher' | 'Winner' |         10 | 'Portal'    |                 8 | false         |

  #######################################################################################################
  @getVoucherWithOutCaching
  Scenario Outline: Create Voucher with different voucherType
    #Set up database connection string
    * configure report = { showLog: true, showAllSteps: false }
    * def config = { username: '#(DBUsername)', password: '#(DBPassword)', url: '#(NatDBURL)', driverClassName: '#(DBDriverClassName)' }
    * def DbUtils = Java.type('utility.DbUtils')
    * def getUtil = Java.type('utility.CommonUtils')
    * def constant =  Java.type('utility.Constants')
    * def instance = Java.type('utility.CommonUtils')
    * def db = new DbUtils(config)
    * def orderResponse = db.readRow(constant.getStoreOrders)
    * def BK = orderResponse.BranchKey
    * def orderid = orderResponse.order_id
    * def orderidresponse = BK + orderid
    * print "orderidresponse is ",orderidresponse
    * def getDAT = Java.type('utility.DbUtils')
    * def getVaucherVal = getDAT.getvalueFrompropFile("CeXVoucherValue")
    * print '-----------------------------------getVaucherVal: ',getVaucherVal
    * string storeCartCost = DbUtils.storevalueInpropFile("CeXVoucherValue",getVaucherVal)
    * def response = call read('classpath:cexUtility/reuseTemplate/oauthtoken.feature')  {grant_type:'#(GrantTypeAsPassword)',username:'#(KiwiUsername)',password:'#(KiwiPassword)',client_id:'#(ClientId)',client_secret:'#(ClientSecret)',scope:'vouchers'}
    * def token = response.response.access_token
    Given url service
    Given path 'voucherservice/v1/Vouchers/headoffice'
    * header Authorization = 'Bearer ' + token
    * request {recipientDetails: <recipientDetails>,value: <voucherValue>,note: <note>,platformId:<platformId>,workstation:<workstation>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    * def resVoucherNumber = response.response.data.voucherNumber
    Then print 'Response Voucher Number is : ',  resVoucherNumber
    * def instance = Java.type('utility.CommonUtils')
    * def wait = instance.hardWait(2)
    * def storeOrderid = DbUtils.storeCommonValueInpropFile("unusedOrder",orderid)
    * def getOrder = getDAT.getvalueFrompropFile("unusedOrder")
    * print getOrder
    * string storeVoucher = DbUtils.storevalueInpropFile("UnusedVoucher",resVoucherNumber)
    * def getVaucher = getDAT.getvalueFrompropFile("UnusedVoucher")
    * print getVaucher
    #activating the above Voucher
    Given url service
    Given path 'voucherservice/v1/Vouchers/'+resVoucherNumber+'/activate'
    * header Authorization = 'Bearer ' + token
    * request {voucherNoteTypeId: <voucherNoteTypeId>,text: <note>}
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method POST
    #asserting the above voucher if it is activated or not
    Given url service
    Given path 'voucherservice/v1/Vouchers/'
    * param voucherNumbers = resVoucherNumber
    * header Authorization = 'Bearer ' + token
    And header Content-Type = 'application/json-patch+json'
    Then retry until responseStatus == <status>
    When method GET
    * print response.response.data.vouchers[0].isRedeemed
    * print response.response.data.vouchers[0].isCancelled
    * print '-------------------------Voucher is now Activated, Unredemed and not Cancelled'

    Examples: 
      | orderNumber          | voucherValue     | status | recipientDetails       | note     | platformId | workstation | voucherNoteTypeId | voucherStatus |
      | '#(orderidresponse)' | #(getVaucherVal) |    200 | 'Test%0ACeX%0AVoucher' | 'Winner' |         10 | 'Portal'    |                 8 | false         |
