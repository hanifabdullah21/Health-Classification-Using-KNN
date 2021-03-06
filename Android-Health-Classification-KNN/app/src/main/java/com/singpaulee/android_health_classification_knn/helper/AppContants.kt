package com.singpaulee.android_health_classification_knn.helper

object AppContants {

    internal val EMPTY_NAME = 1001
    internal val EMPTY_EMAIL = 1002
    internal val FALSE_EMAIL_FORMAT = 1003
    internal val EMPTY_USERNAME = 1004
    internal val FALSE_USERNAME_FORMAT = 1005
    internal val EMPTY_PASSWORD = 1006
    internal val EMPTY_CONFIRM_PASSWORD = 1007
    internal val FALSE_CONFIRM_PASSWORD = 1008
    internal val EMPTY_GENDER = 1009
    internal val EMPTY_BORN_DATE = 1010
    internal val EMPTY_PREGNANT_DATE = 10101
    internal val EMPTY_VILLAGE = 1011
    internal val EMPTY_HEIGHT = 1012
    internal val EMPTY_WEIGHT = 1013
    internal val EMPTY_AGE = 1014
    internal val EMPTY_PREGNANT_AGE = 1015
    internal val EMPTY_LILA = 1016

    internal val DATE_PICKER = "datepicker"
    internal val FROM_DATE = "FROM_DATE"
    internal val UNTIL_DATE = "UNTIL_DATE"

    enum class STATUS_MODE(val status: String) {
        STATUS_BURUK("Buruk"),
        STATUS_BAIK("Baik"),
        STATUS_LEBIH("Lebih"),
        STATUS_KURANG("Kurang"),
        STATUS_OBESITAS("Obesitas"),
    }

    enum class STATUS_PREGNANT(val status: String) {
        STATUS_KURANG("Kurang"),
        STATUS_NORMAL("Normal"),
        STATUS_OVERWEIGHT("Overweight"),
        STATUS_OBESITAS("Obesitas"),
    }
}