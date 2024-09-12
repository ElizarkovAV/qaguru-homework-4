package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SelenoidGithubTests {

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "eager";
    }

    @Test
    void openAssertionPageCheckJUnitCodeIsDisplayed() {
        //act
        open("/selenide/selenide"); //открыть страницу selenide в github
        $("#wiki-tab").click(); //перейти на Wiki страницу проекта
        //asserts
        $(".markdown-body ul").shouldHave(text("Soft assertions")); //проверка наличия ссылки Soft assertions
        //act
        $(byText("Soft assertions")).click(); //переход на страницу Soft assertions
        //asserts
        //проверка наличия JUnit5 кода на странице
        $(".markdown-body").shouldHave(
                text("""
                        @ExtendWith({SoftAssertsExtension.class})
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                               
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }        
                        """)
        );
        //проверка наличия JUnit5 кода на странице
        $(".markdown-body").shouldHave(
                text("""
                        class Tests {
                          @RegisterExtension\s
                          static SoftAssertsExtension softAsserts = new SoftAssertsExtension();
                                                
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                                
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }
                        """
                )
        );


        sleep(5000);
    }

}
