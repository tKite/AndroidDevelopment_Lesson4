package com.example.cryptoloader;

import static com.example.cryptoloader.MyLoader.ARG_KEY;
import static com.example.cryptoloader.MyLoader.ARG_TEXT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cryptoloader.databinding.ActivityMainBinding;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public	class MainActivity extends	AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>{
    public	final String TAG = this.getClass().getSimpleName();
    private	final int LoaderID = 1234;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public	static SecretKey generateKey(){
        try	{
            SecureRandom sr	= SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any	data used as random	seed".getBytes());
            KeyGenerator kg	=	KeyGenerator.getInstance("AES");
            kg.init(256,	sr);
            return	new SecretKeySpec((kg.generateKey()).getEncoded(),	"AES");
        }	catch(NoSuchAlgorithmException e)	{
            throw new RuntimeException(e);
        }
    }
    public	static	byte[]	encryptMsg(String	message,	SecretKey	secret)	{
        Cipher	cipher	=	null;
        try	{
            cipher	=	Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,	secret);
            return	cipher.doFinal(message.getBytes());
        }	catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                       BadPaddingException | IllegalBlockSizeException e)	{
            throw new RuntimeException(e);
        }
    }
    public void	onClickButton(View view)	{
        Bundle	bundle	=	new	Bundle();
        String str = binding.editText.getText().toString();
        SecretKey gen_key = generateKey();
        //	Обработка	данных	в	Loader
        byte[]	cryptText	= encryptMsg(str, gen_key);
        bundle.putByteArray(ARG_TEXT,	cryptText);
        bundle.putByteArray(ARG_KEY, gen_key.getEncoded());
        Toast.makeText(this, str+gen_key.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, str+gen_key.toString());
        LoaderManager.getInstance(this).initLoader(LoaderID,	bundle,	this);
    }
    @Override
    public	void	onLoaderReset(@NonNull Loader<String> loader)	{
        Log.d(TAG,	"onLoaderReset");
    }
    @NonNull
    @Override
    public	Loader<String>	onCreateLoader(int	i,	@Nullable Bundle	bundle)	{
        if	(i	==	LoaderID)	{
            Toast.makeText(this,"onCreateLoader:" +	i, Toast.LENGTH_SHORT).show();
            Log.d(TAG,	"onCreateLoader:" + i);
            return	new	MyLoader(this,	bundle);
        }
        throw new InvalidParameterException("Invalid loader	id");
    }
    @Override
    public	void onLoadFinished(@NonNull Loader<String>	loader,	String	s)	{
        if	(loader.getId()	==	LoaderID)	{
            Log.d(TAG,	"onLoadFinished:	"	+	s);
            Toast.makeText(this,"onLoadFinished: " + s,	Toast.LENGTH_SHORT).show();
        }
    }
}
