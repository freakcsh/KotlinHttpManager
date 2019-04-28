package com.freak.kotlinhttpmanager.entity

/**
 *
 * @author Administrator
 * @date 2019/3/16
 */

class LoginStatusEntity {

    /**
     * code : 200
     * profile : {"userId":1794699363,"nickname":"freak_csh","avatarUrl":"http://p3.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg","birthday":"-2209017600000","userType":0,"djStatus":0}
     * bindings : [{"expiresIn":2147483647,"expired":false,"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"137****4100\",\"hasPassword\":true}","refreshTime":1552708437,"id":6825653043,"type":1,"userId":1794699363,"url":""}]
     */

    var code: Int = 0
    var profile: ProfileBean? = null
    var bindings: List<BindingsBean>? = null

    class ProfileBean {
        /**
         * userId : 1794699363
         * nickname : freak_csh
         * avatarUrl : http://p3.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg
         * birthday : -2209017600000
         * userType : 0
         * djStatus : 0
         */

        var userId: Int = 0
        var nickname: String? = null
        var avatarUrl: String? = null
        var birthday: String? = null
        var userType: Int = 0
        var djStatus: Int = 0

        override fun toString(): String {
            return "ProfileBean{" +
                    "userId=" + userId +
                    ", nickname='" + nickname + '\''.toString() +
                    ", avatarUrl='" + avatarUrl + '\''.toString() +
                    ", birthday='" + birthday + '\''.toString() +
                    ", userType=" + userType +
                    ", djStatus=" + djStatus +
                    '}'.toString()
        }
    }

    class BindingsBean {
        /**
         * expiresIn : 2147483647
         * expired : false
         * tokenJsonStr : {"countrycode":"","cellphone":"137****4100","hasPassword":true}
         * refreshTime : 1552708437
         * id : 6825653043
         * type : 1
         * userId : 1794699363
         * url :
         */

        var expiresIn: Int = 0
        var isExpired: Boolean = false
        var tokenJsonStr: String? = null
        var refreshTime: Int = 0
        var id: Long = 0
        var type: Int = 0
        var userId: Int = 0
        var url: String? = null

        override fun toString(): String {
            return "BindingsBean{" +
                    "expiresIn=" + expiresIn +
                    ", expired=" + isExpired +
                    ", tokenJsonStr='" + tokenJsonStr + '\''.toString() +
                    ", refreshTime=" + refreshTime +
                    ", id=" + id +
                    ", type=" + type +
                    ", userId=" + userId +
                    ", url='" + url + '\''.toString() +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "LoginStatusEntity{" +
                "code=" + code +
                ", profile=" + profile +
                ", bindings=" + bindings +
                '}'.toString()
    }
}
