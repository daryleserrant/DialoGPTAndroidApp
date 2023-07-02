package com.example.dialogptchatbotapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UserChatBubble extends RelativeLayout {
    TextView commentText;
    public UserChatBubble(Context context) {
        super(context);
    }

    public UserChatBubble(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UserChatBubble, 0,0);
        try {
            String textValue = attributes.getString(R.styleable.UserChatBubble_userText);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.user_comment_bubble,this);

            commentText = v.findViewById(R.id.commentText);
            commentText.setText(textValue);
        } finally {
            attributes.recycle();
        }
    }

    public UserChatBubble(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UserChatBubble, defStyleAttr,0);
        try {
            String textValue = attributes.getString(R.styleable.UserChatBubble_userText);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.user_comment_bubble,this);

            commentText = v.findViewById(R.id.commentText);
            commentText.setText(textValue);
        } finally {
            attributes.recycle();
        }
    }

    public void setText(String text)
    {
        commentText.setText(text);
    }

    public String getText()
    {
        return (String) commentText.getText();
    }

    public UserChatBubble(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UserChatBubble, defStyleAttr,defStyleRes);
        try {
            String textValue = attributes.getString(R.styleable.UserChatBubble_userText);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.user_comment_bubble,this);

            commentText = v.findViewById(R.id.commentText);
            commentText.setText(textValue);
        } finally {
            attributes.recycle();
        }
    }

}
