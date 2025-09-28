package ru.hits.bdui.admin.templates.database.entity.serialization

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.domain.screen.components.Button
import ru.hits.bdui.domain.screen.components.Image
import ru.hits.bdui.domain.screen.components.Input
import ru.hits.bdui.domain.screen.components.ProgressBar
import ru.hits.bdui.domain.screen.components.Spacer
import ru.hits.bdui.domain.screen.components.Switch
import ru.hits.bdui.domain.screen.components.Text

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Text::class, name = "text"),
    JsonSubTypes.Type(value = Input::class, name = "textField"),
    JsonSubTypes.Type(value = Image::class, name = "image"),
    JsonSubTypes.Type(value = Spacer::class, name = "spacer"),
    JsonSubTypes.Type(value = ProgressBar::class, name = "progressBar"),
    JsonSubTypes.Type(value = Switch::class, name = "switch"),
    JsonSubTypes.Type(value = Button::class, name = "button"),
)
interface ComponentMixIn