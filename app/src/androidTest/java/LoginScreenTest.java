/*Tests for Login Page; 14 total tests, */

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.allOf;
import android.widget.EditText;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snowcorp.login.LoginActivity;
import org.snowcorp.login.R;
import org.snowcorp.login.RegisterActivity;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<LoginActivity> ActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkTitle() {
        onView(withText("Android Login")).check(matches(isDisplayed()));
    }


    //checks presence of Login Button
    @Test
    public void checkLoginButton(){
        int buttonId = R.id.button_login; // Replace with the actual button ID

        // Check if the button is displayed
       onView(withId(buttonId)).check(matches(isDisplayed()));
    }

    //checks presence of Register Link
    @Test
    public void checkRegisterLink(){
        int buttonId = R.id.button_register;

        // Check if the button is displayed
        onView(withId(buttonId)).check(matches(isDisplayed()));
    }

    //Check if forgot password link is present
    @Test
    public void checkForgotPassword(){
        int buttonId = R.id.button_reset;
        onView(withId(buttonId)).check(matches(isDisplayed()));

    }

    @Test
    public void clickForgotPassword(){
       int buttonId = R.id.button_reset;
       onView(withId(buttonId)).perform(click());
       onView(withText("Forgot Password")).check(matches(isDisplayed()));
    }


    @Before
    public void initIntent(){
        Intents.init();
    }

    @Test
    public void clickRegister(){
        int buttonId = R.id.button_register;
        onView(withId(buttonId)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(RegisterActivity.class.getName()));

    }

    @After
    public void releaseIntent(){
        Intents.release();
    }

    @Test
    public void typeEmail() throws InterruptedException {
        int emailId = R.id.edit_email;
        onView(
                allOf(
                        isDescendantOfA(withId(emailId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("Arka.pal.0521@gmail.com"));
        onView(withText("Arka.pal.0521@gmail.com")).check(matches(isDisplayed()));
    }

    @Test
    public void typePassword() throws InterruptedException {
        int passwordId = R.id.edit_password;
        onView(
                allOf(
                        isDescendantOfA(withId(passwordId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("asdfadsfadsfadsf"));
        onView(withText("asdfadsfadsfadsf")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyLogin() throws InterruptedException {
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());
        //wait for 2 seconds for warning to appear
        Thread.sleep(2000);
        onView(withText("Please enter the credentials!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }


    //check if entering valid email results in no error
    @Test
    public void validEmailLogin() {
        //enter invalid email
        int emailId = R.id.edit_email;
        onView(
                allOf(
                        isDescendantOfA(withId(emailId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("Arka.pal.0521@gmail.com"));

        //enter nonempty password
        int passwordId = R.id.edit_password;
        onView(
                allOf(
                        isDescendantOfA(withId(passwordId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("asdfadsfadsfadsf"));


        //Login should fail
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());


    }

    //check if invalid email goes through
    @Test
    public void invalidEmailLogin1() throws InterruptedException {
        //enter invalid email
        int emailId = R.id.edit_email;
        onView(
                allOf(
                        isDescendantOfA(withId(emailId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("Arka"));

        //enter nonempty password
        int passwordId = R.id.edit_password;
        onView(
                allOf(
                        isDescendantOfA(withId(passwordId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("asdfadsfadsfadsf"));



        //Login should fail
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());
        Thread.sleep(1000);
        onView(withText("Email is not valid!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

    }
    //check if another invalid email goes through
    @Test
    public void invalidEmailLogin2() throws InterruptedException {
        //enter invalid email
        int emailId = R.id.edit_email;
        onView(
                allOf(
                        isDescendantOfA(withId(emailId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("Arka@gmail"));

        //enter nonempty password
        int passwordId = R.id.edit_password;
        onView(
                allOf(
                        isDescendantOfA(withId(passwordId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("asdfadsfadsfadsf"));



        //Login should fail
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());
        Thread.sleep(1000);
        onView(withText("Email is not valid!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

    }


    //check if empty password with valid email goes through
    @Test
    public void emptyPasswordLogin() throws InterruptedException {
        //enter valid email
        int emailId = R.id.edit_email;
        onView(
                allOf(
                        isDescendantOfA(withId(emailId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("Arka.Pal.0521@gmail.com"));


        //Login should fail
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());
        //wait for 2 seconds for warning to appear
        Thread.sleep(2000);
        onView(withText("Please enter the credentials!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

    }
     //check if empty email goes through
    @Test
    public void emptyEmailLogin() throws InterruptedException {
        //enter password
        int passwordId = R.id.edit_password;
        onView(
                allOf(
                        isDescendantOfA(withId(passwordId)),
                        isAssignableFrom(EditText.class)))
                .perform(typeText("asdfadsfadsfadsf"));


        //Login should fail
        int buttonId = R.id.button_login;
        onView(withId(buttonId)).perform(click());
        //wait for 2 seconds for warning to appear
        Thread.sleep(2000);
        onView(withText("Please enter the credentials!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));

    }


}
