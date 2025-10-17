package ru.hits.bdui.domain.screen.components.additional

sealed interface HorizontalOrVerticalAlignment {
    sealed interface VerticalAlignment : HorizontalOrVerticalAlignment {
        data object Top : VerticalAlignment
        data object Center : VerticalAlignment
        data object Bottom : VerticalAlignment
    }

    sealed interface HorizontalAlignment : HorizontalOrVerticalAlignment {
        data object Start : HorizontalAlignment
        data object Center : HorizontalAlignment
        data object End : HorizontalAlignment
    }
}