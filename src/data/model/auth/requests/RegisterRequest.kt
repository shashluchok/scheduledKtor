package com.scheduled.data.model.auth.requests

data class RegisterRequest (
        val name:String,
        val email:String,
        val password:String
)