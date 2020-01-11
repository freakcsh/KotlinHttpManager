package com.freak.kotlinhttpmanager

/**
 * Created by Administrator on 2019/3/16.
 */

class LoginModel {

    /**
     * loginType : 1
     * code : 200
     * account : {"id":1794699363,"userName":"1_13790994100","type":1,"status":0,"whitelistAuthority":0,"createTime":1552619990230,"salt":"[B@32f340ee","tokenVersion":0,"ban":0,"baoyueVersion":0,"donateVersion":0,"vipType":0,"viptypeVersion":0,"anonimousUser":false}
     * profile : {"djStatus":0,"expertTags":null,"experts":{},"avatarImgId":109951163250233890,"backgroundImgId":109951162868126480,"backgroundUrl":"http://p1.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg","avatarImgIdStr":"109951163250233892","backgroundImgIdStr":"109951162868126486","userId":1794699363,"accountStatus":0,"vipType":0,"gender":0,"province":110000,"nickname":"freak_csh","userType":0,"mutual":false,"remarkName":null,"authStatus":0,"defaultAvatar":true,"avatarUrl":"http://p1.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg","birthday":-2209017600000,"city":110101,"detailDescription":"","followed":false,"description":"","signature":"","authority":0,"avatarImgId_str":"109951163250233892","followeds":0,"follows":3,"eventCount":0,"playlistCount":1,"playlistBeSubscribedCount":0}
     * bindings : [{"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"13790994100\",\"hasPassword\":true}","expired":false,"userId":1794699363,"url":"","expiresIn":2147483647,"refreshTime":1552708437,"id":6825653043,"type":1}]
     */

    var token: String? = null
    var huanxin_id: String? = null
    var huanxin_pwd: String? = null
    override fun toString(): String {
        return "LoginModel(token=$token, huanxin_id=$huanxin_id, huanxin_pwd=$huanxin_pwd)"
    }


}
