package com.nazwa.hopefinder.entity

data class Notifikasi(
    val id: String,
    val title: String,
    val type: String,
    val timestamp: Long,
    val isRead: Boolean = false
)
