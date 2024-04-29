package com.example.trappi.model

import java.io.Serializable

data class User(
    var uid: String?,
    var username: String,
    var email: String,
):Serializable
{
    override fun toString(): String {
        return "$username='$username, 'email='$email')"
    }
}