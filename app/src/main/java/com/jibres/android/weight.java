package com.jibres.android;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.jibres.android.Static.tag;

public class weight {
    public static void EditTextVerify(final EditText edt_1 ,
                                          final EditText edt_2 ,
                                          final EditText edt_3,
                                          final EditText edt_4,
                                          final EditText edt_5,
                                          final EditTextVerify_Listener listener)
    {
        edt_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "beforeTextChanged: "+i+"|"+i1+"|"+i2);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "onTextChanged: "+i+"|"+i1+"|"+i2);
                if (charSequence.length() == 1){
                    edt_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(tag.error, "afterTextChanged: "+editable);

            }
        });

        edt_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_3.requestFocus();
                }else {
                    edt_1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edt_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_4.requestFocus();
                }else {
                    edt_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_5.requestFocus();
                }else {
                    edt_3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        edt_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String all_verifyCode =
                        edt_1.getText().toString()
                                +edt_2.getText().toString()
                                +edt_3.getText().toString()
                                +edt_4.getText().toString()
                                +edt_5.getText().toString();
                if (charSequence.length() == 1){
                    if (all_verifyCode.length() == 5){
                        listener.onReceived();
                    }else {
                        edt_1.getText().clear();
                        edt_2.getText().clear();
                        edt_3.getText().clear();
                        edt_4.getText().clear();
                        edt_5.getText().clear();
                        edt_1.requestFocus();
                        listener.onError();
                    }
                }else {
                    edt_4.requestFocus();
                    listener.onError();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_1.getText().clear();
                        return true;
                    }else if (keyCode == KeyEvent.KEYCODE_ENTER){
                        edt_2.requestFocus();
                    }else {
                        if (edt_1.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_2.setText(n);
                            }
                            edt_2.requestFocus();
                        }
                    }
                }
                return false;
            }
        });

        edt_2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        if (edt_2.length() == 0){
                            edt_1.getText().clear();
                            edt_1.requestFocus();
                        }else {
                            edt_2.getText().clear();
                            edt_1.requestFocus();
                        }
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_2.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_3.setText(n);
                            }
                            edt_3.requestFocus();
                            return true;
                        }
                    }


                }
                return false;
            }
        });

        edt_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        if (edt_3.length() == 0){
                            edt_2.getText().clear();
                            edt_2.requestFocus();
                        }else {
                            edt_3.getText().clear();
                            edt_2.requestFocus();
                        }
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_3.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_4.setText(n);
                            }
                            edt_4.requestFocus();
                            return true;
                        }
                    }
                }

                return false;
            }
        });

        edt_4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        if (edt_4.length() == 0){
                            edt_3.getText().clear();
                            edt_3.requestFocus();
                        }else {
                            edt_4.getText().clear();
                            edt_3.requestFocus();
                        }
                        return true;
                        //this is for backspace
                    }else if (keyCode == KeyEvent.KEYCODE_ENTER){
                        edt_5.requestFocus();
                        return true;
                    }else {
                        if (edt_4.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_5.setText(n);
                            }
                            edt_5.requestFocus();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        edt_5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        if (edt_5.length() == 0){
                            edt_4.getText().clear();
                            edt_4.requestFocus();
                        }else {
                            edt_5.getText().clear();
                            edt_4.requestFocus();
                        }
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_5.length() == 1){
                            String all_verifyCode =
                                    edt_1.getText().toString()
                                            +edt_2.getText().toString()
                                            +edt_3.getText().toString()
                                            +edt_4.getText().toString()
                                            +edt_5.getText().toString();
                            if (all_verifyCode.length() == 5){
                                listener.onReceived();
                                return true;
                            }else {
                                edt_1.getText().clear();
                                edt_2.getText().clear();
                                edt_3.getText().clear();
                                edt_4.getText().clear();
                                edt_5.getText().clear();
                                edt_1.requestFocus();
                                listener.onError();
                            }
                        }
                    }
                }
                return false;
            }
        });

    }
    public interface EditTextVerify_Listener {
        void onReceived();
        void onError();
    }
}
