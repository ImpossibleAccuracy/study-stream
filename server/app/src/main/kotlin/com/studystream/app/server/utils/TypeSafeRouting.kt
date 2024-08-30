package com.studystream.app.server.utils

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import io.ktor.server.resources.delete as ktorDelete
import io.ktor.server.resources.get as ktorGet
import io.ktor.server.resources.post as ktorPost
import io.ktor.server.resources.put as ktorPut

inline fun <reified T : Any> Route.typeSafeGet(
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
) = ktorGet<T>(body)

inline fun <reified T : Any> Route.typeSafePost(
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route = ktorPost<T>(body)

inline fun <reified T : Any> Route.typeSafePut(
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route = ktorPut<T>(body)

inline fun <reified T : Any> Route.typeSafeDelete(
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route = ktorDelete<T>(body)
