**App Overview**

The app houses its three main pages, home, favourites and search, as fragments within the main activity. The user can navigate between these three fragments using the navigation menu at the bottom of the screen. The app revolves around the feature of inputting a query in the search menu, and then searching that query in Amazon using an API. Once a query is entered, the search results activity opens, displaying a scrollable list of products related to the query. The user can click on a product in the list, changing its colour to light green. This indicates that the product has been selected. The user should select the products that they are interested in, and click the “create item listing” button at the top of the page. This adds all the selected products into an “item listing.” These item listings store the list of products and are stored to the device, meaning that they are not lost when the app terminates. 

Once an item listing has been created, it stores the query as its name, and is displayed in the favourites page. In the favourites page, all the created listings are shown in another list similar to the one for the search results. The user can interact with item listings in several ways. The user can click on the listing, revealing a button that redirects to the amazon product page of the cheapest product in the listing. The user can also click a “see list” button, opening an activity that displays the list of products within the listing. Each item shown can be clicked to reveal a button that links to the individual product’s web page. Within the list of products, there is a button that allows the user to update the listing. When clicked, the API searches the same query, showing new results from amazon, including any changes in price that may have occurred. The items that were originally in the listing are already highlighted in green and are shown at the top, and the user can add more products to the listing, or remove existing ones. 

Finally, the home page contains a single button. If the user has not yet made a search, it displays the text “Go Search.” When clicked, it redirects to the search page. Once the user has made a search, it displays the text “Last Checked.” Now when clicked, the API makes a search using the most recently searched query from the search page.

**Critical Code Blocks**
	
MainActivity houses the home, favourites, and search pages.
The pages are fragments within the activity, named HomeFragment, FavouritesFragment, and SearchFragment.
Individual products are instances of the Item object, and item listings are instances of the ItemListing object. An ItemListing object stores an ArrayList of Item objects as an instance variable.
SearchResultsActivity and ItemsAdapter are responsible for the search results page.
ItemListingAdapter is responsible for displaying item listings in the favourites page.
ProductsActivity is responsible for displaying the list of Items within an ItemListing. It opens through the favourites page. 
Resource files in XML are found in the “res” folder.
To add elements to a page, they should first be added in the layout files for the page.

