package com.cabral.meusjogosfavoritos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabral.meusjogosfavoritos.R
import com.cabral.meusjogosfavoritos.ui.theme.GamesRatingTheme

@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val languages = listOf(
        "pt" to stringResource(id = R.string.portuguese),
        "en" to stringResource(id = R.string.english)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup()
            .testTag("language_selector")
    ) {
        Text(
            text = stringResource(id = R.string.language),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        languages.forEach { (code, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (code == currentLanguage),
                        onClick = { onLanguageChange(code) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp)
                    .testTag("language_option_$code"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (code == currentLanguage),
                    onClick = null // Handled by the Row's selectable modifier
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorPreview() {
    GamesRatingTheme {
        LanguageSelector(
            currentLanguage = "pt",
            onLanguageChange = {}
        )
    }
}
