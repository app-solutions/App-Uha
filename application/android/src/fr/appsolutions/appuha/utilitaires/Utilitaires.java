package fr.appsolutions.appuha.utilitaires;



public final class Utilitaires {
	
	
	public static final String TAG = "App-Uha";
	public static String UNIQUEIDENTIFIER;
	public static String TOKEN;
	public static String LOCAL_PLANNING_NAME = "plannings";
	public static String LOCAL_WIDGET_NAME = "widget";
	public static String LOCAL_PLANNING_RAM = null;
	public static int PLANNING_WEEK = 1;
	public static String LOCAL_COMPTE_NAME = "userAccount";
	public static String LOCAL_USERINFOS_NAME = "userInfos";
	public static boolean TIMER;
	
	public static final String VERS = "App-Uha Android - 1.4 (051114AS1)";
	
	//////////////////////////////////////////////////EN MODE DE PRODUCTION & ALPHA/BETA TEST/////////////////////////////////////////////////////
	
	
	// SERVER
	private static final String SRV_URL = "";
	
	//URLs WEB SERVICE
	public static final String SRV_URL_REGISTER = SRV_URL + "DeviceRegister.php";
	public static final String SRV_URL_GET_INFOS =  SRV_URL + "GetUserInfos.php";
	public static final String SRV_URL_USER_UPDATE = SRV_URL + "UpdateUserAccount.php";
	public static final String SRV_URL_SEND_MESSAGE = SRV_URL + "SendNotifications.php";
	public static final String SRV_URL_GET_PLANNING = SRV_URL + "GetPlanning.php";
	
	//GOOGLE CLOUD MESSAGING ID
	public static final String GCM_ID = "";
	
	
	
	

	//////////////////////////////////////////////////EN MODE DE DEVELOPPEMENT//////////////////////////////////////////////////
	
	
	//URLs WEB SERVICE
	//public static final String SRV_URL_REGISTER = "";
	//public static final String SRV_URL_GET_INFOS = "";
	//public static final String SRV_URL_USER_UPDATE = "";
	//public static final String SRV_URL_SEND_MESSAGE = "";
	//public static final String SRV_URL_GET_PLANNING = "";
		
	//GOOGLE CLOUD MESSAGING ID
	//public static final String GCM_ID = "";
	
}
