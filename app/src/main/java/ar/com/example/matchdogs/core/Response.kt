package ar.com.example.matchdogs.core

import java.lang.Exception

sealed class Response<out T> {

    class Loading<out T>: Response<T>()
    class Success<out T>(val data: T): Response<T>()
    class Failure<out T>(val exception: Exception): Response<T>()

}