package com.felipepalma14.cotajusta.ui.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipepalma14.cotajusta.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = stringResource(R.string.search_fiis))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBoxEmptyPreview() {
    SearchBox(
        query = "",
        onQueryChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBoxWithTextPreview() {
    SearchBox(
        query = "HGLG11",
        onQueryChange = {}
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SearchBoxDarkPreview() {
    SearchBox(
        query = "",
        onQueryChange = {}
    )
}
