package com.freak.kotlinhttpmanager.entity

/**
 * Created by Administrator on 2019/3/16.
 */

class LoginEntity {

    /**
     * loginType : 1
     * code : 200
     * account : {"id":1794699363,"userName":"1_13790994100","type":1,"status":0,"whitelistAuthority":0,"createTime":1552619990230,"salt":"[B@32f340ee","tokenVersion":0,"ban":0,"baoyueVersion":0,"donateVersion":0,"vipType":0,"viptypeVersion":0,"anonimousUser":false}
     * profile : {"djStatus":0,"expertTags":null,"experts":{},"avatarImgId":109951163250233890,"backgroundImgId":109951162868126480,"backgroundUrl":"http://p1.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg","avatarImgIdStr":"109951163250233892","backgroundImgIdStr":"109951162868126486","userId":1794699363,"accountStatus":0,"vipType":0,"gender":0,"province":110000,"nickname":"freak_csh","userType":0,"mutual":false,"remarkName":null,"authStatus":0,"defaultAvatar":true,"avatarUrl":"http://p1.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg","birthday":-2209017600000,"city":110101,"detailDescription":"","followed":false,"description":"","signature":"","authority":0,"avatarImgId_str":"109951163250233892","followeds":0,"follows":3,"eventCount":0,"playlistCount":1,"playlistBeSubscribedCount":0}
     * bindings : [{"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"13790994100\",\"hasPassword\":true}","expired":false,"userId":1794699363,"url":"","expiresIn":2147483647,"refreshTime":1552708437,"id":6825653043,"type":1}]
     */

    var loginType: Int = 0
    var code: Int = 0
    var msg: String? = null
    var account: AccountBean? = null
    var profile: ProfileBean? = null
    var bindings: List<BindingsBean>? = null

    class AccountBean {
        /**
         * id : 1794699363
         * userName : 1_13790994100
         * type : 1
         * status : 0
         * whitelistAuthority : 0
         * createTime : 1552619990230
         * salt : [B@32f340ee
         * tokenVersion : 0
         * ban : 0
         * baoyueVersion : 0
         * donateVersion : 0
         * vipType : 0
         * viptypeVersion : 0
         * anonimousUser : false
         */

        var id: Int = 0
        var userName: String? = null
        var type: Int = 0
        var status: Int = 0
        var whitelistAuthority: Int = 0
        var createTime: Long = 0
        var salt: String? = null
        var tokenVersion: Int = 0
        var ban: Int = 0
        var baoyueVersion: Int = 0
        var donateVersion: Int = 0
        var vipType: Int = 0
        var viptypeVersion: Int = 0
        var isAnonimousUser: Boolean = false

        override fun toString(): String {
            return "AccountBean{" +
                    "id=" + id +
                    ", userName='" + userName + '\''.toString() +
                    ", type=" + type +
                    ", status=" + status +
                    ", whitelistAuthority=" + whitelistAuthority +
                    ", createTime=" + createTime +
                    ", salt='" + salt + '\''.toString() +
                    ", tokenVersion=" + tokenVersion +
                    ", ban=" + ban +
                    ", baoyueVersion=" + baoyueVersion +
                    ", donateVersion=" + donateVersion +
                    ", vipType=" + vipType +
                    ", viptypeVersion=" + viptypeVersion +
                    ", anonimousUser=" + isAnonimousUser +
                    '}'.toString()
        }
    }

    class ProfileBean {
        /**
         * djStatus : 0
         * expertTags : null
         * experts : {}
         * avatarImgId : 109951163250233890
         * backgroundImgId : 109951162868126480
         * backgroundUrl : http://p1.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg
         * avatarImgIdStr : 109951163250233892
         * backgroundImgIdStr : 109951162868126486
         * userId : 1794699363
         * accountStatus : 0
         * vipType : 0
         * gender : 0
         * province : 110000
         * nickname : freak_csh
         * userType : 0
         * mutual : false
         * remarkName : null
         * authStatus : 0
         * defaultAvatar : true
         * avatarUrl : http://p1.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg
         * birthday : -2209017600000
         * city : 110101
         * detailDescription :
         * followed : false
         * description :
         * signature :
         * authority : 0
         * avatarImgId_str : 109951163250233892
         * followeds : 0
         * follows : 3
         * eventCount : 0
         * playlistCount : 1
         * playlistBeSubscribedCount : 0
         */

        var djStatus: Int = 0
        var expertTags: Any? = null
        var experts: ExpertsBean? = null
        var avatarImgId: Long = 0
        var backgroundImgId: Long = 0
        var backgroundUrl: String? = null
        var avatarImgIdStr: String? = null
        var backgroundImgIdStr: String? = null
        var userId: Int = 0
        var accountStatus: Int = 0
        var vipType: Int = 0
        var gender: Int = 0
        var province: Int = 0
        var nickname: String? = null
        var userType: Int = 0
        var isMutual: Boolean = false
        var remarkName: Any? = null
        var authStatus: Int = 0
        var isDefaultAvatar: Boolean = false
        var avatarUrl: String? = null
        var birthday: Long = 0
        var city: Int = 0
        var detailDescription: String? = null
        var isFollowed: Boolean = false
        var description: String? = null
        var signature: String? = null
        var authority: Int = 0
        var avatarImgId_str: String? = null
        var followeds: Int = 0
        var follows: Int = 0
        var eventCount: Int = 0
        var playlistCount: Int = 0
        var playlistBeSubscribedCount: Int = 0

        class ExpertsBean

        override fun toString(): String {
            return "ProfileBean{" +
                    "djStatus=" + djStatus +
                    ", expertTags=" + expertTags +
                    ", experts=" + experts +
                    ", avatarImgId=" + avatarImgId +
                    ", backgroundImgId=" + backgroundImgId +
                    ", backgroundUrl='" + backgroundUrl + '\''.toString() +
                    ", avatarImgIdStr='" + avatarImgIdStr + '\''.toString() +
                    ", backgroundImgIdStr='" + backgroundImgIdStr + '\''.toString() +
                    ", userId=" + userId +
                    ", accountStatus=" + accountStatus +
                    ", vipType=" + vipType +
                    ", gender=" + gender +
                    ", province=" + province +
                    ", nickname='" + nickname + '\''.toString() +
                    ", userType=" + userType +
                    ", mutual=" + isMutual +
                    ", remarkName=" + remarkName +
                    ", authStatus=" + authStatus +
                    ", defaultAvatar=" + isDefaultAvatar +
                    ", avatarUrl='" + avatarUrl + '\''.toString() +
                    ", birthday=" + birthday +
                    ", city=" + city +
                    ", detailDescription='" + detailDescription + '\''.toString() +
                    ", followed=" + isFollowed +
                    ", description='" + description + '\''.toString() +
                    ", signature='" + signature + '\''.toString() +
                    ", authority=" + authority +
                    ", avatarImgId_str='" + avatarImgId_str + '\''.toString() +
                    ", followeds=" + followeds +
                    ", follows=" + follows +
                    ", eventCount=" + eventCount +
                    ", playlistCount=" + playlistCount +
                    ", playlistBeSubscribedCount=" + playlistBeSubscribedCount +
                    '}'.toString()
        }
    }

    class BindingsBean {
        /**
         * tokenJsonStr : {"countrycode":"","cellphone":"13790994100","hasPassword":true}
         * expired : false
         * userId : 1794699363
         * url :
         * expiresIn : 2147483647
         * refreshTime : 1552708437
         * id : 6825653043
         * type : 1
         */

        var tokenJsonStr: String? = null
        var isExpired: Boolean = false
        var userId: Int = 0
        var url: String? = null
        var expiresIn: Int = 0
        var refreshTime: Int = 0
        var id: Long = 0
        var type: Int = 0

        override fun toString(): String {
            return "BindingsBean{" +
                    "tokenJsonStr='" + tokenJsonStr + '\''.toString() +
                    ", expired=" + isExpired +
                    ", userId=" + userId +
                    ", url='" + url + '\''.toString() +
                    ", expiresIn=" + expiresIn +
                    ", refreshTime=" + refreshTime +
                    ", id=" + id +
                    ", type=" + type +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "LoginModel{" +
                "loginType=" + loginType +
                ", code=" + code +
                ", msg='" + msg + '\''.toString() +
                ", account=" + account +
                ", profile=" + profile +
                ", bindings=" + bindings +
                '}'.toString()
    }
}
