package _390Tests.Release3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fsck.k9.R;
import com.fsck.k9.activity.Accounts;
import com.fsck.k9.activity.drunk_mode_challenges.MathChallenge;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MathChallengeTest {

    private MathChallenge mathChallenge;
    private Context context;

    private ViewInteraction descriptionTextView;

    private ViewInteraction signNumberPicker;
    private ViewInteraction leftNumberPicker;
    private ViewInteraction rightNumberPicker;
    private ViewInteraction submitButton;

    @Rule
    public IntentsTestRule<MathChallenge> mActivityTestRule = new IntentsTestRule<>(MathChallenge.class);

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();

        mathChallenge = mActivityTestRule.getActivity();

        descriptionTextView = onView(withId(R.id.math_challenge_description));

        signNumberPicker = onView(withId(R.id.sign));
        leftNumberPicker = onView(withId(R.id.left_number));
        rightNumberPicker = onView(withId(R.id.right_number));
        submitButton = onView(withId(R.id.submit_math_answer_button));
    }

    @Test
    public void testWrongAnswerMakesPlayerFail() {
        int solution = mathChallenge.getSolution();

        // we want wrong answer, so if random solution happens to be 0, just change answer
        if (solution == 0) {
            rightNumberPicker.perform(setNumber(1));
        }

        submitButton.perform(click());

        // after submit, make sure UI changes as expected
        TextView description = mathChallenge.findViewById(R.id.math_challenge_description);
        descriptionTextView.check(matches(withText(R.string.drunk_mode_challenge_failed)));
        assertEquals(Color.RED, ((ColorDrawable) description.getBackground()).getColor());

        // wait for activity to finish and verify that was booted to specified activity
        SystemClock.sleep(mathChallenge.MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE);
        intended(hasComponent(Accounts.class.getName()));
    }

    @Test
    public void testRightAnswerMakesPlayerPass() {
        int solution = mathChallenge.getSolution();

        // answer correctly
        inputAsAnswer(solution);

        submitButton.perform(click());

        // after submit, make sure UI changes as expected confirming answer was right
        TextView description = mathChallenge.findViewById(R.id.math_challenge_description);
        descriptionTextView.check(matches(withText(R.string.drunk_mode_challenge_success)));
        assertEquals(Color.GREEN, ((ColorDrawable) description.getBackground()).getColor());
    }

    private void inputAsAnswer(int number) {
        //put negative sign for negative number
        if (number < 0) {
            signNumberPicker.perform(setNumber(1));
        }

        number = Math.abs(number);

        rightNumberPicker.perform(setNumber(number % 10));
        leftNumberPicker.perform((setNumber((number / 10) % 10)));
    }

    private static ViewAction setNumber(final int n) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((NumberPicker) view).setValue(n);
            }

            @Override
            public String getDescription() {
                return "Set NumberPicker value";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }
}
