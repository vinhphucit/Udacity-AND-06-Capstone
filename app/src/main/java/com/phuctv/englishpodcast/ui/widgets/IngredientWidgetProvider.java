package com.phuctv.englishpodcast.ui.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.data.local.LocalDataSource;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {
    public static List<FavouriteModel> mChoosedIngredients;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds) {

        LocalDataSource.getInstance().getFavourites().compose(RxUtils.applySchedulersSingle())
                .subscribeWith(new DisposableSingleObserver<List<FavouriteModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<FavouriteModel> recipe) {
                        mChoosedIngredients = recipe;
//                        RemoteViews views = getListRemoteView(context, recipe);
                        for (int appWidgetId : appWidgetIds) {
                            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_app_widget);

                            Intent intent = new Intent(context, ListWidgetService.class);
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                            intent.putExtra(ListWidgetService.KEY_FAVOURITES, new Gson().toJson(recipe));
                            views.setRemoteAdapter(R.id.appwidget_list, intent);
                            views.setTextViewText(R.id.appwidget_text, context.getString(R.string.podcast_favourites));
                            appWidgetManager.updateAppWidget(appWidgetId, views);
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                    }
                });


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    private static RemoteViews getListRemoteView(Context context, List<FavouriteModel> recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_app_widget);
        Intent intent = new Intent(context, ListWidgetService.class);

        intent.putExtra(ListWidgetService.KEY_FAVOURITES, new Gson().toJson(recipe));


        views.setRemoteAdapter(R.id.appwidget_list, intent);

        return views;
    }
}

