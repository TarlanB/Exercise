package collins.step_definitions;

import collins.pom_pages.Dashboard;
import collins.utils.ConfReader;
import collins.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class AddingItemSteps {


    Dashboard dashboard = new Dashboard();


    @Given("user on the dashboard page of amazon")
    public void user_on_the_dashboard_page_of_amazon() {
        Driver.getDriver().get(ConfReader.getProperty("env"));
    }

    @When("user enters the text {string} in the search bar")
    public void user_enters_the_text_in_the_search_bar(String text) {
        dashboard.searchBar.sendKeys(text);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @When("user chooses the last auto-complete option from the dropdown menu suggestions")
    public void user_chooses_the_last_auto_complete_option_from_the_dropdown_menu_suggestions() {
        List<WebElement> dropdownSuggestions = Driver.getDriver().findElements(By.xpath("//div[@class='autocomplete-results-container']/div"));
        dropdownSuggestions.get(dropdownSuggestions.size() - 1).click();

    }


    // Declaring global variables in order to use in the next step

    String priceOfEach;
    String expectedQuantity;


    @When("user adds the first shoe to the Cart with a quantity of {string}")
    public void user_adds_the_first_shoe_to_the_cart_with_a_quantity_of(String quantity) {

        //locating first shoe , the locator gives us all the shoes from page 1 and click
        Driver.getDriver().findElement(By.xpath("//div[@class='a-section a-spacing-base a-text-center']")).click();

        //locating the price element with two parts which is decimal value and storing value into variable
        priceOfEach = Driver.getDriver().findElement(By.xpath("//*[@id=\"corePrice_feature_div\"]/div/span/span[2]/span[2]")).getText();
        priceOfEach += "." + Driver.getDriver().findElement(By.xpath("//*[@id=\"corePrice_feature_div\"]/div/span/span[2]/span[3]")).getText();

        //quantity comes from feature file
        expectedQuantity = quantity;

        //locating quantity element and click
        Driver.getDriver().findElement(By.xpath("//span[@class='a-dropdown-label']")).click();


        List<WebElement> quantityList = Driver.getDriver().findElements(By.xpath("//ul[@class='a-nostyle a-list-link']/li"));
        if (quantityList.size() >= Integer.valueOf(quantity)) {
            quantityList.get(Integer.valueOf(quantity) - 1).click();
        } else {
            throw new RuntimeException("Quantity is not available");
        }

        //Locating add cart web element and click
        Driver.getDriver().findElement(By.xpath("//input[@id='add-to-cart-button']")).click();


    }

    @Then("user goes to the cart and the total price and quantity of shoes are correct")
    public void user_goes_to_the_cart_and_the_total_price_and_quantity_of_shoes_are_correct() {
        //locating cart element and click
        Driver.getDriver().findElement(By.id("nav-cart-text-container")).click();

        //locating subtotal price
        String actualTotalPrice = Driver.getDriver().findElement(By.xpath("//*[@id=\"sc-subtotal-amount-activecart\"]/span")).getText();

        //and in order to get rid of the dollar sign
        actualTotalPrice = actualTotalPrice.substring(1);

        String expectedTotalPrice = String.valueOf(Double.valueOf(priceOfEach) * Double.valueOf(expectedQuantity));


        String actualQuantity = Driver.getDriver().findElement(By.xpath("//*[@id=\"sc-subtotal-label-activecart\"]")).getText();

        Assert.assertTrue(actualTotalPrice.contains(expectedTotalPrice));
        Assert.assertTrue(actualQuantity.contains(expectedQuantity));


    }

    @Then("user deletes the items from the cart")
    public void user_deletes_the_items_from_the_cart() {

        Driver.getDriver().findElement(By.xpath("//input[@value='Delete']")).click();


    }

    @Then("cart should be empty")
    public void card_should_be_empty() {

        Assert.assertEquals("0", Driver.getDriver().findElement(By.xpath("//span[@id='nav-cart-count']")).getText());


    }


}
