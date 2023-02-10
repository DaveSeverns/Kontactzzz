package com.sevdotdev.kontactzzz.contacts.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setText
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sevdotdev.kontactzzz.R
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.core.ui.theme.KontactzzzTheme

/**
 * Displays the details of a individual contact in a material card
 * Each card has an Icon which consists of a familiar circle with the first initial displayed
 * against a themed background. This icon is part of a few smalled pieces which the
 * [ContactCard] is responsible for laying out
 *
 * @param contact a contact which will be displayed
 * @param modifier compose modifier
 */
@Composable
internal fun ContactCard(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .testTag(ContactCardTestTags.CardContainer),
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
            ContactIcon(initial = contact.initial.toString())
            ContactDetails(
                contact = contact
            )
        }
    }
}

/**
 * Custom contact icon, acts as a placeholder before image feature is avaialble
 */
@Composable
private fun ContactIcon(
    initial: String,
) {
    val backgroundColor = MaterialTheme.colors.primary
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .size(size = 80.dp)
            .testTag(ContactCardTestTags.CardIcon)
            .drawBehind {
                drawCircle(
                    color = backgroundColor,
                    radius = 100f
                )
            },
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Center),
            text = initial,
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
        ContactDetailItem(
            detail = contact.name,
            textStyle = MaterialTheme.typography.h6,
        )
        ContactDetailItem(
            detail = contact.phoneNumber,
            icon = Icons.Default.Phone,
            contentDescription = stringResource(id = R.string.phone_content_description),
        )
        ContactDetailItem(
            detail = contact.emailAddress,
            icon = Icons.Default.Email,
            contentDescription = stringResource(id = R.string.email_content_description),
        )
    }
}

@Composable
private fun ContactDetailItem(
    detail: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    icon: ImageVector? = null,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            .testTag(ContactCardTestTags.cardDetail(detail)),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon?.let {
            Icon(imageVector = icon, contentDescription = contentDescription)
        }
        Text(text = detail, style = textStyle)
    }
}


object ContactCardTestTags {
    const val CardContainer = "ContactCard.container"
    const val CardIcon = "ContactCard.Icon"
    fun cardDetail(detail: String) = "ContactCard.Detail=$detail"
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
                ContactCard(contact = contact1)
                ContactCard(contact = contact2)
            }
        }
    }

}

private val contact1 = Contact(
    1, name = "Ted",
    phoneNumber = "123",
    emailAddress = "i@.com",
)

private val contact2 = Contact(
    2, name = "Mrs. Scotty VanWinkel",
    phoneNumber = "123-222-3333",
    emailAddress = "looseygoosey69@hacker.ru"
)