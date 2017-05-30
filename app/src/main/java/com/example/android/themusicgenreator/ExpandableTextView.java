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
 * @see <a href="https://codexplo.wordpress.com/2013/09/07/android-expandable-textview/">Android Expandable TextView</a>
 * User: Bazlur Rahman Rokon
 * Date: 9/7/13 - 3:33 AM
 *
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

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
                if(trim){
                    setBackgroundResource(R.color.background_color);
                }
                else{
                    setBackgroundResource(R.color.colorAccent);
                }

                requestFocusFromTouch();
            }
        });

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

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }
}