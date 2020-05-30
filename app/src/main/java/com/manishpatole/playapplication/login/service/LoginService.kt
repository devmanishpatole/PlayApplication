package com.manishpatole.playapplication.login.service

import com.manishpatole.playapplication.login.model.LoginSuccess
import com.manishpatole.playapplication.login.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginService {

    @POST
    suspend fun login(@Url url: String, @Body loginRequest: LoginRequest): Response<LoginSuccess>
}