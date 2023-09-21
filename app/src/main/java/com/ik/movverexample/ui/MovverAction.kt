package com.ik.movverexample.ui

sealed class MovverAction {
    data class OnSearch(val query: String) : MovverAction()
    object Sort : MovverAction()
}
