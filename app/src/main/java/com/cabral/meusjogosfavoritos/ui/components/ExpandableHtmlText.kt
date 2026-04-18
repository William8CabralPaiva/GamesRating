package com.cabral.meusjogosfavoritos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabral.meusjogosfavoritos.R
import com.cabral.meusjogosfavoritos.ui.theme.GamesRatingTheme

@Composable
fun ExpandableHtmlText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 4,
) {
    var expanded by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                if (!expanded) {
                    isTextOverflowing = result.hasVisualOverflow
                }
            }
        )

        if (isTextOverflowing) {
            Text(
                text = if (expanded) stringResource(R.string.see_more) else stringResource(R.string.see_less),
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.End) // <
                    .clickable { expanded = !expanded }
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableHtmlTextPreview() {
    val htmlText =
        AnnotatedString.fromHtml("<p>Este é um exemplo de texto HTML que pode ser expandido.</p>")
    GamesRatingTheme {
        Surface {
            ExpandableHtmlText(htmlText)
        }
    }
}
