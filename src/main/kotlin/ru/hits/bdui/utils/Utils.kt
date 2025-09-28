package ru.hits.bdui.utils

import org.springframework.dao.DuplicateKeyException
import java.sql.SQLException

fun Throwable.isUniqueViolation(): Boolean {
    if (this is DuplicateKeyException) return true

    var cause: Throwable? = this
    while (cause != null) {
        if (cause is SQLException && cause.sqlState == "23505") return true
        cause = cause.cause
    }
    return false
}