package zhuangzhi.android.baking.util;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import zhuangzhi.android.baking.data.Recipe;

public final class DataUtil {

    private DataUtil() {}

    private static String getRecipeString(Context context) {
        String json = "";
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return json;
    }

    public static List<Recipe> getRecipes(Context context) {
        String json = getRecipeString(context);
        return new Gson().fromJson(json, new TypeToken<List<Recipe>>(){}.getType());
    }

}
