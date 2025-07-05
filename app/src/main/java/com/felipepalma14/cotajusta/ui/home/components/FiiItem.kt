package com.felipepalma14.cotajusta.ui.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.felipepalma14.cotajusta.R
import com.felipepalma14.cotajusta.data.remote.model.Fii
import com.felipepalma14.cotajusta.ui.common.noRippleClickable
import com.felipepalma14.cotajusta.ui.home.preview.FiiPreviewParameterProvider
import java.util.Locale

@Composable
fun FiiItem(
    fii: Fii,
    onFavoriteClick: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fii.symbol,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = fii.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = String.format(Locale.getDefault(), "DY: %.2f%%", fii.dividendYield),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = String.format(Locale.getDefault(), "R$ %.2f", fii.lastPrice),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { onFavoriteClick(fii.symbol) },
                modifier = Modifier.testTag("FavoriteButton_${fii.symbol}")
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_to_favorites)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FiiItemPreview(
    @PreviewParameter(FiiPreviewParameterProvider::class) fii: Fii
) {
    FiiItem(
        fii = fii,
        onFavoriteClick = {},
        onClick = {}
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FiiItemDarkPreview(
    @PreviewParameter(FiiPreviewParameterProvider::class) fii: Fii
) {
    FiiItem(
        fii = fii,
        onFavoriteClick = {},
        onClick = {}
    )
}
