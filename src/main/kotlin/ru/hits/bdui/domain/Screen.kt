package ru.hits.bdui.domain

data class Screen(
    val components: List<Component>,
    val endpoints: List<Endpoint>
)