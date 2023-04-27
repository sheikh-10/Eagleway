package com.shop.eagleway.request

data class User(val userNum: String? = null,
                val userInfo: UserInfo? = null,
                ) {
    data class UserInfo(val userName: String? = null,
                        val businessName: String? = null,
                        val language: String? = null,
                        val email: String? = null)
}
