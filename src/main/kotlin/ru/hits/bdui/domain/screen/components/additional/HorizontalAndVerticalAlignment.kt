package ru.hits.bdui.domain.screen.components.additional

sealed interface HorizontalAndVerticalAlignment {
    data object TopStart : HorizontalAndVerticalAlignment
    data object TopCenter : HorizontalAndVerticalAlignment
    data object TopEnd : HorizontalAndVerticalAlignment
    data object CenterStart : HorizontalAndVerticalAlignment
    data object Center : HorizontalAndVerticalAlignment
    data object CenterEnd : HorizontalAndVerticalAlignment
    data object BottomStart : HorizontalAndVerticalAlignment
    data object BottomCenter : HorizontalAndVerticalAlignment
    data object BottomEnd : HorizontalAndVerticalAlignment
}

sealed interface VerticalAlignment {
    data object Top : VerticalAlignment
    data object Center : VerticalAlignment
    data object Bottom : VerticalAlignment
}

sealed interface HorizontalAlignment {
    data object Start : HorizontalAlignment
    data object Center : HorizontalAlignment
    data object End : HorizontalAlignment
}