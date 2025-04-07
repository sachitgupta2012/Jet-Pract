package com.jetpacktest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

object Models {
    //VerifyLogin API response
    data class LoginResponse(
        @SerializedName("Message") var Message: String = "",
        @SerializedName("Details") var Details: LoginDetails = LoginDetails(),
        @SerializedName("StatusCode") var StatusCode: Int = 0,
        @SerializedName("Token") var Token: String = ""
    )

    data class LoginDetails(

        @SerializedName("UserId") var UserId: String = "",
        @SerializedName("Password") var Password: String = "",
        @SerializedName("UserEmail") var UserEmail: String = "",
        @SerializedName("UserType") var UserType: String = "",
        @SerializedName("UserRole") var UserRole: String = "",
        @SerializedName("NewPassword") var NewPassword: String = "",
        @SerializedName("ProfilePic") var ProfilePic: String = "",
        @SerializedName("Name") var Name: String = "",
        @SerializedName("MobileNumber") var MobileNumber: String = "",
        @SerializedName("IsFirstTime") var IsFirstTime: Boolean = false
    )

    @Parcelize
    data class ChannelListResponse(
        @SerializedName("Status") var Status: Int? = null,
        @SerializedName("Message") var Message: String = "",
        @SerializedName("Details") var ChannelDetails: ArrayList<ChannelDetails> = arrayListOf()
    ) : Parcelable

    @Parcelize
    data class ChannelDetails(
        @SerializedName("ID") var ID: Int? = null,
        @SerializedName("ChannelName") var ChannelName: String? = "",
        @SerializedName("Description") var Description: String? = "",
        @SerializedName("Link") var Link: String? = "",
        @SerializedName("LogoUrl") var LogoUrl: String = "",
        @SerializedName("BannerUrl") var BannerUrl: String = "",
        @SerializedName("YoutubeLink") var YoutubeLink: String? = "",
        @SerializedName("InstagramLink") var InstagramLink: String? = "",
        @SerializedName("FacebookLink") var FacebookLink: String? = "",
        @SerializedName("SnapchatLink") var SnapchatLink: String? = "",
        @SerializedName("TwitterLink") var TwitterLink: String? = "",
        @SerializedName("ArtistName") var ArtistName: String? = "",
        @SerializedName("TitleName") var TitleName: String? = "",
        @SerializedName("IsActive") var IsActive: Boolean? = null,
        @SerializedName("CreatedAt") var CreatedAt: String? = ""
    ) : Parcelable

    //GetContentPages
    data class ContentResponse(
        @SerializedName("Status") var Status: Int? = null,
        @SerializedName("Message") var Message: String = "",
        @SerializedName("Details") var Details: ContentDetails? = ContentDetails()
    )

    data class ContentDetails(

        @SerializedName("PrivacyURL") var PrivacyURL: String = "",
        @SerializedName("AboutURL") var AboutURL: String = "",
        @SerializedName("TermURL") var TermURL: String = "",
        @SerializedName("IsNotification") var IsNotification: Boolean? = null,
        @SerializedName("IsFirstTime") var IsFirstTime: Boolean? = null

    )

    //Logout
    data class LogoutResponse(
        @SerializedName("Message") var Message: String? = null,
        @SerializedName("Details") var Details: String? = null,
        @SerializedName("StatusCode") var StatusCode: Int? = null,
        @SerializedName("Token") var Token: String? = null
    )
}