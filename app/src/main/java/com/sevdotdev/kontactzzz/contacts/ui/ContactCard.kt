package com.sevdotdev.kontactzzz.contacts.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.ui.theme.KontactzzzTheme

@Composable
internal fun ContactCard(
    viewState: Contact,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            Modifier
                .fillMaxWidth(.9f)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactImage(name = viewState.name)
            ContactDetails(
                contact = viewState
            )
        }
    }
}

@Composable
private fun ContactImage(
    name: String,
) {
    val backgroundColor = MaterialTheme.colors.primary
    Box(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .size(80.dp)
            .drawBehind {
                drawCircle(
                    color = backgroundColor,
                    radius = 100f
                )
            },
    ) {
        Text(
            modifier = Modifier
                .align(Center),
            text = name.first().toString(),
            style = MaterialTheme.typography.h3
        )
    }
}


@Composable
private fun ContactDetails(
    contact: Contact,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = contact.name, style = MaterialTheme.typography.subtitle1
        )
        Text(text = contact.phoneNumber)
        Text(text = contact.emailAddress)
    }
}


@Preview(
    showSystemUi = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun ContactListItemPreview() {
    KontactzzzTheme {
        Surface {
            Column {
                ContactCard(viewState = contact1)
                ContactCard(viewState = contact2)
            }
        }
    }

}

private val contact1 = Contact(
    1,name = "Ted", phoneNumber = "123", emailAddress = "i@.com"
)

private val contact2 = Contact(
    2,name = "Mrs. Scotty VanWinkel",
    phoneNumber = "123-222-3333",
    emailAddress = "looseygoosey69@hacker.ru"
)