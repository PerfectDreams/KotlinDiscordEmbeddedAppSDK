<h1 align="center">✨ Kotlin/JS Bindings for the Discord Embedded App SDK ✨</h1>

This repository contains Kotlin/JS bindings for the [Discord Embedded App SDK](https://github.com/discord/embedded-app-sdk), which is used for creating Discord activities.

These bindings are currently at a very early stage and are not recommended for production use! However, we do use them for [PixelBloom](https://discord.com/application-directory/1402296127109468190). :3

## Usage

```kotlin
repositories {
    maven("https://repo.perfectdreams.net/")
}

kotlin {
    js {
        browser()
        binaries.executable()

        compilerOptions {
            // Enable ES6
            target = "es2015"
            useEsClasses = true
        }
    }

    sourceSets {
        jsMain {
            dependencies {
                implementation("net.perfectdreams.discordembeddedappsdk:kotlin-discord-embedded-app-sdk:0.0.1")
            }
        }
    }
}
```

Below is a minimal example of setting up the SDK, based on the original example from [Discord's Embedded App SDK README](https://github.com/discord/embedded-app-sdk/blob/main/README.md#usage).

Some nice things to know about:

* Some methods return promises. To avoid callback hell, you can use `await()` to wait for them to resolve inside of a suspendable function. 
* Some of the variables are also in `snake_case` and not `camelCase`. That's how the Discord SDK expects them, and sadly `@JsName` does not work with `@JsPlainObject` right now. So we'll need to live with those `snake_case` variables. (see `KT-72474`)
    * When they are fixed, the bindings will be changed to use `camelCase` instead.

```kotlin
import discord.embeddedappsdk.AuthenticateParams
import discord.embeddedappsdk.AuthorizeParams
import discord.embeddedappsdk.DiscordSDK
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.utils.io.charsets.Charsets
import js.promise.await
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

fun main() {
    val http = HttpClient {}
    val discordSDK = DiscordSDK("YOUR_OAUTH2_CLIENT_ID")

    GlobalScope.launch {
        // Wait for READY payload from the discord client
        discordSDK.ready().await()

        // Pop open the OAuth permission modal and request for access to scopes listed in scope array below
        val authorizeResult = discordSDK.commands.authorize(
            AuthorizeParams(
                client_id = "YOUR_OAUTH2_CLIENT_ID",
                response_type = "code",
                state = "",
                prompt = "none",
                scope = arrayOf("applications.commands")
            )
        ).await()
        val code = authorizeResult.code

        // Retrieve an access_token from your application's server
        val responseAsText = http.post("/.proxy/api/token") {
            setBody(
                TextContent(
                    text = buildJsonObject {
                        put("code", code)
                    }.toString(),
                    contentType = ContentType.Application.Json,
                )
            )
        }.bodyAsText(Charsets.UTF_8)
        val responseAsJson = Json.parseToJsonElement(responseAsText)
        val accessCode = responseAsJson.jsonObject["access_code"]!!.jsonPrimitive.content

        // Authenticate with Discord client (using the access_token)
        val auth = discordSDK.commands.authenticate(AuthenticateParams(accessCode)).await()
        val accessToken = auth.access_token
    }
}
```