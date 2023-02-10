# Kontactzzz

Welcome to Kontactzzz, the most innovative contacts app (not) on the market. What makes it so innovative? You don’t know who your contacts are until you install the app. 
 
Technically this is what Kontactzzz is in its current state. However it has been designed to be expanded upon and hopefully one day become a truly great application. 
It will need a brilliant team to get it there so I will go through the current implementation and decisions that were made to get Kontactzzz to its current state. 
Then I will discuss some plans for the future and some hopefully low hanging fruit that can be built on top of what exists.

## Where are we today? 🗓️

Currently Kontactzzz key (read: only) feature is displaying a list of contacts which are pulled from a remote server. This list is nice pruned down to only pertinent data, 
sorted alphabetically and then grouped together by first initial, then finally displayed to the user in a scrollable column of material cards. Because our contacts can only be 
generated from a back end server, the Kontactzzz screen has a “pull-to-refresh” feature, which will check the server again after the user makes a pull gesture down from the top 
of their device screen. 

Even though the contacts are sourced from the network, the app is usable offline as well. After at least one successful fetch from the network user will be able to see 
the latest synced contacts regardless if they have a WiFi or Data connection active. 

## UI Implementation 🎨
Kontactzzz’s UI is 100% Jetpack Compose. I chose to use Compose because I was able to quickly put together a lazy loading list which supports sticky headers with a simple 
lambda function out of the box. This really gives Kontactzzz a modern a professional look to its flagship screen.  I also wanted to have Kontactzzz follow a Material design 
palette which is super easy to do with Compose. 

The other advantage of using Compose is being able to break down each UI component into smaller chunks and then…well… composite them into a larger Composable to lay them out.
In the future if another screen in the app wants to reuse any of the Composables in the contacts package it could be as simple as changing their visibility access. 
Or if say we add a feature show an image of a contact, it could either be swapped in for the `ContactIcon` Composable or we could determine during a composition which one to 
show in that spot based on whether or not an image is present. 

## App Architecture and Patterns 🏛️ 
The main pattern for the UI/presentation layer is MVVVM. I made use of the Android Arch. ViewModel to benefit from the its lifecycle in holding the state of the 
view across configuration changes. I did not need to make use of `SavedStateHandle` as Model which feeds the ViewModel is already observed from a persistence datasource 
in the data layer. I use the ViewModel to expose three different states to the View, one to represent the data I want to display (the list of contacts), if the data is being 
loaded/or refreshing which is indicated by the `PullRefreshIndicator` Composable, and finally there is an error flow which is exposed which will be used to send Error events 
up the Composition above the `ContactsScreen` to where I have access to the Snackbar. I chose to leave the `Scaffold` which hosts the `Snackbar` in the main activity with 
the intention that if I was to add more screens I would likely use the `NavHost` component and place that within the content lambda of the `Scaffold` so things like the 
`TopAppBar` or a bottom nav sheet could be shared across that graph. I would also be able to make use of the `Snackbar` I implemented, which acts a delegate for the 
`SnackbarHost` within the current screen for showing errors. This delegate could be used in a similar way for new screens as well as for displaying dismissible messages 
other than error notifications.

The MVVM architecture is supported via a data layer which employs the repository pattern to abstract away the sources of data from the UI. Currently Kontactzzz gets by fine with 
a single repository which is responsible exposing data to the UI and also being grand central station for communicating between different data sources and controlling access to 
update the data that the UI receives. The repository also is currently responsible for some light business logic around error handling from the data sources as well. Because the 
data is sourced from network, but I wanted the application to have persistence to be usable offline as well, it was important that I decided early on what the contacts feature's 
source of truth would be to make sure that the data could be read anytime but still kept up to date from the server. This is why I decided to choose the local database 
as the Single Source of Truth. The UI layer makes a request for the data via an exposed flow from the repository and it also makes separate request to refresh from the network.  Instead of trying to resolve conflicts between the network and local db I use the repository to try and save the network response to the db and then the flow is updated if there is a change in the db table. This is not quite a uni-directional data flow, as no data is really traveling from, nor are there changes being requested from the UI but having this setup as it is will leave that possibility open to future expansion.

One thing I do want to callout, is if you look through the some of the model classes for Contacts you may think the mapping is a little redundant. 
I would make a case though that there is a benefit to having a data model that is free of any hit of someone other entity's schema or implementation, 
such as the 3rd party specific annotations in the data layer versions of these classes. In addition just having a "cleaner" object to look at I feel strongly 
that it's a good idea to define the contract for data in an application early and set the boundaries up between layers like I did with Kontactzzz.

## What's Next? 🔮
The next feature that I would like to tackle, would be giving users the ability to create new contacts to add to the local database. 
I would be able to reuse many parts of the existing data layer as well as integrate a new `CreateContactScreen` into my composition by adding a NavGraph as mentioned above.
As the app's features grow it would probably be a good idea to add some UseCases to the domain layer to better isolate more specific user and data flows that would materialize.
