package com.example.hijau;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class GradientTextView extends AppCompatTextView {

    private int startColor = 0x436544; // default hijau tua
    private int endColor = 0x6FA07E;   // default hijau muda
    private Shader textShader;

    public GradientTextView(Context context) {
        super(context);
        init(null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);
            startColor = a.getColor(R.styleable.GradientTextView_startColor, startColor);
            endColor = a.getColor(R.styleable.GradientTextView_endColor, endColor);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textShader == null) {
            float width = getPaint().measureText(getText().toString());
            textShader = new LinearGradient(
                    0, 0, width, getTextSize(),
                    new int[]{startColor, endColor},
                    null,
                    Shader.TileMode.CLAMP
            );
            getPaint().setShader(textShader);
        }
        super.onDraw(canvas);
    }
}
