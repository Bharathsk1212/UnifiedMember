/**
 * 
 */
package utility;

/**
 * @author RG,Mona
 */
public class Constants {

	/**
	 * /////////////////////////Backend constants for Appsetting/////////////////////////////
	 */
	public static String memberVerifyEmailKey = "isMyAccountEmailVerificationEnabled";
	public static String appsettingKey = "dlk89Ji3uLn76iOpO";
	public static String getBFKey = "isBoxesFlagsEnabled";
	public static String wesell = "wesellBasketItemQuantityLimit";
	public static String webuy = "webuyBasketItemQuantityLimit";
	public static String wesellminbasket = "wesellMinimumBasketAmount";
	public static String disableSignUpEmailVerification = "isSignUpEmailVerificationEnabled";
	public static String getMemberGDPREnabledKey = "isMemberGDPREnabled";
	public static String minCartValue = "minimumBuyCartValuePerItem";
	public static String getDeXintrntalKey = "isInternationalDeliveryEnabled";
	public static String SignUpEmailVerificationkey = "isSignUpEmailVerificationEnabled";
	public static String NewEmailVerification = "isMemberEmailVerificationEnabled";
	public static String maxAttributeCount = "inStockAlertMaxAttributeCount";
	public static String maxListItemCount = "instockAlertMaxListItemCount";
	public static String ClickAndCollect = "isClickAndCollectEnabled";
	public static String reCaptcha = "isReCaptchaEnabled";
	public static String reCaptchaOnLogin = "isReCaptchaOnLoginEnabled";
	public static String basketSharing = "isBasketSharingEnabled";
	public static String imageNames = "isStaticImageHostEnabled";
	public static String PIIKey = "x7[v3Pk=D";
	public static String getIDProofKey = "isProofOfIdRequired";
	public static String pwForEmailChange = "isPasswordRequiredForEmailChange";
	public static String flagWebuyMinimumBasketCashAmount = "webuyMinimumBasketCashAmount";
	public static String paypalV2ApiEnabled = "isPaypalV2ApiEnabled";
	public static String AlgoliaAPIKey = "c5856d9f0815505af3f86a33f2c26935";
	public static String AlgoliaApplicationId = "JIE6P1H3VT";
	public static String OldAlgoliaURL = "https://jie6p1h3vt-1.algolianet.com";
	public static String AlgoliaURL = "https://jie6p1h3vt-dsn.algolia.net";
	public static String separateBilling = "isSeparateBillingAndDeliveryAddressesEnabled";
	public static String RedeemVoucher= "isKiwiServiceRedeemVoucherEnabled";
	public static String CreateVoucher= "isKiwiServiceCreateVoucherEnabled";



	/**
	 * /////////////////////////Backend constants for
	 * CeX/////////////////////////////
	 */
	public static int UKmemberId = 1118689;
	// 1117364;
	public static int UKmemberId2 = 11135326;

	public static String CeXGetExistingMemberAddressId ="select address_id from address_enc where InUse='1' and account_id= "+ UKmemberId + " order by date_last_modified desc limit 1";
	public static String separateBillingEnabled = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String separateBillingDisabled = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String setEmailVerified ="update members_enc set IsEmailValid = 1 where id=";
	public static String getExpireTokenListWithActive = "SELECT Token FROM MemberPasswordResetToken WHERE Expiry < CURRENT_TIMESTAMP and Active = 1  ORDER BY Expiry ASC LIMIT 1";
	public static String setSqlSafeUpdates = "SET SQL_SAFE_UPDATES=0";
	public static String updateActiveStateOfToken = "update MemberPasswordResetToken set Active = 0 where MemberId=";
	public static String getInActiveToken = "select Token from MemberPasswordResetToken where Active = 0 AND MemberId=";
	public static String getPasswordToken = "select Token from MemberPasswordResetToken where MemberId=";
	public static String getDiscontinuedBoxId = "SELECT box_id FROM boxes where discontinued=1 ORDER BY box_id DESC LIMIT 1";
	public static String getMember = "select * from members_enc where id=";
	public static String getDecrptEmail = "select fn_decrypt(email," + "'" + PIIKey + "'"
			+ ") as emailaddres from members_enc where id=";
	public static String updateMemberTble = "update members_enc set IsEmailValid= 1 where id=";
	public static String selectCookie = "select cookieid from cart where itemId =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'" + " and cookieId = " + "'"
			+ DbUtils.getvalueFrompropFile("CeXCookie") + "'" + " ORDER BY date_added DESC LIMIT 1";
	public static String updateStockToZero = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String updateStock = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String updatememberVerifyEmail = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + memberVerifyEmailKey + "'" + " and platformId=0";
	public static String updateBoxFlag = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + getBFKey + "'" + " and platformId=0";
	public static String disableBoxFlag = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + getBFKey + "'" + " and platformId=0";

	public static String updatIdProof = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + getIDProofKey + "'" + " and platformId=0";
	public static String passwordRequiredForEmailChange = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + pwForEmailChange + "'" + " and platformId=0";
	public static String passwordRequiredForEmailChangeEnable = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + pwForEmailChange + "'" + " and platformId=0";
	public static String getPaymentRef = "select PaymentReference from weborderpayment where webordernumber=";
	public static String getorder = "select weborder.WebOrderNumber from weborder inner join  weborderdetail  on weborder.webordernumber = weborderdetail.webordernumber inner join weborderdeliverycharge on weborderdetail.webordernumber = weborderdeliverycharge.webordernumber where customerID = "
			+ UKmemberId + " order by weborderdeliverycharge.WebOrderNumber desc limit 1";
	public static String StockZero = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String getWeborderNum = "select * from weborderpayment inner join allocation on weborderpayment.webordernumber = allocation.orderid where webordernumber= ";
	public static String cancelMember = "update members_enc set cancelled =1 where id=";
	public static String nullifyCancelMember = "update members_enc set cancelled =0 where id=";
	public static String suspendMember = "update members_enc set suspended =1 where id=";
	public static String nullifysuspendMember = "update members_enc set suspended =0 where id=";
	public static String banMember = "update members_enc set banned =1 where id=";
	public static String nullifybanMember = "update members_enc set banned =0 where id=";
	public static String updateStock_adult = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =\"000732276162\" and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String deleteCart = "delete from cart where itemId =";
	public static String deleteCeXCart = "delete from cart where cookieId =";
	public static String updateAllBoxFlag = "UPDATE boxes SET BuyAllowed =1 , SaleAllowed =1 , WebBuyAllowed =1 , WebSaleAllowed =1  WHERE Box_id ="
			+ "'" + DbUtils.getvalueFrompropFile("BoxId") + "'" + "";
	public static String sellBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` = " + "'" + wesell + "'" + "and platformid=0";
	public static String buyBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` = " + "'" + webuy + "'" + " and platformid=0";
	public static String discontinuedItem = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'" + " ";
	public static String continuedItem = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'" + " ";
	public static String getProcessedOrder = "select picklist.webordernumber from picklist inner join weborder on picklist.WebOrderNumber=weborder.WebOrderNumber where weborder.PaymentType=8 and picklist.webordernumber is not null order by PickDateTime  desc limit  1;";
	public static String updateNormalVoucher = "update voucher set redeemed =0 , cancelled=0 where branchkey ='WZ' and orderid =1019609";
	public static String updateVoucheValue = "update voucher set value = "
			+ DbUtils.getValueFromPropertiesFile("weSellVoucherValue") + " where orderid=1019609 and  branchkey ='WZ'";
	public static String CeXVoucherData = "{\"WZ1019609\":{\"voucherNo\":\"WZ1019609\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATVoucherValue")
			+ "\",\"voucherIssueDate\":\"2020/10/01 12:28:16\",\"voucherExpiryDate\":\"3020/10/13\"}}";
	public static String InvalidCeXVoucher = "{\"WZ1019609\":{\"voucherNo\":\"WZ1019609\",\"voucherValue\":\" \",\"voucherIssueDate\":\"2020/10/01 12:28:16\",\"voucherExpiryDate\":\"3020/10/13\"}}";
	public static String CeXUATVoucher = "WZ1019609";
	public static String nullifyAllMemberFlag = "update members_enc SET suspended =0 , cancelled =0 , banned =0  where id=";
	public static String getDecrptAccountNo = "select fn_decrypt(AccountNumber," + "'" + PIIKey + "'"
			+ ") as AccNo from bacsdetail_enc where MemberID=";
	public static String WorldPayRedirectionUrl = "&country=GB&language=en&successURL=http://uk.cwr.dev.webuy.com/buy&failureURL=http://uk.cwr.dev.webuy.com/buy&pendingURL=http://uk.cwr.dev.webuy.com/buy&cancelURL=http://uk.cwr.dev.webuy.com/buy";
	public static String updateMinCeXBasketAmount = "update appsettings set Value= AES_ENCRYPT(15," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + " and platformId=0";
	public static String updateMinCeXBasketAmount1 = "update appsettings set Value= AES_ENCRYPT(1," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + " and platformId=0";
	public static String updateCeXBoxPrice = "update boxprice set sellprice=1 where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("BoxId") + "'" + " ";
	public static String updateStockSpecific = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("SpecificBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String nullifyMinCeXBasketAmount = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + "  and platformId=0";
	public static String deleteCeXVoucherlogs = "delete from voucherpaymentblock where memberid =";
	public static String deleteallocation = "delete from allocation";
	public static String updateWrongCeXVoucheValue = "update voucher set value = "
			+ DbUtils.getvalueFrompropFile("UATWrongCeXVoucherValue") + "  where orderid= 1019609 and  branchkey ='WZ'";
	public static String nullifyCeXBoxSellPrice = "update boxprice set sellprice = "
			+ DbUtils.getvalueFrompropFile("KidXBoxIdsellPrice") + " where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'";
	public static String wrongpaymentCeXVoucher = "{\"WZ1019609\":{\"voucherNo\":\"WZ1019609\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATWrongCeXVoucherValue")
			+ "\",\"voucherIssueDate\":\"2020/10/01 12:28:16\",\"voucherExpiryDate\":\"3020/10/13\"}}";
	public static String deleteAccountNumberAndDetails = "delete from bacsdetail_enc where MemberID =";
	public static String disableCeXIntrntionalFlag = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + getDeXintrntalKey + "'" + " and platformId=0";
	public static String disableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + " and platformId=0;";
	public static String valueMemberGDPR = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings";
	public static String enableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + " and platformId=0";
	public static String selectMember = "SELECT MemberId FROM memberlegalagreementhistory order by MemberId desc limit 1 ;";
	public static String updateTermVersionID = "UPDATE memberlegalagreementhistory SET TermsVersionId=3 where MemberId = "
			+ DbUtils.getvalueFrompropFile("DBmemberID") + " ";
	public static String disableSignupEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + disableSignUpEmailVerification + "'"
			+ " and platformId=0";
	public static String unblockmember_voucher = "delete from voucherpaymentblock where memberid=";
	public static String readWebOrder = "select distinct weborder.WebOrderNumber from weborder inner join weborderdetail  on weborder.webordernumber = weborderdetail.webordernumber inner join weborderdeliverycharge on weborderdetail.webordernumber = weborderdeliverycharge.webordernumber where customerID = "
			+ UKmemberId + " order by weborderdeliverycharge.WebOrderNumber desc limit 1;";
	public static String readProcessWebOrder = "select distinct webordernumber from weborderpayment inner join allocation on weborderpayment.webordernumber = allocation.orderid where webordernumber=";
	public static String updatePayment = "update payment_type set in_use=1 and ok_for_sell_to_cex=1 where payment_id=";
	public static String updtePaymentPlatform = "update paymenttypeplatforms set inuse=1 , availablefortestdrop = 1 where PaymentID =";
	public static String takeWebuyBoxId = "update boxes set discontinued = 0 where box_id =";
	public static String disableSignUpEmailVerify = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + disableSignUpEmailVerification + "'" + " and platformId=0";
	public static String getToken = "select Token from verifymemberemailtoken where MemberId =";
	public static String enableNewEmailVerification = "update appsettings set Value= AES_ENCRYPT(1," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + NewEmailVerification + "'" + " and platformId=0";
	public static String disableNewEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + NewEmailVerification + "'" + " and platformId=0";
	public static String enablememberVerifyEmail = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + memberVerifyEmailKey + "'" + " and platformId=0";
	public static String disablememberVerifyEmail = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + memberVerifyEmailKey + "'" + " and platformId=0";
	public static String enableSignUpEmailVerification = "update appsettings set Value= AES_ENCRYPT(1," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + SignUpEmailVerificationkey + "'" + " and platformId=0";
	public static String deletefavbox = "DELETE from memberfavouriteboxes where MemberId=";
	public static String CeXupdateSellBasketMaxQnty = "update appsettings set Value= AES_ENCRYPT(2," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesell + "'" + " and platformId=0";
	public static String DeXupdateSellBasketMinQnty = "update appsettings set Value= AES_ENCRYPT(1," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesell + "'" + " and platformId=0";
	public static String CeXupdateBuyBasketMaxQnty = "update appsettings set Value= AES_ENCRYPT(200," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + webuy + "'" + " and platformId=0";
	public static String KidXupdateSellBasketMinQnty = "update appsettings set Value= AES_ENCRYPT(2," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesell + "'" + " and platformId=0";
	public static String KidXupdateBuyBasketMaxQnty = "update appsettings set Value= AES_ENCRYPT(200," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + webuy + "'" + " and platformId=0";
	public static String deleteInstockalert = "DELETE FROM memberinstockalert where MemberId=";
	public static String AlertId = "select AlertId from memberinstockalert where MemberId =";
	public static String maxAlertAttributeCount5 = "update appsettings set Value= AES_ENCRYPT(5," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + maxAttributeCount + "'" + " and platformId=0";
	public static String maxAlertAttributeCount12 = "update appsettings set Value= AES_ENCRYPT(12," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + maxAttributeCount + "'" + " and platformId=0";
	public static String maxAlertListItemCount2 = "update appsettings set Value= AES_ENCRYPT(2," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + maxListItemCount + "'" + " and platformId=0";
	public static String maxAlertListItemCount3 = "update appsettings set Value= AES_ENCRYPT(3," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + maxListItemCount + "'" + " and platformId=0";
	public static String enableClickCollect = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + ClickAndCollect + "'" + " and platformId=0";
	public static String enableReCaptcha = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + reCaptcha + "'" + " and platformId=0";
	public static String disableReCaptcha = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + reCaptcha + "'" + " and platformId=0";
	public static String updateStockBox2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String deleteCeXVoucherfailurelogs = "delete from voucherpaymentfailurelog where memberid =";
	public static String disableBasketSharing = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + basketSharing + "'" + " and platformId=0";
	public static String enableBasketSharing = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + basketSharing + "'" + " and platformId=0";
	public static String getCeXAppsettings = "SELECT distinct `key` , CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey
			+ "'" + ") as char) as value from appsettings  where  platformid in (0,";
	public static String iosAppVersion = "SELECT APPVERSION FROM appsettings WHERE PLATFORMID=6 AND APPENVIRONMENT='live' ORDER BY APPVERSION DESC LIMIT 1";
	public static String androidAppVer = "SELECT APPVERSION FROM appsettings WHERE PLATFORMID=5 AND APPENVIRONMENT='live' ORDER BY APPVERSION DESC LIMIT 1";
	public static String deleteWallet="DELETE FROM membercryptocoinwallet where MemberId= ";
	public static String enableReCaptchaOnLogin = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + reCaptchaOnLogin + "'" + " and platformId=0";
	public static String disableReCaptchaOnLogin = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + reCaptchaOnLogin + "'" + " and platformId=0";
	public static String enableimageNames = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + imageNames + "'" + " and platformId=0";
	public static String disableimageNames = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + imageNames + "'" + " and platformId=0";
	public static String webuyMinimumBasketCashAmount = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + flagWebuyMinimumBasketCashAmount + "'" + " and platformId=0";
	public static String fivewebuyMinimumBasketCashAmount = "update appsettings set Value= AES_ENCRYPT(5," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + flagWebuyMinimumBasketCashAmount + "'" + " and platformId=0";
	public static String minCartValueLimit = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + minCartValue + "'" + " and platformId=0";
	public static String revertminCartValueLimit = "update appsettings set Value= AES_ENCRYPT(5," + "'" + appsettingKey + "'"
			+ ") where `key` = " + "'" + minCartValue + "'" + " and platformId=0";
	public static String KiwiServiceRedeemVoucher = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + RedeemVoucher + "'" + " and platformId=17";
	public static String KiwiServiceCreateVoucher = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + CreateVoucher + "'" + " and platformId=17";
	/**
	 * /////////////////////////Backend constants for
	 * DeX/////////////////////////////
	 */

	//public static final String DeXdbName = "dex";
	public static String epos2000DeX = "epos2000DeX";
	public static int DeXUKmemberId = 1118128;
	public static int DeXUKmemberId2 = 1118555;
	public static int DeXUKmemberId3 = 1142872;
	public static String separateBillingEnabledforDeX = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String separateBillingDisabledforDeX = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String updateDeXStock_adult = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =\"SDVD11876839\" and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String selectCookieForDeX = "select cookieid from cart where itemId =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'" + " ORDER BY date_added DESC LIMIT 1";
	public static String updateDeXStock = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String updateDeXStockToZero = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String DeXgetOrder = "select weborder.WebOrderNumber from weborder inner join  weborderdetail  on weborder.webordernumber = weborderdetail.webordernumber inner join weborderdeliverycharge on weborderdetail.webordernumber = weborderdeliverycharge.webordernumber where customerID = "
			+ DeXUKmemberId + " order by weborderdeliverycharge.WebOrderNumber desc limit 1";
	public static String updateDeXintrntnalFlag = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + getDeXintrntalKey + "'" + "  and platformId=0";
	public static String getDeliveryCharg = "select deliverycharge from at_dex.wesellcountrydeliverycharge where id =9";
	public static String DeXdiscontinuedItem = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'" + " ";
	public static String DeXcontinuedItem = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'" + " ";
	public static String DeXStockToZero = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String DeXupdateStock = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String DeXupdateStock2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String DeXupdateAllBoxFlag = "UPDATE boxes SET BuyAllowed =0 , SaleAllowed =0 , WebBuyAllowed =0 , WebSaleAllowed =0 WHERE Box_id ="
			+ "'" + DbUtils.getvalueFrompropFile("DeXBoxId") + "'" + "";
	public static String DeXsellBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` = " + "'" + wesell + "'" + "  and platformid=0";
	public static String DeXbuyBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` =  " + "'" + webuy + "'" + "  and platformid=0";
	public static String DeXgetorder = "select weborder.WebOrderNumber from weborder inner join  weborderdetail  on weborder.webordernumber = weborderdetail.webordernumber inner join weborderdeliverycharge on weborderdetail.webordernumber = weborderdeliverycharge.webordernumber where customerID = "
			+ DeXUKmemberId + " order by weborderdeliverycharge.WebOrderNumber desc limit 1";
	public static String StockZeroBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String StockHundredBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("BoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String discontinuedItemBoxID2 = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("BoxId2") + "'" + " ";
	public static String continuedItemBoxID2 = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("BoxId2") + "'" + " ";
	public static String DeXdiscontinuedItemBoxID2 = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId2") + "'" + " ";
	public static String DeXcontinuedItemBoxID2 = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId2") + "'" + " ";
	public static String DeXStockZeroBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String DeXStockHundredBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String updateDeXNormalVoucher = "update voucher set redeemed =0 , cancelled=0 where branchkey ='KD' and orderid =7015";
	public static String updateDeXVoucheValue = "update voucher set value = "
			+ DbUtils.getvalueFrompropFile("UATDeXVoucherValue") + "where orderid=7015 and  branchkey ='KD'";
	public static String deleteDexCart = "delete from cart where cookieId =";
	public static String DeXVoucherData = "{\"KD7015\":{\"voucherNo\":\"KD7015\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATDeXVoucherValue")
			+ "\",\"voucherIssueDate\":\"2017/10/31 10:29:15\",\"voucherExpiryDate\":\"3017/11/07\"}}";
	public static String DeXgetDecrptAccountNo = "select fn_decrypt(AccountNumber," + "'" + PIIKey + "'"
			+ ") as AccNo from bacsdetail_enc where MemberID=";
	public static String DeXnullifyAllMemberFlag = "update members_enc SET suspended =0 , cancelled =0 , banned =0  where id=";
	public static String DeXcancelMember = "update members_enc set cancelled =1 where id=";
	public static String updateWrongDeXVoucheValue = "update voucher set value = "
			+ DbUtils.getvalueFrompropFile("UATWrongDeXVoucherValue") + " where orderid=7015 and  branchkey ='KD'";
	public static String DeXWorldPayRedirectionUrl = "&country=GB&language=en&failureURL=https://uk.uat.designerexchange.com/checkout?paymentState=failure&successURL=https://uk.uat.designerexchange.com/checkout/processworldpaypayment&pendingURL=https://uk.uat.designerexchange.com/checkout?paymentState=pending&cancelURL=https://uk.uat.designerexchange.com/checkout?paymentState=cancelled";
	public static String updateMinDeXBasketAmount = "update appsettings set Value= AES_ENCRYPT(15," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + "and platformId=0";
	public static String updateDeXBoxPrice = "update boxprice set sellprice=1 where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'" + " ";
	public static String updateDeXStockSpecific = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("SpecificBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String nullifyMinDeXBasketAmount = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + " and platformId=0";
	public static String deleteDeXVoucherlogs = "delete from voucherpaymentblock where memberid =";
	public static String nullifyDeXBoxSellPrice = "update boxprice set sellprice = "
			+ DbUtils.getvalueFrompropFile("DeXBoxIdsellPrice") + " where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("DeXBoxId") + "'";
	public static String wrongpaymentDeXVoucher = "{\"KD7015\":{\"voucherNo\":\"KD7015\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATWrongDeXVoucherValue")
			+ "\",\"voucherIssueDate\":\"2017/10/31 10:29:15\",\"voucherExpiryDate\":\"3017/11/07\"}}";
	public static String DeXnullifyCancelMember = "update members_enc set cancelled =0 where id=";
	public static String DeXsuspendMember = "update members_enc set suspended =1 where id=";
	public static String DeXnullifysuspendMember = "update members_enc set suspended =0 where id=";
	public static String DeXbanMember = "update members_enc set banned =1 where id=";
	public static String DeXnullifybanMember = "update members_enc set banned =0 where id=";
	public static String DeXdeleteAccountNumberAndDetails = "delete from bacsdetail_enc where MemberID =";
	public static String InvalidDeXVoucher = "{\"KD7015\":{\"voucherNo\":\"KD7015\",\"voucherValue\":\"\",\"voucherIssueDate\":\"2017/10/31 10:29:15\",\"voucherExpiryDate\":\"3017/11/07\"}}";
	// public static String DeXupdateBoxFlag ="update appsettings set Value=
	// AES_ENCRYPT(0,"+"'"+appsettingKey+"'"+") where `key` =
	// "+DbUtils.getvalueFrompropFile("getBFKey")+" and platformId=0";
	public static String DeXgetMember = "select * from members_enc where id=";
	public static String DeXgetDecrptEmail = "select fn_decrypt(email," + "'" + PIIKey + "'"
			+ ") as emailaddres from members_enc where id=";
	public static String DeXdisableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + "  and platformId=0";
	public static String DeXenableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + "  and platformId=0";
	public static String DeXdisableSignupEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + disableSignUpEmailVerification + "'"
			+ " and platformId=0";
	public static String DeXSignUpEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + SignUpEmailVerificationkey + "'" + " and platformId=0";
	public static String DeXimage = "update boxesonline set productimage = 1";
	public static String DeXupdatesafe = "SET SQL_SAFE_UPDATES = 0";
	public static String disableDeXIntrntionalFlag = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + getDeXintrntalKey + "'" + " and platformId=0";
	public static String enablepaypalV2Api = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + paypalV2ApiEnabled + "'" + " and platformId=0";
	public static String disablepaypalV2Api = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + paypalV2ApiEnabled + "'" + " and platformId=0";
	public static String KlarnaPayLaterOption30Days ="Klarna-Pay later in 30 days";
	public static String KlarnaPayLaterOptionByInterestFree ="Klarna-Monthly financing";
	public static String DeXAddressId ="select address_id from address_enc where InUse='1' and account_id= "+ DeXUKmemberId + " order by date_last_modified desc limit 1";



	/**
	 * /////////////////////////KidX constants/////////////////////////////
	 */
	public static int KidXUKmemberId = 1118352;
	// 1114980; 1118352
	public static int KidXUKmemberId2 = 1118347;
	//public static String KidXdbName = "at_kidx";
	//public static String KidXdbName = "kidxUtility";
	public static String epos2000_kidx = "epos2000_kidx";
	public static String separateBillingEnabledforKidX = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String KidXAddressId ="select address_id from address_enc where InUse='1' and account_id= "+ KidXUKmemberId + " order by date_last_modified desc limit 1";
	public static String separateBillingDisabledforKidX = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + separateBilling + "'" + " and platformId=17";
	public static String KidXdiscontinuedItem = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'" + " ";
	public static String KidXcontinuedItem = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'" + " ";
	public static String KidXStockToZero = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String KidXupdateStock = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String KidXupdateAllBoxFlag = "UPDATE boxes SET BuyAllowed =1 , SaleAllowed =1 , WebBuyAllowed =1 , WebSaleAllowed =1  WHERE Box_id ="
			+ "'" + DbUtils.getvalueFrompropFile("KidXBoxId") + "'" + "";
    public static String disableKidXBasketSharing = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + basketSharing + "'" + " and platformId=0";
	public static String enableKidXBasketSharing = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + basketSharing + "'" + " and platformId=0";
	public static String KidXsellBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` = " + "'" + wesell + "'" + "  and platformid=0";
	public static String KidXbuyBasketMaxQnty = "SELECT CAST(AES_DECRYPT(`value`, " + "'" + appsettingKey + "'"
			+ ") as char) from appsettings  where `key` = " + "'" + webuy + "'" + "  and platformid=0";
	public static String KidXupdateSellBasketMaxQnty = "update appsettings set Value= AES_ENCRYPT(2," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesell + "'" + " and platformId=0";
	public static String KidXgetorder = "select weborder.WebOrderNumber from weborder inner join  weborderdetail  on weborder.webordernumber = weborderdetail.webordernumber inner join weborderdeliverycharge on weborderdetail.webordernumber = weborderdeliverycharge.webordernumber where customerID = "
			+ KidXUKmemberId + " order by weborderdeliverycharge.WebOrderNumber desc limit 1";
	public static String KidXdiscontinuedItemBoxID2 = "UPDATE boxes SET discontinued = 1 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId2") + "'" + " ";
	public static String KidXcontinuedItemBoxID2 = "UPDATE boxes SET discontinued = 0 where Box_id = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId2") + "'" + " ";
	public static String KidXStockZeroBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 0 WHERE boxstock.Boxid = stock.Boxid";
	public static String KidXStockHundredBoxID2 = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId2") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String KidXWorldPayRedirectionUrl = "&successURL=https://uk.uat.kidx.dev/buy/process?webOrderNumber="
			+ DbUtils.getvalueFrompropFile("KidXWorldPayOrder")
			+ "&failureURL=https://uk.uat.kidx.dev/buy?paymentState=failure'+'&pendingURL=https://uk.uat.kidx.dev/buy?paymentState=pending'+'&cancelURL=https://uk.uat.kidx.dev/buy";
	public static String updateWrongKidXVoucheValue = "update voucher set value = "
			+ DbUtils.getvalueFrompropFile("UATWrongKidXVoucherValue") + " where orderid=70 and  branchkey ='UKKXSM'";
	public static String updateKidXVoucheValue = "update voucher set value = "
			+ DbUtils.getvalueFrompropFile("UATKidXVoucherValue") + " where orderid=70 and  branchkey ='UKKXSM'";
	public static String updateKidXNormalVoucher = "update voucher set redeemed =0 , vtype=9 , cancelled=0 where branchkey ='UKKXSM' and orderid =70";
	public static String KidXVoucherData = "{\"UKKXSM70\":{\"voucherNo\":\"UKKXSM70\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATKidXVoucherValue")
			+ "\",\"voucherIssueDate\":\"2020/06/24 09:23:41\",\"voucherExpiryDate\":\"3020/07/01\"}}";
	public static String InvalidKidXVoucher = "{\"UKKXSM70\":{\"voucherNo\":\"UKKXSM70\",\"voucherValue\":\"\",\"voucherIssueDate\":\"2020/06/24 09:23:41\",\"voucherExpiryDate\":\"3020/07/01\"}}";
	public static String nullifyMinKidXBasketAmount = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + "  and platformId=0";
	public static String updateKidXBoxSellPrice = "update boxprice set sellprice=1 where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'" + " ";
	public static String updateKidXStockSpecific = "UPDATE boxstock,(SELECT Boxid FROM boxstock WHERE Boxid =" + "'"
			+ DbUtils.getvalueFrompropFile("KidXSpecificBoxId") + "'"
			+ "  and LocationID IN (SELECT LocationID FROM locnbranchliveones where branchid in (select branchid from branchesonline  where EcomAllowed =1 ))) AS stock SET QuantityOnHand = 100 WHERE boxstock.Boxid = stock.Boxid";
	public static String deleteKidXVoucherlogs = "delete from voucherpaymentblock where memberid =";
	public static String updateMinKidXBasketAmount = "update appsettings set Value= AES_ENCRYPT(15," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + wesellminbasket + "'" + " and platformId=0";
	public static String nullifyKidXBoxSellPrice = "update boxprice set sellprice = "
			+ DbUtils.getvalueFrompropFile("KidXBoxIdsellPrice") + " where boxid = " + "'"
			+ DbUtils.getvalueFrompropFile("KidXBoxId") + "'";
	public static String wrongpaymentKidXVoucher = "{\"UKKXSM70\":{\"voucherNo\":\"UKKXSM70\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATWrongKidXVoucherValue")
			+ "\",\"voucherIssueDate\":\"2020/06/24 09:23:41\",\"voucherExpiryDate\":\"3020/07/01\"}}";
	public static String wrongKidXVoucher = "{\"UKKXSM70\":{\"voucherNo\":\"UKKXSM70\",\"voucherValue\":\""
			+ DbUtils.getvalueFrompropFile("UATKidXInvalidVoucher")
			+ "\",\"voucherIssueDate\":\"2020/06/24 09:23:41\",\"voucherExpiryDate\":\"3020/07/01\"}}";
	public static String KidXnullifyCancelMember = "update members_enc set cancelled =0 where id=";
	public static String KidXsuspendMember = "update members_enc set suspended =1 where id=";
	public static String KidXnullifysuspendMember = "update members_enc set suspended =0 where id=";
	public static String KidXbanMember = "update members_enc set banned =1 where id=";
	public static String KidXnullifybanMember = "update members_enc set banned =0 where id=";
	public static String KidXgetDecrptAccountNo = "select fn_decrypt(AccountNumber," + "'" + PIIKey + "'"
			+ ") as AccNo from bacsdetail_enc where MemberID=";
	public static String KidXnullifyAllMemberFlag = "update members_enc SET suspended =0 , cancelled =0 , banned =0  where id=";
	public static String KidXcancelMember = "update members_enc set cancelled =1 where id=";
	public static String KidXdeleteAccountNumberAndDetails = "delete from bacsdetail_enc where MemberID =";
	public static String KidXgetDecrptEmail = "select fn_decrypt(email," + "'" + PIIKey + "'"
			+ ") as emailaddres from members_enc where id=";
	public static String KidXdisableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(0," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + " and platformId=0";
	public static String KidXenableMemberGDPR = "update appsettings set Value= AES_ENCRYPT(1," + "'" + appsettingKey
			+ "'" + ") where `key` = " + "'" + getMemberGDPREnabledKey + "'" + "  and platformId=0";
	public static String KidXdisableSignupEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + disableSignUpEmailVerification + "'"
			+ "  and platformId=0";
	public static String KidXSignUpEmailVerification = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + SignUpEmailVerificationkey + "'" + " and platformId=0";
	public static String KidXdisableReCaptchaToken = "update appsettings set Value= AES_ENCRYPT(0," + "'"
			+ appsettingKey + "'" + ") where `key` = " + "'" + reCaptcha + "'" + " and platformId=0";

	/**
	 * /////////////////////////creds and utility
	 * constants/////////////////////////////
	 */
	public final static String WindKillDriver = "Taskkill /IM chromedriver.exe /F";
	public final static String windKillBrowser = "Taskkill /IM chrome.exe /F";
	public final static String macKillDriver = "pkill chrome";
	public final static String macKillBrowser = "pkill Google Chrome";
	//public final static String linuxKillDriver = "ps aux | grep chromedriver| awk ' { print $2 } ' | xargs kill -9";
	public final static String linuxKillBrowser = "ps aux | grep chrome | awk ' { print $2 } ' | xargs kill -9";
	public static String cardNo = "4444333322221111";
	public static String holderName = "Test";
	public static String cardExpMonth = "01";
	public static String cardExpYear = "30";
	public static String cardCVVNo = "555";
	public static String RESPONSEFORMAT = "application/json";
	public static String RESPONSE = "response";
	public static Object RANDOM_VALUE = "Random_Value";
	public static String DATA_SPLIT = "\\|";
	public static String OBJECT_SPLIT = "\\!";
	public static String DAT = "data"; //
	public final static String propLoc = "\\src\\test\\java\\utility\\storedData.properties";
	public static final int dbretry = 1;
	public static String browser = "Chrome";
	public static String paypalUsrName = "uk_1317892730_per@webuy.com";
	public static String paypalPswd = "U@TUKPERSONAL";
	public static String paypalPayer = "6DW7926NC7KMQ";
	public static String clientIP = "10.4.252.35";
	public static String uatWSS = "10.204.2.121";
	public static String uatServerUsrName = "ec2-user";
	public static String uatWSSMasterPath = "/home/ec2-user";
	public static String UATmemcacheIP = "10.204.2.86";

	/****************uat pointing****************/
	//	public static int CeXmemCachePort = 11211;
	//	public static int DeXmemCachePort = 11321;
	//	public static int KidXmemCachePort = 11226;

	/****************AT pointing****************/
	//	public static int CeXmemCachePort = 11425; 
	//	public static int DeXmemCachePort = 11426;
	//	public static int KidXmemCachePort = 11427; 

	public static String countryUnderTest = "uk";
	public static String dbUsername = "automation_qa";
	public static String dbPassword = "cex@123";
	public static String getVoucherHashValue = "http://uat-wss2.util.ws/voucherhashkey.php?vouchernumber=";
	public static String getVoucherdata = "select * from Voucher where OrderID=";
	public static String getVoucherdExpiry = "select ExpiryDate from Voucher where OrderID=";
	public static String defaultBrowser = "chrome";
	public static String StoredbName = "epos2000";
	public static String NatStoredbName = "epos2000National";
	public static String sortCode = "000099";
	public static String deleteMember = "delete from members_enc where id =";
	public static String getStoreOrders = "SELECT top 1 T1.BranchKey,T1.order_id FROM orders T1 LEFT JOIN Voucher T2 ON T2.orderid=T1.order_id WHERE T2.orderid IS NULL and T1.processed=0 and T1.Order_Type=1 and T1.BranchKey not in ('HH', 'HQ') and T2.ROrderID IS NULL and T2.RBranchKey IS NULL AND T2.Redeemed IS NULL AND T2.Cancelled IS NULL order by order_date desc";
	public static String DeXgetStoreOrders = "SELECT top 1 T1.BranchKey,T1.order_id FROM orders T1 LEFT JOIN Voucher T2 ON T2.orderid=T1.order_id WHERE T2.orderid IS NULL and T1.processed=0 and T1.Order_Type=1 and T1.BranchKey ='HQ' and T2.ROrderID IS NULL and T2.RBranchKey IS NULL AND T2.Redeemed IS NULL AND T2.Cancelled IS NULL order by order_date desc";
	public static String getNationalOrders = "SELECT top 1 T1.BranchKey,T1.order_id  FROM orders T1 LEFT JOIN Voucher T2 ON T2.orderid=T1.order_id WHERE T2.orderid IS NULL and T1.processed=0 and T1.Order_Type=1 order by order_date desc";
	public static String applePayUrl = "https://apple-pay-gateway-cert.apple.com/paymentservices/startSession";
	public static String msSqldriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String epsoStorDBUrl = "jdbc:sqlserver://10.204.2.224;databaseName=epos2000";
	public static String epsoSUPDBUrl = "jdbc:sqlserver://10.204.2.224;databaseName=epos2000";
	public static String epsoSUPDBUrlKidX = "jdbc:sqlserver://10.204.2.224;databaseName=epos2000_kidx";
	public static String epsoSUPDBUrlDeX = "jdbc:sqlserver://10.204.2.224;databaseName=epos2000_dex";
	public static String StorDBUrl = "jdbc:sqlserver://10.204.2.224;databaseName=";
	public static String UATDBUrl = "jdbc:mysql://10.204.2.26:3306/";
	public static String DeXDBUrl = "jdbc:sqlserver://10.204.2.224;databaseName=Epos2000_dex";
	public static String nationalStorDBUrl = "jdbc:sqlserver://10.204.2.188;databaseName=Epos2000";
	public static String meposDbUrl = "jdbc:sqlserver://10.204.2.34;databaseName=Epos2000";
	public static String epos2000UserName = "automation_qa";
	public static String epos2000Password = "C8A7-dq&[b";
	public static String webDbURL = "jdbc:mysql://10.204.2.26:3306?useSSL=false";
	public static String atwebDbURL = "jdbc:mysql://10.204.2.26:3306/";
	public static String Sqldriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String mySqldriver = "com.mysql.cj.jdbc.Driver";
	public static String webDbUserName = "automation_qa";
	public static String webDbPassword = "cex@123";
	public static String MySQLDbURL = "jdbc:mysql://10.204.2.13?serverTimezone=UTC";
	public static String MySQLUserName = "eposv2";
	public static String MySQLPassword = "Ep0$v2U@t";
	public static String uatCWCMServer = "10.204.2.167";
	public static String getCronFolder= "/var/www/CWCM.git/master/www/docroot/utility_scripts";
	public static String deliveryMethodId = "1";
	public static String CollectionMethodId = "2";
	// public final static String propLoc =
	// "src"+File.separator+File.separator+"test"+File.separator+File.separator+"java"+File.separator+File.separator+"utility"+File.separator+File.separator+"storedData.properties";
	// public static String uatVoucherData =
	// "{\""+DbUtils.getvalueFrompropFile("UnusedVoucher")+"\":{\"voucherNo\":\""+DbUtils.getvalueFrompropFile("UnusedVoucher")+"\",\"voucherValue\":\""+DbUtils.getvalueFrompropFile("UATVoucherValue")+"\",\"voucherIssueDate\":\""+DbUtils.getvalueFrompropFile("issuedate")+"\",\"voucherExpiryDate\":\""+DbUtils.getvalueFrompropFile("ExpiryDate")+"\"}}";
}
