package com.scheduled

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*
import authentication.JwtService
import authentication.hash
import com.scheduled.repository.DatabaseFactory
import com.scheduled.repository.UserRepo
import com.scheduled.routes.UserRoutes
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()

    val db = UserRepo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }

    install(Locations)

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson (ContentType.Text.Plain)
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        UserRoutes(db = db,jwtService = jwtService, hashFunction = hashFunction)
    }
}

data class MySession(val count: Int = 0)

