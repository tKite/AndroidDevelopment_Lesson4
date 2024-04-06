package com.example.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    private	String	firstName;
    public static final String ARG_TEXT = "text";
    public static final String ARG_KEY = "key";

    private SecretKey gen_key;
    private byte[] text;


    public	MyLoader(@NonNull Context context, Bundle args)	{
        super(context);
        if	(args != null){
            byte[] decode = args.getByteArray(ARG_KEY);
            gen_key = new SecretKeySpec(decode, 0, decode.length, "AES");
            text = args.getByteArray(ARG_TEXT);
        }
    }
    public	static	String	decryptMsg(byte[]	cipherText,	SecretKey secret){
        /*	Decrypt	the	message	*/
        try	{
            Cipher	cipher	=	Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,	secret);
            return	new	String(cipher.doFinal(cipherText));
        }	catch	(NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                       | BadPaddingException | InvalidKeyException e)	{
            throw	new	RuntimeException(e);
        }
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public	String	loadInBackground(){
        //	emulate	long-running operation
        SystemClock.sleep(5000);
        return	decryptMsg(text, gen_key);
    }
}
