package com.example.android.themusicgenreator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;


/**
 * A custom View class which I will use to include the descriptions for each screen.
 * I use this class so too much of the screen is not taken up by the description, by default.
 * I think it is better to have short text with the option to see more of the description.
 * This code was posted for use online. Found at:
 *
 * @see <a href="https://codexplo.wordpress.com/2013/09/07/android-expandable-textview/">Android Expandable TextView</a>
 * User: Bazlur Rahman Rokon
 * Date: 9/7/13 - 3:33 AM
 * <p>
 * I have since made some small modifications but the original code can be
 * found at the aforementioned link.
 * The modifications I made allow the user to set the visibility of the text view to GONE with an
 * OnLongClickListener.
 */
public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {
    private static final int DEFAULT_TRIM_LENGTH = 80;
    private static final String ELLIPSIS = "...press to view more...press and hold to hide completely";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();

        // Setting an OnClickListener to expand/trim the text when clicked. Also setting the
        // background color of the text to white, when expanded, just to differentiate from all
        // other views on the screen.
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
                if (trim) {
                    setBackgroundResource(R.color.background_color);
                } else {
                    setBackgroundResource(R.color.colorAccent);
                }

                requestFocusFromTouch();
            }
        });

        // Setting an OnLongClickListener to allow the user to hide the ExpandableTextView if they
        // so wish. This is set up so the screens can return to their intended layout once the user
        // has read the description of the screen.
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setVisibility(GONE);
                Snackbar.make(v.getRootView(), context.getString(R.string.removed_description), Snackbar.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    /**
     * A method which uses the enary operator to get the text that should be displayed.
     * Be it the original full text or the trimmed version, by checking the boolean variable trim.
     *
     * @return returns a CharSequence, either trimmed or original text
     */
    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    /**
     * Standard set method, also calls the getTrimmedText() method to set the trimmedTextVariable.
     *
     * @param text A CharSequence of the full text to be displayed by the ExpandableTextView when
     *             it is expanded fully.
     * @param type A BufferType object to define the characteristics of the text, such as styleable,
     *             editable or static
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText();
        bufferType = type;
        setText();
    }

    /**
     * A method to get the trimmed version of the text, and appending the ELLIPSIS variable to
     * notify the user that they can click to view more and expand the TextView
     *
     * @return returns a CharSequence of the trimmed textm if the length of the text is long enough
     * such that it needs to be trimmed.
     */
    private CharSequence getTrimmedText() {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    /**
     * Standard getter method for the originalText attribute
     *
     * @return returns a CharSequence of the original text
     */
    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText();
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }
}