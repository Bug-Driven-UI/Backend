package ru.hits.bdui.domain.screen.interactions.actions

sealed interface Action

/**
 * Удаленные действия, которые должны обрабатываться на бэкенде
 */
sealed interface RemoteAction : Action

/**
 * Локальные действия, исполняемые на клиентах без вызова бэкенда
 */
sealed interface LocalAction : Action