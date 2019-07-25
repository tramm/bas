package com.bookservice.constants;

public class ConstantsUrl {

    //private static String BASE_URL = "http://dev.bas.turnright.tech/";

    private static String BASE_URL = "http://bas.turnright.tech/";

    private static String BASE_URL_USER_API = BASE_URL + "api/v1/user/";

    private static String BASE_URL_VEHICLE_API = BASE_URL + "api/v1/vehicle/";

    private static String BASE_URL_OFFER_API = BASE_URL + "api/v1/offer/";

    private static String BASE_URL_SERVICE_API = BASE_URL + "api/v1/service/";


    public static String VERIFY = BASE_URL + "verify";

    public static String LOGIN = BASE_URL + "login";

    public static String OTP_VERIFY = BASE_URL + "otp_verify";

    public static String CONFIRM_PIN = BASE_URL + "register";


    ////User Api
    public static String USERS = BASE_URL_USER_API + "partners";

    public static String ADD_USERS = BASE_URL_USER_API + "partners/add";

    public static String UPDATE_USERS = BASE_URL_USER_API + "partners/update";

    public static String DELETE_USERS = BASE_URL_USER_API + "partners/delete";

    public static String VEHICLES = BASE_URL_USER_API + "listUserVehicles";

    public static String ADD_VEHICLES = BASE_URL_USER_API + "addUserVehicle";

    public static String UPDATE_VEHICLES = BASE_URL_USER_API + "updateUserVehicle";

    public static String DELETE_VEHICLES = BASE_URL_USER_API + "deleteUserVehicle";

    //Vehicle API
    public static String BRANDS = BASE_URL_VEHICLE_API + "vehicleBrands";

    public static String MODELS = BASE_URL_VEHICLE_API + "vehicleModels/listByBrand";

    //Offer API
    public static String OFFERS = BASE_URL_OFFER_API + "offers";

    public static String CATEGORIES = BASE_URL_OFFER_API + "services";

    public static String CATEGORY_OFFERS_LIST = BASE_URL_OFFER_API + "offers/list";

    //Service API
    public static String BOOKINGS_HISTORY = BASE_URL_SERVICE_API + "bookings";

    public static String BOOK_OFFER = BASE_URL_SERVICE_API + "bookings/add";

}
