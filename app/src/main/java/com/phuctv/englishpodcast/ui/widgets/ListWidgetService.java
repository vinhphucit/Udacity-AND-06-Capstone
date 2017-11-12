package com.phuctv.englishpodcast.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.phuctv.englishpodcast.ui.widgets.IngredientWidgetProvider.mChoosedIngredients;


public class ListWidgetService extends RemoteViewsService {
    public static final String KEY_FAVOURITES = "KEY_FAVOURITES";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(getApplicationContext(), intent);
    }

    class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
        private final String TAG = ListRemoteViewFactory.class.getSimpleName();

        private final Context mContext;
        private final Intent intent;
        private List<FavouriteModel> ingredients = new ArrayList<>();

        public ListRemoteViewFactory(Context context, Intent intent) {
            Timber.d("constructor");
            this.mContext = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            Timber.d("onCreate");

            Type type = new TypeToken<List<FavouriteModel>>() {
            }.getType();
            ingredients = new Gson().fromJson(intent.getStringExtra(KEY_FAVOURITES), type);
        }

        @Override
        public void onDataSetChanged() {
            this.ingredients = mChoosedIngredients;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            FavouriteModel ingredient = ingredients.get(position);

            RemoteViews views =
                    new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item_widget);

            views.setTextViewText(R.id.item_name, ingredient.getName());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}