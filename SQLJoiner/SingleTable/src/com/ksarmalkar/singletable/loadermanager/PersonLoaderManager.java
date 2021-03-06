package com.ksarmalkar.singletable.loadermanager;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import com.ksarmalkar.singletable.contentprovider.ProjectProvider;
import com.ksarmalkar.singletable.loader.PersonLoader;
import com.ksarmalkar.singletable.model.Person;

public class PersonLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "LOADER_TAG";
    private Activity activity;
    private SimpleCursorAdapter personCursorAdapter;

    public PersonLoaderManager(Activity activity, SimpleCursorAdapter personCursorAdapter){
        this.activity = activity;
        this.personCursorAdapter = personCursorAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /**
         * This requires the URI of the Content Provider
         * projection is the list of columns of the database to return. Null will return all the columns
         * selection is the filter which declares which rows to return. Null will return all the rows for the given URI.
         * selectionArgs:  You may include ?s in the selection, which will be replaced
         * by the values from selectionArgs, in the order that they appear in the selection.
         * The values will be bound as Strings.
         * sortOrder determines the order of rows. Passing null will use the default sort order, which may be unordered.
         * To back a ListView with a Cursor, the cursor must contain a column named _ID.
         */
        return new PersonLoader(activity, null, null, null);
    }

    /**
     * Called when a previously created loadermanager has finished its load. This assigns the new Cursor but does not close the previous one.
     * This allows the system to keep track of the Cursor and manage it for us, optimizing where appropriate. This method is guaranteed
     * to be called prior to the release of the last data that was supplied for this loadermanager. At this point you should remove all use of
     * the old data (since it will be released soon), but should not
     * do your own release of the data since its loadermanager owns it and will take care of that.
     * The framework would take of closing of old cursor once we return.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(personCursorAdapter!=null && cursor!=null)
            personCursorAdapter.swapCursor(cursor); //swap the new cursor in.
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }

    /**
     * This method is triggered when the loadermanager is being reset and the loadermanager  data is no longer available.
     * This is called when the last Cursor provided to onLoadFinished() above
     * is about to be closed. We need to make sure we are no longer using it.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(personCursorAdapter!=null)
            personCursorAdapter.swapCursor(null);
        else
            Log.v(TAG,"OnLoadFinished: mAdapter is null");
    }
}
