package com.eunho.crossfitranker.common


/**
 * room
 **/
sealed class RoomDataResult<out T> {
    data object NoConstructor : RoomDataResult<Nothing>()
    data class Success<T>(val resultData: T) : RoomDataResult<T>()
    data class Error(val exception: Throwable) : RoomDataResult<Nothing>()
}


/**
 * firebase
 **/
sealed class FireBaseDataResult<out T> {
    data class Success<T>(val resultData: T) : FireBaseDataResult<T>()
    data class Error(val exception: Throwable) : FireBaseDataResult<Nothing>()
}