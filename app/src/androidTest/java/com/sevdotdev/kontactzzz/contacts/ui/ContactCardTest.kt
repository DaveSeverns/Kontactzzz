package com.sevdotdev.kontactzzz.contacts.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.core.ui.theme.KontactzzzTheme
import org.junit.Rule
import org.junit.Test


class ContactCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contactCardTest() {
        with(composeTestRule) {
            val name = "Eowyn"
            val phoneNumber = "2020202202 x0030"
            val emailAddress = "AragornStan400@rohirrim.ro"
            setContent {
                KontactzzzTheme {
                    ContactCard(
                        contact = Contact(
                            id = 1,
                            name = name,
                            phoneNumber = phoneNumber,
                            emailAddress = emailAddress,
                        )
                    )
                }
            }
            onNodeWithTag(ContactCardTestTags.CardContainer).assertExists()
                .assertIsDisplayed()
            onNodeWithTag(ContactCardTestTags.CardIcon).assertExists()
                .assertIsDisplayed()
            onNodeWithTag(ContactCardTestTags.cardDetail(name)).assertExists()
                .assertIsDisplayed()
            onNodeWithTag(ContactCardTestTags.cardDetail(phoneNumber)).assertExists()
                .assertIsDisplayed()
            onNodeWithTag(ContactCardTestTags.cardDetail(emailAddress)).assertExists()
                .assertIsDisplayed()

            onNode(hasParent(hasTestTag(ContactCardTestTags.cardDetail(name)))).assertTextEquals(name)
            onNode(hasParent(hasTestTag(ContactCardTestTags.cardDetail(phoneNumber)))).assertTextEquals(phoneNumber)
            onNode(hasParent(hasTestTag(ContactCardTestTags.cardDetail(emailAddress)))).assertTextEquals(emailAddress)
        }
    }
}